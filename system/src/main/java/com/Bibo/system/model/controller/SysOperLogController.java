package com.Bibo.system.model.controller;


import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.LogListDTO;
import com.Bibo.system.model.pojo.vo.LogListVO;
import com.Bibo.system.model.service.ISysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
@Api(tags = "操作日志")
@RestController
@RequestMapping("/log")
public class SysOperLogController  extends BaseController {

    @Autowired
    private ISysOperLogService operLogService;


    @SysLog(title="根据查询条件查询日志列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据查询条件查询日志列表")
    @GetMapping("/list")
    public Response<List<LogListVO>> list(LogListDTO logListDTO){
        return getDataTable(operLogService.selectLogPageList(logListDTO));
    }


    @SysLog(title="删除日志", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除日志")
    @ApiImplicitParam(value = "id",name = "id"  )
    @GetMapping("/delete")
    public Response delete(String id){
        return toResponse(operLogService.removeById(id));
    }


    @SysLog(title="清空日志", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "清空日志")
    @GetMapping("/deleteAll")
    public Response deleteAll(){
        operLogService.deleteAll();
        return success();
    }

}
