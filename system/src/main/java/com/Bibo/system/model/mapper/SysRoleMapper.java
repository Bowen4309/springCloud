package com.Bibo.system.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.common.pojo.entity.SysRole;
import com.Bibo.common.pojo.vo.RoleListVO;
import com.Bibo.system.model.pojo.dto.RoleListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 分页查询角色列表
     * @param role 角色条件
     * @return 结果
     */
//    @Select("<script>" +
//            "SELECT r.*,dd2.dict_label status_name FROM sys_role r " +
//            "LEFT JOIN sys_dict_data dd2 ON r.status = dd2.dict_value AND dd2.dict_type = 'sys_object_status' \n" +
//            "WHERE  r.status='0' " +
//            "<if test=\"role.roleName != null and role.roleName != ''\">AND r.role_name LIKE concat('%',#{role.roleName},'%') </if> " +
//            "</script>")
    public IPage<RoleListVO> selectRolePageList(Page page, @Param("role") RoleListDTO role);

    /**
     * 根据用户名称查询角色
     * @param userName 用户id
     * @return 角色列表
     */
    @Select("select DISTINCT r.role_id,r.role_name,r.role_key,r.data_scope,r.status,r.create_time,r.remark FROM sys_role r \n" +
            "LEFT JOIN sys_user_role ur on ur.role_id=r.role_id \n" +
            "LEFT JOIN sys_user u on u.user_id=ur.user_id\n" +
            "LEFT JOIN sys_dept d on u.dept_id=d.dept_id\n" +
            "where r.status='0' and u.user_name=#{userName}")
    public List<SysRole> selectRolesByUserName(@Param("userName") String userName);


    /**
     * 根据用户id获取角色悬着框列表
     * @param userId 用户id
     * @return 角色id列表
     */
    @Select("select  r.role_id FROM sys_role r \n" +
            "LEFT JOIN sys_user_role ur on ur.role_id=r.role_id \n" +
            "LEFT JOIN sys_user u on u.user_id=ur.user_id\n" +
            "LEFT JOIN sys_dept d on u.dept_id=d.dept_id\n" +
            "WHERE u.user_id=#{userId}")
    public String[] selectRoleListByUserId(@Param("userId") String userId);


    /**
     * 根据用户id查询角色
     * @param userId 用户id
     * @return 角色列表
     */
    @Select("select DISTINCT r.role_id,r.role_name,r.role_key,r.data_scope,r.status,r.create_time,r.remark FROM sys_role r \n" +
            "LEFT JOIN sys_user_role ur on ur.role_id=r.role_id \n" +
            "LEFT JOIN sys_user u on u.user_id=ur.user_id\n" +
//            "LEFT JOIN sys_dept d on u.dept_id=d.dept_id\n" +
            "WHERE r.status='0' and ur.user_id=#{userId}")
    public List<SysRole> selectRolesByUserId(@Param("userId") String userId);

    /**
     * 查询角色列表的权限列表
     * @param sql 拼接SQL
     * @return 权限ID列表
     */
    @Select("select distinct power_id from sys_role_power where role_id in (${sql})")
    public Set<String> selectRolePower(@Param("sql") String sql);



}
