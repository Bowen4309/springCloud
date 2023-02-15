package com.Bibo.system.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.common.pojo.entity.SysPower;
import com.Bibo.system.model.pojo.dto.PowerDTO;
import com.Bibo.system.model.pojo.dto.PowerListDTO;
import com.Bibo.system.model.pojo.vo.PowerListVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface SysPowerMapper extends BaseMapper<SysPower> {

//    /**
//     * 查询系统菜单列表
//     * @param power 菜单信息
//     * @return 菜单列表
//     */
//    @Select("<script>" +
//            "SELECT power_id,power_name,parent_id,order_num,path,power_type,visible,status,icon,code,create_time FROM sys_power where 1=1 \n" +
//            "<if test=\"power.powerName != null and power.powerName != ''\">AND power_name like concat('%',#{power.powerName},'%') </if> \n" +
//            "<if test=\"power.visible != null and power.visible != ''\">AND visible=#{power.visible} </if> \n" +
//            "<if test=\"power.status != null and power.status != ''\">AND status=#{power.status} </if> \n" +
//            "ORDER BY parent_id,order_num" +
//            "</script>")
//    public List<SysPower> selectPowerList(@Param("power") SysPower power);



    /**
     * 条件分页查询权限列表
     * @param powerListDTO 权限条件
     * @return 结果
     */
    @Select("<script>" +
            "SELECT p.* FROM sys_power p  " +
            "WHERE  p.status='0' " +
            "<if test=\"power.powerName != null and power.powerName != ''\">AND p.power_name LIKE concat('%',#{power.powerName},'%') </if> " +
            "order by p.create_time DESC" +
            "</script>")
    public IPage<PowerListVO> selectPowerPageList(Page page, @Param("power") PowerListDTO powerListDTO);

    /**
     * 根据用户查询系统菜单列表
     * @param power 菜单信息
     * @param userId 用户id
     * @return 菜单列表
     */
    @Select("<script>" +
            "SELECT p.power_id,p.power_name,p.parent_id,p.path,p.power_type,p.visible,p.status,p.icon,p.code,p.create_time FROM sys_power p \n" +
            "LEFT JOIN sys_role_power rp on rp.power_id=p.power_id \n" +
            "LEFT JOIN sys_user_role ur on ur.role_id=rp.role_id\n" +
            "LEFT JOIN sys_role r on r.role_id = ur.role_id \n" +
            "where p.power_type!='3'  and ur.user_id = #{userId} \n" +
            "<if test=\"power.powerName != null and power.powerName != ''\">AND p.power_name like concat('%',#{power.powerName},'%') </if> \n" +
            "ORDER BY p.parent_id" +
            "</script>")
    public List<SysPower> selectPowerList(@Param("power") PowerDTO power, @Param("userId") String userId);

    /**
     * 根据用户id查询权限
     * @param userId 用户id
     * @return 权限列表
     */
    @Select("SELECT DISTINCT p.component_name from sys_power p \n" +
            "LEFT JOIN sys_user_power up on up.power_id=p.power_id \n" +
            "where p.status='0' and p.power_type!='3' and up.user_id=#{userId}")
    public List<String> selectMenuPermsByUserId(@Param("userId") String userId);


    /**
     * 根据角色id查询菜单树信息
     * @param roleId 角色id
     * @return 选中菜单列表
     */
    @Select("SELECT p.power_id FROM sys_power p \n" +
            "LEFT JOIN sys_role_power rp on rp.power_id=p.power_id \n" +
            "where rp.role_id = #{roleId} \n" +
            "ORDER BY p.parent_id")
    public List<String> selectPowerListByRoleId(@Param("roleId") String roleId);


    /**
     * 根据用户查询菜单
     * @param userId 用户id
     * @return 菜单列表
     */
    @Select("SELECT DISTINCT p.power_id,p.parent_id,p.component,p.component_name,p.power_name,p.code,p.path,p.icon,p.visible,p.status,p.create_time,p.power_type FROM sys_power p\n" +
            "LEFT JOIN sys_role_power rp on rp.power_id=p.power_id \n" +
            "LEFT JOIN sys_user_role ur on ur.role_id=rp.role_id\n" +
            "LEFT JOIN sys_role r on r.role_id = ur.role_id \n" +
            "LEFT JOIN sys_user u on u.user_id = ur.user_id \n" +
            "where u.user_id=#{userId} and p.status='0' and r.status='0' \n" +
            "ORDER BY p.parent_id ")
    public List<SysPower> selectPowerTreeByUserId(@Param("userId") String userId);
}
