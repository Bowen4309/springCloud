package com.Bibo.system.model.service;

import com.Bibo.system.model.pojo.dto.SysMessageTemplateDTO;
import com.Bibo.system.model.pojo.dto.SysMessageTemplateListReqDTO;
import com.Bibo.system.model.pojo.entity.SysMessageTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.MyMessageReqDTO;

public interface ISysMessageTemplateService extends IService<SysMessageTemplate> {

    Response saveOrUpdateMessageTemplate(SysMessageTemplateDTO dto);

    Response findTemplateById(String id);

    Response selectTemplateList(SysMessageTemplateListReqDTO dto);

    Response updateTemplateStatus(String id, Integer status);

    Response findMessageByUserId(MyMessageReqDTO dto);

    Response updateReadStatus(String messageId);

    Response deleteMessage(String messageIds);

    Response deleteAllMessage();


}
