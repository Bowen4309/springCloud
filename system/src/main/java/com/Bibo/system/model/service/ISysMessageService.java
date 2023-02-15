package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.MessageListDTO;
import com.Bibo.system.model.pojo.entity.SysMessage;

import java.util.List;

/**
 * <p>
 * 消息相关消息表 服务类
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
public interface ISysMessageService extends IService<SysMessage> {


    List<SysMessage> getAllByTemplateId(String templateId);


    /**
     * 分页查询通知列表
     * @param messageListDTO 查询参数
     * @return 返回结果
     */
    Response selectMessagePageList(MessageListDTO messageListDTO);

    /**
     * 发送消息
     * @param messageId 发送消息ID
     * @param type 发送类型
     * @param user 发送用户
     */
    void senderUserMessage(String messageId, String type, LoginUser user);

    void deleteUserAllMessage();
}
