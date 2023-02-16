package com.Bibo.system.model.controller;

import com.Bibo.system.model.pojo.dto.*;
import com.Bibo.system.model.service.ISysCustomFormService;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import com.xdh.traffic_system.model.pojo.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "数据采集")
@RestController
@RequestMapping("/custom")
public class SysCustomFormController {

    @Autowired
    private ISysCustomFormService sysCustomFormService;

    @SysLog(title="数据采集字段新增或修改", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "数据采集字段新增或修改",notes = "数据采集字段新增或修改")
    @PostMapping("/form/save")
    Response saveForm(@RequestBody SysCustomFormFieldInsertDto dtoList){
        return sysCustomFormService.saveForm(dtoList);
    }


    @SysLog(title="数据采集多个字段新增或修改", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "数据采集多个字段新增或修改",notes = "数据采集字段多个新增或修改")
    @PostMapping("/form/list/save")
    Response saveFormList(@RequestBody SysCustomFormFieldInsertObjDto dtoList){
        return sysCustomFormService.saveFormList(dtoList);
    }

    @SysLog(title="数据采集字段状态修改", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "数据采集字段状态修改",notes = "数据采集字段状态修改")
    @GetMapping("/form/updateStatus")
    Response updateStatus(SysCustomFormFieldStatusUpdateDto dto){
        return sysCustomFormService.updateStatus(dto);
    }

    @SysLog(title="数据采集字段列表查询", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "数据采集字段列表查询",notes = "数据采集字段列表查询")
    @GetMapping("/form/list")
    Response selectFormFieldList(@ApiParam(name = "status",value = "1是0否空全部",required = false) @RequestParam(required = false) Integer status){
        return sysCustomFormService.selectFormFieldList(status);
    }

    @SysLog(title="数据采集字段录入", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "数据采集字段录入",notes = "数据采集字段录入")
    @PostMapping("/value/save")
    Response saveFieldValue(@RequestBody SysCustomFieldValueInsertDto dtoList){
        return sysCustomFormService.saveFieldValue(dtoList);
    }

    @SysLog(title="数据采集表单查询", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "数据采集表单查询",notes = "数据采集表单查询")
    @PostMapping("/value/list")
    Response selectFieldValueList(@RequestBody SysCustomFieldValueListReqObjDto obj){
        return sysCustomFormService.selectFieldValueList(obj);
    }

    @SysLog(title="根据行号查询详情", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据行号查询详情",notes = "根据行号查询详情")
    @GetMapping("/value/find")
    public Response selectFieldValueByRowNoAndFormId(String formId, Integer defaultRowNo) {
        return sysCustomFormService.selectFieldValueByRowNoAndFormId(formId,defaultRowNo);
    }



}
