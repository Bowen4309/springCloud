package com.Bibo.system.model.controller;


import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.MyMessageReqDTO;
import com.Bibo.system.model.pojo.dto.SysMessageDTO;
import com.Bibo.system.model.pojo.dto.SysMessageTemplateDTO;
import com.Bibo.system.model.pojo.dto.SysMessageTemplateListReqDTO;
import com.Bibo.system.model.service.ISysMessageTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 消息相关消息表 前端控制器
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
@Api(tags = "消息通知")
@RestController
@RequestMapping("/message/template")
public class SysMessageController extends BaseController {

    @Autowired
    private ISysMessageTemplateService templateService;


    @SysLog(title="新增或修改消息模板", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "新增或修改消息模板")
    @PostMapping("/saveOrUpdate")
    Response saveOrUpdateMessageTemplate(@RequestBody SysMessageTemplateDTO dto){
        return templateService.saveOrUpdateMessageTemplate(dto);
    }

    @SysLog(title="根据主键id查询消息模板", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据主键id查询消息模板")
    @GetMapping("/template/id")
    Response<SysMessageTemplateDTO> findTemplateById(String id){
        return templateService.findTemplateById(id);
    }

    @SysLog(title="查询消息模板分页列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "查询消息模板分页列表")
    @PostMapping("/template/page")
    Response<List<SysMessageTemplateDTO>> selectTemplateList(@RequestBody SysMessageTemplateListReqDTO dto){
        return templateService.selectTemplateList(dto);
    }

    @SysLog(title="消息模板启用或停用", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "消息模板启用或停用")
    @GetMapping("/template/status")
    Response updateTemplateStatus(String id, Integer status){
        return templateService.updateTemplateStatus(id, status);
    }

    @SysLog(title="用户消息列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "用户消息列表")
    @PostMapping("/user/message")
    Response<List<SysMessageDTO>> findMessageByUserId(@RequestBody MyMessageReqDTO dto){
        return templateService.findMessageByUserId(dto);
    }

    @SysLog(title="用户消息改为已读", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "用户消息改为已读")
    @GetMapping("/message/read")
    Response updateReadStatus(String messageId){
        return templateService.updateReadStatus(messageId);
    }

    @SysLog(title="用户删除消息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "用户删除消息")
    @GetMapping("/message/delete")
    Response deleteMessage(String messageIds){
        return templateService.deleteMessage(messageIds);
    }

    @SysLog(title="用户删除所有消息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "用户删除所有消息")
    @GetMapping("/message/deleteAll")
    Response deleteAllMessage(){
        return templateService.deleteAllMessage();
    }







//    @SysLog(title="根据查询条件查询消息通知列表", operatorType= OperatorTypeEnum.SEARCH)
//    @ApiOperation(value = "根据查询条件查询消息通知列表")
//    @GetMapping("/list")
//    public Response<MessagePageListVO> list(MessageListDTO messageListDTO){
//        return messageService.selectMessagePageList(messageListDTO);
//    }
//
//    @SysLog(title="根据消息通知id获取详细信息", operatorType= OperatorTypeEnum.SEARCH)
//    @ApiOperation(value = "根据消息通知id获取详细信息")
//    @GetMapping("/get")
//    @ApiImplicitParam(value = "id",name = "id",required = false)
//    public Response<DictListVO> getInfo(String id){
//        MessageVO messageVO = new MessageVO();
//        BeanUtil.copyProperties(messageService.getById(id), messageVO);
//        return success().data(messageVO);
//    }
//
//    @SysLog(title="新增消息通知", operatorType= OperatorTypeEnum.SEARCH)
//    @ApiOperation(value = "新增消息通知")
//    @PostMapping("/add")
//    public Response add(@RequestBody MessageDTO messageDTO){
//        LoginUser user = RedisUtil.getUserByRedis();
//
//        SysMessage message = new SysMessage();
//        BeanUtil.copyProperties(messageDTO, message);
//        String id = UUID.randomUUID().toString().replace("-","");
////        message.setMessageId(id);
////        message.setSender(user.getUserId());
////        message.setSenderName(user.getUserName());
////        message.setSenderDept(user.getDept().getDeptId());
//        message.setCreateTime(new Date());
//        boolean row = messageService.save(message);
//        messageService.senderUserMessage(id, messageDTO.getReceiver(), user);
//        return toResponse(row);
//    }
//
//
//    @SysLog(title="删除消息通知", operatorType= OperatorTypeEnum.DELETE)
//    @ApiOperation(value = "删除消息通知")
//    @ApiImplicitParam(value = "id",name = "id",required = true)
//    @GetMapping("/delete")
//    public Response delete(String id){
//        return toResponse(messageService.update(
//                new UpdateWrapper<SysMessage>().set("status","2").eq("message_id",id)));
//    }







}
