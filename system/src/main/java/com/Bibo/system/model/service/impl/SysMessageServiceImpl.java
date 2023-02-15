package com.Bibo.system.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.response.Response;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.system.model.mapper.SysMessageMapper;
import com.Bibo.system.model.mapper.SysUserMessageMapper;
import com.Bibo.system.model.pojo.dto.MessageListDTO;
import com.Bibo.system.model.pojo.entity.SysMessage;
import com.Bibo.system.model.pojo.vo.MessagePageListVO;
import com.Bibo.system.model.service.ISysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 消息相关消息表 服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

    @Autowired
    private SysUserMessageMapper userMessageMapper;

    @Override
    public List<SysMessage> getAllByTemplateId(String templateId) {
        QueryWrapper<SysMessage> qw = new QueryWrapper<>();
        qw.eq("template_id",templateId);
        List<SysMessage> sysMessages = this.baseMapper.selectList(qw);
        return sysMessages;
    }

    /**
     * 分页查询通知列表
     * @param messageListDTO 查询参数
     * @return 返回结果
     */
    @Override
    public Response selectMessagePageList(MessageListDTO messageListDTO){
        Page<SysMessage> page = new Page<>(messageListDTO.getPageNum(), messageListDTO.getPageSize());
        IPage<MessagePageListVO> messagePage = this.baseMapper.selectMessagePageList(page, messageListDTO, RedisUtil.getUserByRedis().getUserId());
//
//        List<MessagePageListVO> messagePageList = (List<MessagePageListVO>) messagePage.getRecords().stream().map(m -> {
//            MessagePageListVO messagePageListVO = new MessagePageListVO();
//            BeanUtil.copyProperties(m, messagePageListVO);
//            return messagePageListVO;
//        }).collect(Collectors.toList());
        return Response.success().data(messagePage.getRecords(), messagePage.getTotal(), messagePage.getPages());
    }


    /**
     * 发送消息
     * @param messageId 发送消息ID
     * @param type 发送类型
     * @param user 发送用户
     */
    @Override
    @Async
    @Transactional
    public void senderUserMessage(String messageId, String type, LoginUser user){
        switch (type){
            case "1":
                userMessageMapper.insertUserAll(messageId, user.getUserId());
            case "2":
                userMessageMapper.insertUserDeptChild(messageId, user.getDept().getDeptId());
            case "3":
                userMessageMapper.insertUserDept(messageId, user.getDept().getDeptId());
        }
    }

    @Override
    public void deleteUserAllMessage() {
        LoginUser userByRedis = RedisUtil.getUserByRedis();
        String userId = userByRedis.getUserId();
        this.baseMapper.deleteAllMessage(userId);
    }

}
