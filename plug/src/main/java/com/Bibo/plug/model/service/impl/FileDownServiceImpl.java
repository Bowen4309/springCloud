package com.Bibo.plug.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.vo.FileDownVO;
import com.Bibo.common.request.PageRequest;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.DateUtils;
import com.Bibo.common.util.ExportUtil;
import com.Bibo.plug.model.dao.SysFileDown;
import com.Bibo.plug.model.mapper.FileDownMapper;
import com.Bibo.plug.model.service.FileDownService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileDownServiceImpl extends ServiceImpl<FileDownMapper, SysFileDown> implements FileDownService {


    /**
     * 查询数据生成文件长传至服务器
     * @param list
     * @param response
     * @param sysFileDown
     */
    @Override
    public void uploadDataFile(List<?> list, HttpServletResponse response, SysFileDown sysFileDown) {
        //上传文件到服务器
        FileDownVO fileDownVO = ExportUtil.export(list,response,sysFileDown.getName());
        if(StringUtils.isNotEmpty(fileDownVO.getUrl())){
            sysFileDown.setStatus(fileDownVO.getStatus());
            sysFileDown.setUrl(fileDownVO.getUrl());
            sysFileDown.setSize(fileDownVO.getSize());
            sysFileDown.setUseTime((int)DateUtils.getTime(new Date(),sysFileDown.getCreateTime()));
        }else{
            fileDownVO.setStatus("数据准备失败");
        }
        //修改上传状态和保存地址
        this.updateById(sysFileDown);
    }

    /**
     * 下载数据文件
     * @param response
     * @param request
     * @param id
     */
    @Override
    public void downFileData(HttpServletResponse response, HttpServletRequest request, String id) {
        SysFileDown sysFileDown = this.getById(id);
        if (null != sysFileDown) {
            ExportUtil.downFile(sysFileDown.getUrl(),response,sysFileDown.getName());
        }
    }

    /**
     * 新增导出数据
     * @param fileName
     * @return
     */
    @Override
    public SysFileDown addDownDile(String fileName) {
        LoginUser user = RedisUtil.getUserByRedis();
        SysFileDown sysFileDown = new SysFileDown();
        sysFileDown.setId(UUID.randomUUID().toString().replace("-",""));
        sysFileDown.setCreateTime(new Date());
        sysFileDown.setUserId(user.getUserId());
        sysFileDown.setUserName(user.getUserName());
        sysFileDown.setName(fileName+".xls");
        sysFileDown.setStatus("生成中");
        this.save(sysFileDown);
        return sysFileDown;
    }

    /**
     * 获取导出列表
     * @return
     */
    @Override
    public Response getDownFileList(PageRequest pageRequest) {
        LoginUser user = RedisUtil.getUserByRedis();
        QueryWrapper<SysFileDown> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId());
        OrderItem orderItem = new OrderItem();
        orderItem.setAsc(true);
        orderItem.setColumn("create_time");
        Page page = new Page(pageRequest.getPageNum(), pageRequest.getPageSize()).addOrder(orderItem).addOrder();
        IPage<SysFileDown> list =this.page(page,queryWrapper);
        return Response.success().data(list.getRecords(),page.getTotal(),page.getPages());
    }
}
