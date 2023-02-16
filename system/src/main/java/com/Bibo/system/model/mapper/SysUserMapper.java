package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.dto.UserListDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysUser;
import com.Bibo.system.model.pojo.vo.UserListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 条件分页查询用户列表
     * @param user 用户信息
     * @return 结果
     */
    @Select("<script>" +
            "SELECT u.user_id,u.user_name,u.user_code,u.police_code,r.role_id,r.role_name role_name,u.dept_id,d.dept_name,dd1.dict_label status_name,u.status,u.create_by,u.create_time,u.update_by,u.update_time \n" +
            "FROM sys_user u LEFT JOIN sys_dept d ON u.dept_id=d.dept_id \n" +
            "LEFT JOIN sys_user_role ur ON ur.user_id=u.user_id \n" +
            "LEFT JOIN sys_role r ON ur.role_id=r.role_id \n" +
            "LEFT JOIN sys_dict_data dd1 ON dd1.dict_value=u.status AND dd1.dict_type='sys_object_status' \n" +
            "WHERE 1=1 " +
            "<if test=\"user.userName != null and user.userName != ''\">AND u.user_name LIKE concat('%',#{user.userName},'%') </if>\n" +
            "<if test=\"user.policeCode != null and user.policeCode != ''\">AND u.police_code = #{policeCode} </if>\n" +
            "<if test=\"user.deptId != null and user.deptId != ''\">AND u.dept_id = #{deptId} </if>\n" +
            "<if test=\"user.gridId != null and user.gridId != ''\">AND u.grid_id = #{gridId} </if>\n" +
            "<if test=\"user.startDate != null and user.startDate != ''\"> " +
            " and u.update_time &gt;=  to_timestamp(${user.startDate}/1000) " +
            "</if>" +
            " <if test=\"user.endDate != null and user.endDate != ''\"> " +
            " and c.update_time &lt;=  to_timestamp(${user.endDate}/1000) " +
            "</if>" +
            "<foreach collection='user.roleIds' item = 'item' index='index' open='(' close=')' separator = ' and ' >" +
            " r.role_id = #{item} " +
            "</foreach>"+
            "ORDER BY u.create_time DESC,u.update_time DESC" +
            "</script>")
    public IPage<UserListVO> selectUserPageList(Page page, @Param("user") UserListDTO user);

    /**
     * 获取用户的菜单路由
     * @param type 类型(1菜单 2目录 3按钮)
     * @param name 名称
     * @param userId 用户ID
     */
    @Select("<script>" +
            "SELECT p.component_name from sys_power p " +
            "LEFT JOIN sys_role_power rp on rp.power_id=p.power_id " +
            "LEFT JOIN sys_role r ON rp.role_id=r.role_id  " +
            "LEFT JOIN sys_user_role ur ON ur.user_id=#{userId}" +
            "WHERE p.power_type=#{type} " +
            "<if test=\"name != null and name != ''\">AND p.parent_id=(SELECT power_id FROM sys_power WHERE component_name=#{name})  </if> " +
            "</script>")
    public Set<String> selectRoutersByUserId(@Param("type") String type, @Param("name") String name, @Param("userId") String userId);


    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 结果
     */
    public LoginUser selectUserByUserName(String username);


    /**
     * 获取用户的菜单路由
     * @param userId 用户ID
     */
    @Select("<script>" +
            "SELECT p.ancestors from sys_power p \n" +
            "LEFT JOIN sys_user_power up ON up.power_id=p.power_id " +
            "where up.user_id=#{userId} and p.system_type='1'" +
            "</script>")
    List<String> selectRouterByUserId(String userId);




    /**
     * 根据用户名查询用户信息
     * @param policeCode 警员编号
     * @return 结果
     */
    public LoginUser selectLoginUserCode(@Param("policeCode") String policeCode);


    @Select("select *from trff_app.v_bas_police_sjspb where JYLX = '1' ")
    List<Map<String,Object>> getPoliceData();
}
