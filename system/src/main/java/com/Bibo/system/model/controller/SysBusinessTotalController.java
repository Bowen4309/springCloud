package com.Bibo.system.model.controller;

import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.*;
import com.Bibo.system.model.service.ISysBusinessTotalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Api(tags = "业务统计指标管理")
@RestController
@RequestMapping("/businessTotal")
public class SysBusinessTotalController {

    @Autowired
    private ISysBusinessTotalService iSysBusinessTotalService;


    @SysLog(title="获取业务指标的类型", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取业务指标类型",notes = "获取业务指标的类型")
    @GetMapping("/getTypeList")
    public Response getTypeList(){
        return iSysBusinessTotalService.getTypeList();
    }

    @SysLog(title="获取业务指标的列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取业务指标的列表",notes = "获取业务指标的列表数据")
    @PostMapping("/getBusinessList")
    public Response<List<BusinessTotalRsponsDTO>> getBusinessList(@RequestBody BusinessTotalListReqDTO businessTotalReqDTO){
        return iSysBusinessTotalService.getBusinessList(businessTotalReqDTO);
    }

    @SysLog(title="获取类型和指标(指标超市)", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取类型和指标(指标超市)",notes = "获取业务指标的类型和对应的业务指标数据")
    @GetMapping("/getTypeAndChildList")
    public Response<List<BusinessTotalListResponDTO>> getTypeAndChildList(){
        return iSysBusinessTotalService.getTypeAndChildList();
    }

    @SysLog(title="根据ID获取业务指标信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "查询业务指标信息",notes = "根据ID获取业务指标信息")
    @GetMapping("/findById")
    @ApiImplicitParam(value = "id",name = "id",required = true)
    public Response<BusinessTotalRsponsDTO> findById(String id){
        return iSysBusinessTotalService.findById(id);
    }


    @SysLog(title="修改业务指标信息", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "修改业务指标信息",notes = "修改业务指标信息")
    @PostMapping("/update")
    public Response update(@RequestBody BusinessTotalReqDTO businessTotalReqDTO){
        iSysBusinessTotalService.updateData(businessTotalReqDTO);
        return Response.success();
    }

    @SysLog(title="新增业务指标信息", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增业务指标信息",notes = "新增业务指标信息")
    @PostMapping("/save")
    public Response save(@RequestBody BusinessTotalReqDTO businessTotalReqDTO){
        iSysBusinessTotalService.saveData(businessTotalReqDTO);
        return Response.success();
    }

    @SysLog(title="删除业务指标信息", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "删除业务指标信息",notes = "删除业务指标信息")
    @GetMapping("/delete")
    @ApiImplicitParam(value = "id",name = "id",required = true)
    public Response delete(String id){
        iSysBusinessTotalService.deleteData(id);
        return Response.success();
    }


    @SysLog(title="设置新用户默认业务指标", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新用户默认业务指标配置",notes = "设置新用户默认业务指标")
    @GetMapping("/initUserBusinessTotal")
    public Response initUserBusinessTotal(){
        iSysBusinessTotalService.initUserBusinessTotal();
        return Response.success();
    }

    @SysLog(title="修改业务指标状态", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "修改业务指标状态",notes = "修改业务指标状态")
    @GetMapping("/updateStatus")
    public Response updateStatus(String ids,String status){
        iSysBusinessTotalService.updateStatus(ids,status);
        return Response.success();
    }

    @SysLog(title="指标审核情况查看", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "指标审核情况查看",notes = "指标审核情况查看")
    @GetMapping("/approveIndex")
    public Response<List<BusinessTotalApproveDTO>> approveIndex(){
        return iSysBusinessTotalService.findApproveData();
    }

    @SysLog(title="添加指标到指标清单", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "添加指标到指标清单",notes = "添加指标到指标清单状态为未提交")
    @GetMapping("/addIndex")
    public Response addIndex(String ids){
        iSysBusinessTotalService.addIndexList(ids);
        return Response.success();
    }

    @SysLog(title="从指标清单或指标展示移除指标", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "从指标清单或指标展示移除指标",notes = "从指标清单或指标展示移除指标")
    @GetMapping("/removeIndex")
    public Response removeIndex(String ids){
        iSysBusinessTotalService.removeIndex(ids);
        return Response.success();
    }

    @SysLog(title="上传图片", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "上传图片",notes = "上传图片")
    @PostMapping("/uploadPicture")
    public Response uploadPicture(@ApiParam(value = "上传文件",name = "file",required = true) MultipartFile file){
        return iSysBusinessTotalService.uploadPicture(file);
    }

    @SysLog(title="上传图片返回全路径", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "上传图片返回全路径",notes = "上传图片返回全路径")
    @PostMapping("/uploadPicture/path")
    public Response getPathByUploadPicture(@ApiParam(value = "上传文件",name = "file",required = true) MultipartFile file,HttpServletRequest request){

        String remoteHost = request.getRemoteHost();
        int serverPort = request.getServerPort();

        String path = iSysBusinessTotalService.uploadPicture(file).getMsg();

        if (!("上传失败!".equals(path))){
            HashMap<String, Object> map = new HashMap<>();
            //map.put("ip",remoteHost);
            map.put("host",remoteHost + ":"+serverPort);
            map.put("path",path);
            return Response.success().data(map);
        }
        return Response.error("上传失败!");
    }

    @SysLog(title="查看图片", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "查看图片",notes = "查看图片")
    @GetMapping("/getPicture")
    public Response getPicture(String path, HttpServletRequest request, HttpServletResponse response){
        return iSysBusinessTotalService.getPicture(path,request,response);
    }

    @SysLog(title="获取个人指标分析数据", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "获取个人指标分析数据",notes = "获取个人指标分析数据")
    @PostMapping("/getIndexData")
    public Response getIndexData(@RequestBody BusinessIndexReqDTO businessIndexReqDTO){
        return iSysBusinessTotalService.getIndexData(businessIndexReqDTO);
    }

    @SysLog(title="用户自定义修改指标模块顺序", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "用户自定义修改指标模块顺序",notes = "用户自定义修改指标模块顺序")
    @PostMapping("/updateIndexOrder")
    public Response updateIndexOrder(){

        return Response.success();
    }
}
