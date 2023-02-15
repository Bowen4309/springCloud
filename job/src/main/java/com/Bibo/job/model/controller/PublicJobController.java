package com.Bibo.job.model.controller;

import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import com.Bibo.job.model.service.IPublicJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "公共定时任务")
@RestController
@RequestMapping("/publicJob")
public class PublicJobController {

    @Autowired
    private IPublicJobService publicJobService;

    @SysLog(title="专题数据定时任务", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "专题数据定时任务",notes = "专题审核数据重审定时任务")
    @GetMapping(value = "/checkAnalyseApprove")
    public Response checkAnalyseApprove() {
        return publicJobService.checkAnlayseApprove();
    }

    @SysLog(title="资源绑定业务网格", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "资源绑定业务网格",notes = "专题审核数据重审定时任务")
    @GetMapping(value = "/bussinessGridMap")
    public Response bussinessGridMap() {
        return publicJobService.bussinessGridMap();
    }

    @SysLog(title="专题活跃数据定时任务", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "专题活跃数据定时任务",notes = "专题审核活跃数据重审定时任务")
    @GetMapping(value = "/updateAnalyseActiveCar")
    public Response updateAnalyseActiveCar(){
        return publicJobService.updateAnalyseActiveCar();
    }

    @SysLog(title="专题异常数据定时任务", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "专题异常数据定时任务",notes = "专题审核异常数据重审定时任务")
    @GetMapping(value = "/updateAnalyseUnusualCar")
    public Response updateAnalyseUnusualCar(){
        return publicJobService.updateAnalyseUnusualCar();
    }

    @SysLog(title="上传车牌号码到ftp", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "上传车牌号码到ftp",notes = "上传车牌号码EXCEL文件到ftp")
    @GetMapping(value = "/uploadCarNumberToFtp")
    public Response uploadCarNumberToFtp(){
        return publicJobService.uploadCarNumberToFtp();
    }

    @SysLog(title="ftp下载图片到本地", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "ftp下载图片到本地",notes = "从ftp下载图片到本地")
    @GetMapping(value = "/downCarPictureFromFtp")
    public Response downCarPictureFromFtp(){
        return publicJobService.downPictureFromFtp();
    }

    @ApiOperation(value = "浪费资源定时任务",notes = "浪费资源定时任务")
    @GetMapping(value = "/test")
    public Response test(){
        return publicJobService.test();
    }

    @SysLog(title="同步企业信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "同步企业信息",notes = "同步企业信息")
    @GetMapping(value = "/synCompanyInfo")
    public Response synCompanyInfo(){
        return publicJobService.synCompanyInfo();
    }

}
