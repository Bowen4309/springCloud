package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysUserMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
public interface SysUserMessageMapper extends BaseMapper<SysUserMessage> {

    @Insert("INSERT INTO sys_user_message(user_id, message_id,create_time) SELECT user_id,#{messageId},now() FROM sys_user WHERE user_id !=#{userId}")
    int insertUserAll(@Param("messageId") String messageId, @Param("userId") String userId);

    @Insert("INSERT INTO sys_user_message(user_id, message_id,create_time) SELECT user_id,#{messageId},now() FROM sys_user WHERE user_id !=#{deptId}")
    int insertUserDeptChild(@Param("messageId") String messageId, @Param("deptId") String deptId);

    @Insert("INSERT INTO sys_user_message(user_id, message_id,create_time) " +
            "SELECT user_id,#{messageId},now() FROM sys_user WHERE dept_id in (select dept_id from sys_dept WHERE ancestors LIKE concat('%',#{deptId},'%'))")
    int insertUserDept(@Param("messageId") String messageId, @Param("deptId") String deptId);
}
