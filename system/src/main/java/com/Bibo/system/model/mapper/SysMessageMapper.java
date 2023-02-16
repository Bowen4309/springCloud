package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.system.model.pojo.dto.MessageListDTO;
import com.Bibo.system.model.pojo.vo.MessagePageListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 消息相关消息表 Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
public interface SysMessageMapper extends BaseMapper<SysMessage> {



    /**
     * 分页查询通知列表
     * @param messageListDTO 查询参数
     * @return 返回结果
     */
    @Select("<script>" +
            "SELECT m.message_id,m.sender_name,m.create_time,m.title,um.status FROM sys_message m " +
            "LEFT JOIN sys_user_message um ON um.message_id = M .message_id " +
            "<if test=\"message.status != null and message.status != ''\">AND um.status=#{message.status} </if> " +
            "WHERE m.status='0'" +
            "<if test=\"message.title != null and message.title != ''\">AND m.title LIKE concat('%',#{message.title},'%') </if> " +
            "<if test=\"message.senderName != null and message.senderName != ''\">AND m.senderName LIKE concat('%',#{message.senderName},'%') </if> " +
            "and ( um.user_id=#{userId} or m.sender=#{userId} ) " +
            "order by m.create_time DESC" +
            "</script>")
    public IPage<MessagePageListVO> selectMessagePageList(Page page, @Param("message") MessageListDTO messageListDTO, @Param("userId") String userId);


    @Delete("update sys_message set delete_status = 2 where receive_id = #{userId}")
    void deleteAllMessage(@Param("userId") String userId);
}
