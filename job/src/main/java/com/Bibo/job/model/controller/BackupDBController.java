package com.Bibo.job.model.controller;

import com.Bibo.common.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "数据库备份数据或更新定时任务")
@RestController
@RequestMapping("/doDB")
public class BackupDBController {

    @ApiOperation(value = "数据库备份",notes = "备份数据库数据定时任务")
    @GetMapping(value = "/backup")
    public Response backup(){


        return Response.success();
    }

}
