package com.Bibo.job.model.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.job.model.dto.TaskListDTO;
import com.Bibo.job.model.entity.QuartzTaskErrors;
import com.Bibo.job.model.entity.QuartzTaskInformations;
import com.Bibo.job.model.service.IQuartzService;
import com.Bibo.job.model.vo.QuartzTaskRecordsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "定时任务")
@RestController
@RequestMapping("/quartz")
public class QuartzController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(QuartzController.class);

    @Autowired
    private IQuartzService quartzService;

    @ApiOperation(value = "定时任务分页列表",notes = "定时任务分页列表")
    @PostMapping(value = "/task/page")
    public Response pageTask(@RequestBody TaskListDTO dto) {
        IPage<QuartzTaskInformations> taskList = quartzService.getTaskList(dto);
        return success().data(taskList.getRecords(),taskList.getTotal(),taskList.getPages());
    }

    @ApiOperation(value = "添加定时任务",notes = "添加定时任务")
    @PostMapping(value = "/task/add")
    public Response addTask(@RequestBody QuartzTaskInformations taskInfo) {
        return toResponse(quartzService.addTask(taskInfo));
    }

    @ApiOperation(value = "根据id获取定时任务详情",notes = "根据id获取定时任务详情")
    @GetMapping(value = "/task/get")
    public Response getTask(@RequestParam(value = "id") String id) {
        return success().data(quartzService.getTaskById(id));
    }

    @ApiOperation(value = "编辑定时任务",notes = "编辑定时任务")
    @PostMapping(value = "/task/edit")
    public Response editTask(@RequestBody QuartzTaskInformations taskInfo) {
        return toResponse(quartzService.updateTask(taskInfo));
    }

    @ApiOperation(value = "启动或者暂停定时任务",notes = "启动或者暂停定时任务")
    @GetMapping(value = "/task/option")
    public Response optionJob(@RequestParam(value = "taskNo", required = true) String taskNo) {
        try {
            return quartzService.startJob(taskNo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return error();
    }

    @ApiOperation(value = "定时任务执行情况",notes = "定时任务执行情况")
    @GetMapping(value = "/task/records")
    public Response<List<QuartzTaskRecordsVo>> taskRecordsPage(@RequestParam(value = "taskNo") String taskNo) {
        return success().data(quartzService.taskRecords(taskNo));
    }

    @ApiOperation(value = "立即运行一次定时任务",notes = "立即运行一次定时任务")
    @GetMapping(value = "/task/run")
    public Response runTaskRightNow(@RequestParam(value = "taskNo") String taskNo) {
        return quartzService.runTaskRightNow(taskNo);
    }

    @ApiOperation(value = "定时任务失败详情",notes = "定时任务失败详情")
    @GetMapping(value = "/task/errors")
    public Response<QuartzTaskErrors> detailTaskErrors(@RequestParam(value = "recordId") String recordId) {
        return success().data(quartzService.detailTaskErrors(recordId));
    }

    /**
     *
     * @return
     */
    @PostMapping(value = "/task/test")
    public Response test001() {
        return success().data("=== 访问成功 ===");
    }
}
