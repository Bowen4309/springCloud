package com.Bibo.system.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysUser;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.DateUtils;
import com.Bibo.system.model.mapper.SysMessageTemplateMapper;
import com.Bibo.system.model.pojo.dto.MyMessageReqDTO;
import com.Bibo.system.model.pojo.dto.SysMessageDTO;
import com.Bibo.system.model.pojo.dto.SysMessageTemplateDTO;
import com.Bibo.system.model.pojo.dto.SysMessageTemplateListReqDTO;
import com.Bibo.system.model.pojo.entity.SysMessage;
import com.Bibo.system.model.pojo.entity.SysMessageTemplate;
import com.Bibo.system.model.service.ISysMessageService;
import com.Bibo.system.model.service.ISysMessageTemplateService;
import com.Bibo.system.model.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysMessageTemplateServiceImpl extends ServiceImpl<SysMessageTemplateMapper, SysMessageTemplate> implements ISysMessageTemplateService {

    @Autowired
    SysMessageTemplateMapper templateMapper;

    @Autowired
    ISysMessageService messageService;
    
    @Autowired
    ISysUserService sysUserService;
    

    @Override
    public Response saveOrUpdateMessageTemplate(SysMessageTemplateDTO dto) {
        if(StringUtils.isBlank(dto.getId())){
            //新增
            SysMessageTemplate template = new SysMessageTemplate();
            BeanUtils.copyProperties(dto,template);
            template.setCreateTime(new Date());
            template.setUpdateTime(new Date());
            template.setStatus(2);  //新建是停用状态
            templateMapper.insert(template);
        }else{
            //修改
            //判断是否允许修改
            String id = dto.getId();
            SysMessageTemplate template = templateMapper.selectById(id);
            if(template.getSendTime().before(new Date())){
                return Response.error("已过推送时间【" + template.getSendTime() + "】，不能修改！");
            }

            BeanUtils.copyProperties(dto,template);
            template.setUpdateTime(new Date());
            template.setStatus(2);  //修改是停用状态
            templateMapper.updateById(template);
        }
        return Response.success();
    }

    @Override
    public Response findTemplateById(String id) {
        SysMessageTemplate template = templateMapper.selectById(id);
        SysMessageTemplateDTO dto = new SysMessageTemplateDTO();
        BeanUtils.copyProperties(template,dto);
        return Response.success().data(dto);
    }

    @Override
    public Response selectTemplateList(SysMessageTemplateListReqDTO dto) {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotBlank(dto.getContent())){
            qw.like("content",dto.getContent());
        }
        if(StringUtils.isNotBlank(dto.getReceiveName())){
            qw.like("receive_name",dto.getReceiveName());
        }
        if(StringUtils.isNotBlank(dto.getSendName())){
            qw.like("send_name",dto.getSendName());
        }
        if(StringUtils.isNotBlank(dto.getTitle())){
            qw.like("title",dto.getTitle());
        }
        if(null != dto.getReceiveType()){
            qw.eq("receive_type",dto.getReceiveType());
        }
        if(null != dto.getStatus()){
            qw.eq("status",dto.getStatus());
        }

        if(StringUtils.isNotBlank(dto.getEndDate())){
            qw.le("create_time", DateUtils.getDateByStr(dto.getEndDate()));
        }
        if(StringUtils.isNotBlank(dto.getStartDate())){
            qw.ge("create_time",DateUtils.getDateByStr(dto.getStartDate()));
        }

        qw.orderByDesc("update_time");

        Page page = new Page(dto.getPageNum(),dto.getPageSize());
        IPage<SysMessageTemplate> dtoPage = this.page(page,qw);
        List<SysMessageTemplateDTO> data = new ArrayList<SysMessageTemplateDTO>();
        dtoPage.getRecords().forEach(record -> {
            //业务指标对象转化DTO
            SysMessageTemplateDTO templateDTO= new SysMessageTemplateDTO();
            BeanUtils.copyProperties(record,templateDTO);
            data.add(templateDTO);
        });
        return Response.success().data(data,dtoPage.getTotal(),dtoPage.getPages());
    }

    @Override
    public Response
    updateTemplateStatus(String id, Integer status) {

//        if(null == status || (status != 2 && status != 3)){
//            return Response.error("状态值错误");
//        }
        SysMessageTemplate template = templateMapper.selectById(id);
        Integer oldStatus = template.getStatus();

        //由停用变启用
        if(oldStatus == 2 && status == 1){
            if(template.getSendTime().before(new Date())){
                return Response.error("已过推送时间【" + template.getSendTime() + "】，不能启用！");
            }

            //查询是否已经生成消息
            List<SysMessage> allByTemplateId = messageService.getAllByTemplateId(template.getId());
            if(allByTemplateId != null && allByTemplateId.size() > 0){
                //生成过消息
                for (SysMessage message : allByTemplateId) {
                    message.setStatus(status);
                    messageService.updateById(message);
                }
            }else{
                //没有生成过消息
                if(StringUtils.isNotBlank(template.getReceiveUserId())){
                    //个人
                    String[] userIds = template.getReceiveUserId().split(",");
                    for (String userId : userIds) {
                        SysMessage message = new SysMessage();
                        BeanUtils.copyProperties(template,message);
                        message.setTemplateId(template.getId());
                        message.setCreateTime(new Date());
                        message.setReadStatus(1); //未读
                        message.setId(null);
                        message.setReceiveId(userId);
                        messageService.saveOrUpdate(message);
                    }


                }
                if(StringUtils.isNotBlank(template.getReceiveDeptId())){
                    //部门
                    String[] split = template.getReceiveDeptId().split(",");
                    for (String deptId : split) {
                        List<SysUser> users = sysUserService.getAllByDeptId(deptId);
                        for (SysUser user : users) {
                            SysMessage message = new SysMessage();
                            BeanUtils.copyProperties(template,message);
                            message.setTemplateId(template.getId());
                            message.setCreateTime(new Date());
                            message.setReadStatus(1); //未读
                            message.setId(null);
                            message.setReceiveName(user.getUserName());
                            message.setReceiveId(user.getUserId());
                            messageService.saveOrUpdate(message);
                        }
                    }
                }
            }
        }

        //由启用变停用
        if(oldStatus == 2 && status == 1){
            List<SysMessage> allByTemplateId = messageService.getAllByTemplateId(template.getId());
            for (SysMessage message : allByTemplateId) {
                if(message.getReadStatus() == 1){
                    //已读状态不改
                    message.setStatus(status);
                    messageService.updateById(message);
                }
            }
        }

        template.setStatus(status);
        templateMapper.updateById(template);
        return Response.success();
    }

    @Override
    public Response findMessageByUserId(MyMessageReqDTO dto) {
//        if(StringUtils.isBlank(dto.getUserId())){
//            return Response.error("userId不能为空");
//        }

        if(StringUtils.isBlank(dto.getUserId())){
            LoginUser userByRedis = RedisUtil.getUserByRedis();
            String userId = userByRedis.getUserId();
            dto.setUserId(userId);
        }



        QueryWrapper<SysMessage> qw = new QueryWrapper<>();
        qw.eq("receive_id",dto.getUserId());
        if(null != dto.getStatus()){
            qw.eq("read_status",dto.getStatus());
        }

        if(StringUtils.isNotBlank(dto.getEndDate())){
            qw.le("update_time", DateUtils.getDateByStr(dto.getEndDate()));
        }
        if(StringUtils.isNotBlank(dto.getStartDate())){
            qw.ge("update_time",DateUtils.getDateByStr(dto.getStartDate()));
        }

        //已读状态
        qw.eq(false,"status",2);
        //未删状态
        qw.eq("delete_status",1);


        qw.orderByAsc("read_status");
        qw.orderByDesc("send_time");


        Page page = new Page(dto.getPageNum(),dto.getPageSize());
        IPage<SysMessage> dtoPage = messageService.page(page,qw);
        List<SysMessageDTO> data = new ArrayList<SysMessageDTO>();
        dtoPage.getRecords().forEach(record -> {
            //业务指标对象转化DTO
            SysMessageDTO messageDTO= new SysMessageDTO();
            BeanUtils.copyProperties(record,messageDTO);
            data.add(messageDTO);
        });
        return Response.success().data(data,dtoPage.getTotal(),dtoPage.getPages());

    }

    @Override
    public Response updateReadStatus(String messageId) {
        SysMessage byId = messageService.getById(messageId);
        byId.setReadStatus(2);
        messageService.updateById(byId);
        return Response.success();
    }

    @Override
    public Response deleteMessage(String messageIds) {
        String[] split = messageIds.split(",");
        for (String messageId : split) {
            SysMessage byId = messageService.getById(messageId);
            byId.setDeleteStatus(2);
            messageService.updateById(byId);
        }
        return Response.success();
    }

    @Override
    public Response deleteAllMessage() {
        messageService.deleteUserAllMessage();
        return Response.success();
    }
}
