package com.Bibo.plug.model.controller;

import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.DateUtils;
import com.Bibo.plug.model.dao.EsSearchDataByTagsDTO;
import com.Bibo.plug.model.dao.EsSearchTagByKeyWordsDTO;
import com.Bibo.plug.model.dao.SysFileDown;
import com.Bibo.plug.model.entity.EsCarTag;
import com.Bibo.plug.model.service.EsService;
import com.Bibo.plug.model.service.FileDownService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Api(tags = "es服务接口")
@RestController
@RequestMapping("/esService")
public class EsServiceController {

    @Autowired
    private EsService esService;

    @Autowired
    private FileDownService fileDownService;

    @SysLog(title="保存车辆标签", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "保存车辆标签",notes = "保存车辆的标签到es")
    @PostMapping("/saveCarPicture")
    public Response saveCarPicture(@RequestBody EsCarTag esCarTag) {
        return esService.saveTag(esCarTag);
    }

    @SysLog(title="根据标签查询数据", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据标签查询数据",notes = "根据标签查询数据")
    @PostMapping("/findDataByTag")
    public Response findDataByTag(@RequestBody EsSearchDataByTagsDTO esSearchDataByTagsDTO){
        return esService.findDataByTag(esSearchDataByTagsDTO);
    }


    @SysLog(title="根据标签导出数据", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据标签导出数据",notes = "根据标签导出数据")
    @PostMapping("/exportDataByTag")
    public Response exportDataByTag(@RequestBody EsSearchDataByTagsDTO esSearchDataByTagsDTO, HttpServletResponse response){
        String fileName = "标签详情数据"+ DateUtils.getDateTimeToStr(new Date());
        SysFileDown sysFileDown =fileDownService.addDownDile(fileName);
        esService.exportDataByTag(esSearchDataByTagsDTO, response,sysFileDown);
        return Response.success();
    }

    @SysLog(title="根据输入关键字查询标签", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据输入关键字查询标签",notes = "根据输入关键字查询标签")
    @PostMapping("/findTagByKeyWord")
    public Response findTagByKeyWord(@RequestBody EsSearchTagByKeyWordsDTO esSearchTagByKeyWordsDTO){
        return esService.findTagByKeyWrods(esSearchTagByKeyWordsDTO);
    }
    @SysLog(title="同步数据到ES", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "同步数据到ES",notes = "同步数据到ES")
    @GetMapping("/syncDataToEs")
    public Response syncDataToEs(){

        return esService.syncDataToEs();
    }


    @SysLog(title="根据警号查询警员基本信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据警号查询警员基本信息",notes = "根据警号查询警员基本信息")
    @GetMapping("/findPoliceInfo")
    public Response findPoliceInfo(String policeCode){
        return esService.findPoliceInfo(policeCode);
    }

    @SysLog(title="根据名称和编号查询企业基本信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据名称和编号查询企业基本信息",notes = "根据名称和编号查询企业基本信息")
    @GetMapping("/findCompanyInfo")
    public Response findCompanyInfo(String code,String name){
        return esService.findCompanyInfo(code, name);
    }

}
