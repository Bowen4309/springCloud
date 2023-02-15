package com.Bibo.system.model.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.pojo.entity.SysDictData;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.*;
import com.Bibo.system.model.pojo.vo.DictListVO;
import com.Bibo.system.model.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author xdh
 * @since 2021-10-19
 */
@Api(tags = "字典内容")
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController extends BaseController {

    @Autowired
    private ISysDictDataService dictDataService;


    @SysLog(title="根据查询条件查询字典列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据查询条件查询字典列表")
    @GetMapping("/list")
    public Response<List<DictListVO>> list(DictListDTO dictListDTO){
        return getDataTable(dictDataService.selectDictPageList(dictListDTO));
    }

    @SysLog(title="根据字典id获取详细信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据字典id获取详细信息")
    @GetMapping("/get")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    public Response<DictListVO> getInfo(String id){
        return success().data(dictDataService.getById(id));
    }

    @SysLog(title="根据字典类型获取类型列表,最里层", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据字典类型获取类型列表,最里层")
    @PostMapping("/typelist")
    public Response dictType(@RequestBody SysDictDataPageReqDto dto){
        IPage<SysDictDataListDto> page = dictDataService.selectDictTypeList(dto);
        return success().data(page.getRecords(),page.getTotal(),page.getPages());
    }

    @SysLog(title="查询天算数据分页列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "查询天算数据分页列表")
    @PostMapping("/tiansuan/list")
    public Response tiansuanDictPage(@RequestBody SysDictDataPageReqDto dto) {
        return dictDataService.selectTfDistTypeList(dto);
    }


    @SysLog(title="根据字典类型获取类型列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据字典类型获取类型列表")
    @GetMapping("/type")
    public Response<List<DictTypeDTO>> dictType(String dictType){
        return success().data(dictDataService.selectDictTypeList(dictType));
    }

    @SysLog(title="新增字典", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增字典")
    @PostMapping("/add")
    public Response add(@RequestBody DictDataDTO dict){
        int count = dictDataService.count(new QueryWrapper<SysDictData>()
                .eq("dict_type",dict.getDictType()).eq("dict_label",dict.getDictLabel()));
        if (count>0){
            return error("新增字典'"+dict.getDictLabel()+"'失败,字典类型的字典名称已存在");
        }
        SysDictData dictData = new SysDictData();
        BeanUtil.copyProperties(dict, dictData);
        dictData.setCreateBy(RedisUtil.getUserByRedis().getUserName());
        dictData.setCreateTime(new Date());
        dictData.setDictSort(dict.getDictSort());
        dictData.setType(dict.getType());
        return toResponse(dictDataService.save(dictData));
    }


    @SysLog(title="编辑字典", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "编辑字典")
    @PostMapping("/edit")
    public Response edit(@RequestBody DictDataDTO dict){
        SysDictData dictData = new SysDictData();
        BeanUtil.copyProperties(dict, dictData);
        // 后期添加userid获取
        dictData.setUpdateBy(RedisUtil.getUserByRedis().getUserName());
        dictData.setUpdateTime(new Date());
        return toResponse(dictDataService.saveOrUpdate(dictData));
    }

    @SysLog(title="暂停或启用字典", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "暂停或启用字典")
    @ApiImplicitParam(value = "id",name = "id",required = true)
    @GetMapping("/stoporstart")
    public Response stopOrStart(String id){
        return toResponse(dictDataService.startOrStopSysDictData(id));
    }

    @SysLog(title="删除字典", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除字典")
    @ApiImplicitParam(value = "id",name = "id",required = true)
    @GetMapping("/delete")
    public Response delete(String id){
        return toResponse(dictDataService.update(
                new UpdateWrapper<SysDictData>().set("status","1").eq("dict_id",id)));
    }

    @SysLog(title="启用字典", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "启用字典")
    @ApiImplicitParam(value = "id",name = "id",required = true)
    @GetMapping("/start")
    public Response start(String id){
        return toResponse(dictDataService.update(
                new UpdateWrapper<SysDictData>().set("status","0").eq("dict_id",id)));
    }


    @ApiOperation(value = "删除字典类型")
    @ApiImplicitParam(value = "dictType",name = "dictType",required = false)
    @GetMapping("/delete/type")
    public Response deleteDictType(String dictType){
        return toResponse(dictDataService.update(
                new UpdateWrapper<SysDictData>().set("status","1").eq("dict_type",dictType)));
    }


    @ApiOperation(value = "更改字典数据到Redis")
    @GetMapping("/renew")
    public Response renewDataToRedis(){
        return dictDataService.renewDictDateToRedis();
    }

    @ApiOperation(value = "查询数据字典最外层列表")
    @GetMapping("/tagsource/list")
    @ApiImplicitParam(value = "dictName",name = "dictName",required = false)
    public Response selectTagSourceList(String dictName){
        return success().data(dictDataService.selectTagSourceList(dictName));
    }

    @ApiOperation(value = "查询数据字典第二层列表")
    @GetMapping("/dicttype/list")
    @ApiImplicitParam(value = "type",name = "type",required = false)
    public Response selectDistinctDictType(String type){
        return success().data(dictDataService.selectDistinctDictType(type));
    }

    @ApiOperation(value = "添加天算字典类型")
    @GetMapping("/dicttype/add")
    @ApiImplicitParam(value = "dety",name = "dety",required =true)
    public Response addTfDistTypeList(String dety){
        return dictDataService.addTfDistTypeList(dety);
    }

}
