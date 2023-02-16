package com.Bibo.system.model.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.Bibo.system.model.pojo.dto.SysDictDataListDto;
import com.Bibo.system.model.pojo.dto.SysDictDataPageReqDto;
import com.Bibo.system.model.pojo.entity.TfDict;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.ApiTypeResourceEnum;
import com.Bibo.common.pojo.entity.SysDictData;
import com.Bibo.common.response.ApiResponse;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.ApiRequestParamsUtils;
import com.Bibo.common.util.HttpRequestUtil;
import com.Bibo.common.util.RequestParamsUtil;
import com.Bibo.system.model.mapper.SysDictDataMapper;
import com.Bibo.system.model.pojo.dto.DictListDTO;
import com.Bibo.system.model.pojo.dto.SysDictDataDto;
import com.Bibo.system.model.pojo.vo.DictListVO;
import com.Bibo.system.model.service.ISysDictDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    /**
     * 分页条件查询字典列表
     * @param dictListDTO 查询条件
     * @return 结果
     */
    @Override
    public IPage<DictListVO> selectDictPageList(DictListDTO dictListDTO){
        Page<SysDictData> page = new Page(dictListDTO.getPageNum(), dictListDTO.getPageSize());
//        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<SysDictData>();
//        queryWrapper.like(true, "dict_label", dictListDTO.getDictLabel());
//        IPage<DictListVO> dictListVO = new Page<>();
//        BeanUtil.copyProperties(this.page(page, queryWrapper), dictListVO);
//        return dictListVO;

        return this.baseMapper.selectDictPageList(page, dictListDTO);
    }


    /**
     * 分页模糊查询天算字典类型
     */
    public Response selectTfDistTypeList(SysDictDataPageReqDto dto){
        //解析接口返回数据
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsObject(ApiTypeResourceEnum.RESOURCE_DICT_LIST.getApiType(), dto));
            if(apiResponse.isSuccess()){
                return Response.success().data(apiResponse.getData(), apiResponse.getCount(), apiResponse.getTotalPages());
            }else {
                return Response.error(apiResponse.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("天河接口错误");
    }

    /**
     * 添加天算字典类型
     */
    @Transactional
    @Override
    public Response addTfDistTypeList(String dety){
        //解析接口返回数据
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeResourceEnum.DICT_TYPE_LIST.getApiType(), dety));
            if(apiResponse.isSuccess()){
                List<JSONObject> jsonObjectList = (List<JSONObject>) apiResponse.getData();
                String loginUser = RedisUtil.getUserByRedis().getUserName();
                for (JSONObject json : jsonObjectList){
                    TfDict tfDict = JSONObject.parseObject(String.valueOf(json), TfDict.class);
                    if (ObjectUtil.isNull(tfDict.getDety()) && ObjectUtil.isNull(tfDict.getDesc1()) && ObjectUtil.isNull(tfDict.getDesc0())){
                        continue;
                    }
                    SysDictData sysDictData = new SysDictData();
                    sysDictData.setDictName(tfDict.getDesc2());
                    if (ObjectUtil.isNotNull(tfDict.getDetyCn())){
                        sysDictData.setDictName(tfDict.getDetyCn());
                    }
                    sysDictData.setDictSort(1);
                    sysDictData.setDictType(tfDict.getDety());
                    sysDictData.setDictLabel(tfDict.getDesc1());
                    sysDictData.setDictValue(tfDict.getDesc0());
                    sysDictData.setRemark(tfDict.getDesc2());
                    sysDictData.setCreateBy(loginUser);
                    sysDictData.setCreateTime(new Date());
                    sysDictData.setType("0");// 天算
                    this.baseMapper.insert(sysDictData);
                }
                return Response.success();
            }else {
                return Response.error(apiResponse.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("天河接口错误");

    }


    /**
     * 查询字典类型列表
     * @param reqDto 字典类型
     * @return 结果
     */
    @Override
    public IPage<SysDictDataListDto> selectDictTypeList(SysDictDataPageReqDto reqDto){
        Page<SysDictDataListDto> page = new Page(reqDto.getPageNum(), reqDto.getPageSize());
        return this.baseMapper.selectDictTypePageList(page, reqDto.getType());
    }

    @Override
    public List<SysDictData> selectDictTypeList(String type) {
        return this.baseMapper.selectDictTypeList(type);
    }

    @Override
    public Response renewDictDateToRedis() {
        //获取数据字典
        List<SysDictData> sysDictDatas = this.baseMapper.selectNotEmptyList();
        if (!(sysDictDatas != null && sysDictDatas.size() > 0)){
            return Response.success("数据字典查询为空！！！");
        }

        //封装数据map
        Map<String, Map<String, String>> firstMap = new HashMap<>();

        sysDictDatas.forEach(sysDictData -> {
            //封装最里层map
            Map<String, String> secondMap = firstMap.get(sysDictData.getDictType());
            if (secondMap == null) {
                HashMap<String, String> insideMap = new HashMap<>();
                insideMap.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
                firstMap.put(sysDictData.getDictType(), insideMap);
            } else {
                secondMap.put(sysDictData.getDictValue(), sysDictData.getDictLabel());
                firstMap.put(sysDictData.getDictType(), secondMap);
            }
        });

        //更新到redis
//        firstMap.forEach((key, value) -> {
//            RedisUtil.set(key,value);
//        });
        for (String s : firstMap.keySet()) {
            boolean result = RedisUtil.set(s, firstMap.get(s));
        }


        //
        List<String> dictTypes = this.baseMapper.selectAllDistinctDictType();
        RedisUtil.set("dict",dictTypes);

        return Response.success("数据字典更新到缓存成功！！！");
    }

    @Override
    public List<SysDictDataDto> selectTagSourceList(String dictName) {
        QueryWrapper<SysDictData> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type","sys_tag_source");
        wrapper.eq("status","0");
        List<SysDictData> sysDictData = this.baseMapper.selectList(wrapper);

        ArrayList<SysDictDataDto> list1 = new ArrayList<>();
        ArrayList<SysDictDataDto> list2 = new ArrayList<>();
        for (SysDictData data : sysDictData) {
            SysDictDataDto dto = new SysDictDataDto();
            dto.setDictLabel(data.getDictLabel());
            dto.setDictValue(data.getDictValue());
            List<SysDictData> data1 = this.baseMapper.selectDistinctDictType(data.getDictValue());
            List<SysDictData> data2 = new ArrayList<>();

            data1.forEach(sysDictData1 -> {
                if(StringUtils.isNotBlank(dictName) && sysDictData1.getDictName().contains(dictName)){
                    data2.add(sysDictData1);
                }
            });

            if(data2.size()>0){
                dto.setDatas(data2);
                list2.add(dto);
                continue;
            }

            dto.setDatas(this.baseMapper.selectDistinctDictType(data.getDictValue()));
            list1.add(dto);
        }

        return list2.size() >0 ? list2 : list1;
    }

    @Override
    public List<SysDictData> selectDistinctDictType(String type) {
        return this.baseMapper.selectDistinctDictType(type);
    }

    @Override
    public Boolean startOrStopSysDictData(String id) {
        SysDictData sysDictData = this.baseMapper.selectById(id);
        if("0".equals(sysDictData.getStatus())){
            return update(new UpdateWrapper<SysDictData>().set("status","1").eq("dict_id",id));
        }
        if("1".equals(sysDictData.getStatus())){
            return update(new UpdateWrapper<SysDictData>().set("status","0").eq("dict_id",id));
        }

        return false;
    }


}
