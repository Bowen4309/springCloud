package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量新增用户角色信息
     * @param userRoles
     * @return
     */
    @Insert("insert into sys_user_role(user_id, role_id) values ${userRoles}")
    public int batchUserRole(@Param("userRoles") String userRoles);


    /**
     * 批量取消用户角色信息
     * @return
     */
    @Delete("delete from sys_user_role where role_id=#{roleId} and user_id in #{userIds}")
    public int deleteUserRoleInfos(@Param("roleId") String roleId, @Param("userIds") String userIds);

}
