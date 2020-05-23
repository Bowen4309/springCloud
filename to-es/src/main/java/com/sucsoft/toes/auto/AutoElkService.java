package com.sucsoft.toes.auto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sucsoft.toes.bean.ColumnDescVO;
import com.sucsoft.toes.dao.SqlDescriptionMapper;
import com.sucsoft.toes.uitls.ChineseUtils;
import com.sucsoft.toes.bean.SqlStatementToElkDO;
import com.sucsoft.toes.bean.WorkProcessVO;
import com.sucsoft.toes.dao.SqlStatementMapper;
import com.sucsoft.toes.mapper.SqlMapper;
import com.sucsoft.toes.service.IEsHelpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

/**
 *
 * <br>-lastModify:2019/8/23 9:22
 *
 * @author Lixiaoban
 * @version 1.0
 */
@Configuration
@SuppressWarnings("unused")
public class AutoElkService {

    private static final Logger log = LoggerFactory.getLogger(AutoElkService.class);

    /**
     * sql 工作内容缓存池
     */
    private Map<String, SqlStatementToElkDO> sqlStatementCache = Maps.newHashMapWithExpectedSize(16);

    /**
     * 工作进度缓冲池
     */
    private Map<String, WorkProcessVO> processCache = Maps.newHashMapWithExpectedSize(128);

    /**
     * 工人
     */
    private ScheduledThreadPoolExecutor scheduler;
    /**
     * 更改字段缓存，最外层使用indexName作为key分割
     * 分割后是一个以数据集主键为key，modify字段值为value的简单map类型的数据
     */
    private Map<String, Map<Object, Object>> modifiedCache = Maps.newHashMapWithExpectedSize(256);

    private IEsHelpService helpService;

    private SqlMapper sqlMapper;

    private SqlStatementMapper sqlStatementMapper;

    private ObjectMapper objectMapper;

    private CacheDataBaseInitialService cacheDataBaseInitialService;

    private SqlDescriptionMapper sqlDescriptionMapper;

    public AutoElkService(CacheDataBaseInitialService cacheDataBaseInitialService, IEsHelpService esHelpService,
                          SqlMapper sqlMapper, SqlStatementMapper sqlStatementMapper, ObjectMapper objectMapper, SqlDescriptionMapper sqlDescriptionMapper){
        this.cacheDataBaseInitialService = cacheDataBaseInitialService;
        this.helpService = esHelpService;
        this.sqlMapper = sqlMapper;
        this.sqlStatementMapper = sqlStatementMapper;
        this.objectMapper = objectMapper;
        this.sqlDescriptionMapper = sqlDescriptionMapper;
        scheduler = new ScheduledThreadPoolExecutor(10,  new ThreadFactoryBuilder()
                .setNameFormat("定时任务%d").build(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
    }

    /**
     * 初始化sqlStatementCache
     * 读库或者读内存
     */
    @PostConstruct
    public void init(){
        //selectList()此处使用报错==>怀疑由于sql还未生成导致
        List<SqlStatementToElkDO> sqlStatementToElkDOS = sqlStatementMapper.list();
        for (SqlStatementToElkDO sqlStatementToElkDO : sqlStatementToElkDOS) {
            try {
                addSqlStatementWithoutCache(sqlStatementToElkDO);
            }catch (Exception e){
                log.error("当前有记录未正常录入——" + sqlStatementToElkDO.toString(),e);
            }
        }
    }

    /**
     * 对外
     * 新增任务并且自动部署为定时任务，含入库操作,适合外部程序调用
     * @param elkDO 任务信息
     * @return 是否成功
     */
    public boolean addSqlStatement(SqlStatementToElkDO elkDO){
        SqlStatementToElkDO one = sqlStatementMapper.findOne(elkDO.getId());
        if(null == one) {
            //入库操作
            addSqlStatementWithoutCache(elkDO);
            //只有在验证通过的情况下才入库
            int insert = sqlStatementMapper.save(elkDO);
            return insert != 0;
        }
        //如果数据库中已经存在，则会有init方法对其进行进入工作集操作
        return true;
    }

    /**
     * 删除任务
     * @param id 任务id
     */
    public void deleteSqlStatement(String id){
        SqlStatementToElkDO sqlStatementToElkDO = sqlStatementCache.get(id);
        boolean b = checkIsWorking(sqlStatementToElkDO.getIndexName(), System.currentTimeMillis());
        if(b){
            throw new IllegalArgumentException("当前任务仍然在执行，稍后删除");
        }
        int delete = sqlStatementMapper.delete(id);
        if(delete == 0){
            throw new IllegalArgumentException("数据库删除无效");
        }
        //删除对内存来说实际上是关闭操作，数据库中执行删除操作，下次不再读取
        sqlStatementToElkDO.setStatus(SqlStatementToElkDO.STATUS_CLOSE);
    }

    /**
     * 插入注释
     * @param descs 注释--携带comment和name的参数
     * @return 是否插入成功
     */
    public boolean addColumnDesc(List<Map> descs, String indexName){
        if(StringUtils.isEmpty(indexName)){
            return false;
        }
        for (Map desc : descs) {
            //注释
            Object comment = desc.get("comment");
            //字段名
            Object name = desc.get("name");
            String pk = indexName + name.toString();
            //如果数据库中已存在相关字段，直接跳过
            ColumnDescVO one = sqlDescriptionMapper.getOne(pk);
            if(null != one){
                continue;
            }
            ColumnDescVO columnDescVO = new ColumnDescVO()
                    .setIndexName(indexName)
                    .setColumnDesc(StringUtils.isEmpty(comment)? "": comment.toString())
                    .setColumnName(name.toString())
                    .setId(pk);
            sqlDescriptionMapper.save(columnDescVO);
        }
        return true;
    }

    /**
     * 对外，根据indexName获取此时间戳中，该程序是否在运行，仅能检测当前最新的一次运行的信息
     * @param indexName indexName
     * @param time 限定时间戳
     * @return 是否在运行
     */
    public boolean checkIsWorking(String indexName,Long time){
        WorkProcessVO workProcessVO = this.processCache.get(indexName);
        time = Optional.ofNullable(time).orElse(System.currentTimeMillis());
        //启动时间小于当前时间===>当前规则已经启动
        // 且结束时间也小于启动时间===>当前规则还在运行未结束
        return workProcessVO.getStart() < time && workProcessVO.getEnd() < workProcessVO.getStart();
    }

    /**
     * 修改当前状态
     * @param elkDO elkDO
     * @param status 状态码 SqlStatementToElkDO
     * @return 是否成功
     */
    public boolean modifyStatus(SqlStatementToElkDO elkDO, int status){
        //修改目标对象
        elkDO.setStatus(status);
        //将修改的状态持久化到数据库
        int i = sqlStatementMapper.updateStatus(elkDO);
        if(i == 0){
            return false;
        }
        //修改池中的对象
        this.sqlStatementCache.get(elkDO.getIndexName()).setStatus(status);

        return true;
    }

    /**
     * 新增任务并且自动部署为定时任务，且没有入库操作，专用于从数据库中初始化使用
     * @param elkDO elkDo
     */
    private void addSqlStatementWithoutCache(SqlStatementToElkDO elkDO){
        //此处生成indexMapping并验证
        try {
            drawIndexMapping(elkDO);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        //内存修改
        this.sqlStatementCache.put(elkDO.getIndexName(), elkDO);
        //加入工作进度缓冲池,仅在新建时添加
        this.processCache.put(elkDO.getIndexName(),new WorkProcessVO());
        //定时任务发布
        drawTask(elkDO);
    }

    /**
     * 生成indexMapping
     * @param elkDO elkDO
     */
    @SuppressWarnings("unchecked")
    private void drawIndexMapping(SqlStatementToElkDO elkDO) throws Exception{
        boolean index = helpService.createIndex(elkDO.getIndexName().toLowerCase());
        //修改indexMapping并执行sql认证
        List<Map> maps = sqlMapper.doSql(elkDO.getStatement());
        if(index){
            //用户自己提交indexMapping，以用户自己提交的为主
            if(!StringUtils.isEmpty(elkDO.getIndexMapping())){
                helpService.updateIndexMapping(objectMapper.readValue(elkDO.getIndexMapping(), Map.class),elkDO.getIndexName().toLowerCase());
            }else {
                if (maps.isEmpty()) {
                    log.error("没有具体数据对应映射关系==>" + elkDO);
                } else {
                    Map map = maps.get(0);
                    //创建一条demo数据截取其自动生成的mapping信息
                    String indexNameDemo = elkDO.getIndexName().toLowerCase() + "indexdemo" + System.currentTimeMillis();
                    helpService.saveItem(map, indexNameDemo, elkDO.getIdName());
                    //获取自动生成的indexMapping
                    Map indexMapping = helpService.getIndexMapping(indexNameDemo);
                    //删除作为indexMapping生成器的index
                    helpService.removeIndex(indexNameDemo);
                    Map properties = (Map) indexMapping.get("properties");
                    //添加中文分词器
                    map.keySet().stream()
                            .filter(k -> map.get(k) instanceof String)
                            .filter(k -> ChineseUtils.isContainChinese(map.get(k).toString()))
                            .forEach(k -> ChineseUtils.setChineseAnalyzer((Map)properties.get(k)));
                    helpService.updateIndexMapping(indexMapping,elkDO.getIndexName().toLowerCase());
                }
            }
        }
    }

    /**
     * 部署任务至定时任务池
     * @param elkDO 任务信息
     */
    private void drawTask(SqlStatementToElkDO elkDO){
        //执行定时操作
        log.info("定时任务-->"+ elkDO + "      -------发布中");
        //60s后启动
        scheduler.scheduleAtFixedRate(new TaskRunner(elkDO),30, elkDO.getIntervalSeconds(), TimeUnit.SECONDS);
        log.info("定时任务-->"+ elkDO + "      -------成功发布");
    }

    /**
     * 确认记录是否更改
     * <p>
     *     根据当前modify字段判断当前记录是否被更新，默认该字段与之前不同即进行更新
     *     此操作当前无存库打算，当前程序重启后缓存数据会全部消息，等待下一次轮询后再次缓存
     *     <b>如果缓存字段没有数据的情况下，默认全更新操作，全部记录全部更新</b>
     *     <b>且缓存或者数据的该字段任意一个为空的话默认同样执行update操作</b>
     * </p>
     * @param indexName elk的索引名称
     * @param rewardId 当前数据库记录id，也是elk索引中的一条文档的id
     * @param value 当前正准备修改的字段的值
     * @return 确认是否更改
     */
    private boolean checkIsModified(String indexName, Object rewardId, Object value){
        Map<Object, Object> modifyCacheItem = getModifyCacheItem(indexName);
        Object o = modifyCacheItem.get(rewardId);
        return null == value || !value.equals(o);
    }

    /**
     * 根据indexName获取当前缓存字段项的缓存记录
     * @param indexName 索引名
     * @return 当前索引下的缓存信息
     */
    private Map<Object, Object> getModifyCacheItem(String indexName){
        if(!modifiedCache.containsKey(indexName)){
            modifiedCache.put(indexName,Maps.newHashMapWithExpectedSize(1024));
        }
        return modifiedCache.get(indexName);
    }

    /**
     * 任务执行方法
     * @author Libin
     */
    private class TaskRunner implements Runnable {

        private SqlStatementToElkDO elkDO;

        TaskRunner(SqlStatementToElkDO elkDO){
            this.elkDO = elkDO;
        }


        /**
         * 插入数据执行方法
         */
        @Override
        public void run() {
            //关闭状态
            if(elkDO.getStatus().equals(SqlStatementToElkDO.STATUS_CLOSE)){
                return;
            }
            log.info(String.format("当前执行sql==>%s==>转入%s",elkDO.getStatement(),elkDO.getIndexName()));
            //操作开始时间记录
            long start = System.currentTimeMillis();
            processCache.get(elkDO.getIndexName()).setStart(start);
            //根据sql获取数据
            List<Map> test = null;
            try {
                test = sqlMapper.doSql(elkDO.getStatement());
            } catch (Exception e) {
                log.error("查询异常",e);
            }
            int errorSkip = 0;
            int unUpdateSkip = 0;
            for (Map map : test) {
                //中途出现错误的情况，跳过此次记录并继续执行下面一条记录
                try {
                    if(!StringUtils.isEmpty(elkDO.getModifyColumn())){
                        Object o = map.get(elkDO.getModifyColumn());
                        if(!checkIsModified(elkDO.getIndexName(),map.get(elkDO.getIdName()),o)){
                            //todo 是否要增加日志说明
                            unUpdateSkip ++;
                            continue;
                        }
                        //非跳过的时候需要对此更新字段进行更新
                        modifiedCache.get(elkDO.getIndexName()).put(map.get(elkDO.getIdName()),o);
                    }
                    helpService.saveItem(map, elkDO.getIndexName().toLowerCase(), elkDO.getIdName());
                } catch (IOException e) {
                    log.error(String.format("当前执行sql==>%s==>转入%s中，记录%s插入es失败",
                            elkDO.getStatement(),elkDO.getIndexName(),map.toString()),e);
                    //错误信息记录
                    //todo 等待一个可操作的方案-->可以记录这条记录异步再插入
                    errorSkip ++;
                }
            }
            //本次导入结束声明
            log.info(String.format("当前执行sql==>%s==>转入%s已结束，应插入(或更新)%d条，实插入(更新)%d条，未改变跳过%d条，出现错误%d条，花费时间%d毫秒"
                    ,elkDO.getStatement(),elkDO.getIndexName(),test.size(),test.size() - errorSkip - unUpdateSkip,
                    unUpdateSkip, errorSkip, System.currentTimeMillis() - start));
            //操作结束时间记录
            processCache.get(elkDO.getIndexName()).setEnd(System.currentTimeMillis());
        }
    }
}
