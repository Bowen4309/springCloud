package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.system.model.pojo.dto.RoleUsersDTO;
import com.Bibo.common.pojo.entity.SysRole;
import com.Bibo.common.pojo.vo.RoleListVO;
import com.Bibo.system.model.pojo.dto.RoleDTO;
import com.Bibo.system.model.pojo.dto.RoleListDTO;
import com.Bibo.system.model.pojo.dto.RolePowerDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     * @param roleListDTO 角色条件
     * @return 结果
     */
    public IPage<RoleListVO> selectRolePageList(RoleListDTO roleListDTO);

    /**
     * 新增或修改角色信息
     * @param role 角色信息
     * @return 结果
     */
    public int saveOrUpdateRole(RoleDTO role);

    /**
     * 校验角色名称是否唯一
     * @param role 角色信息
     * @return 结果
     */
    public Boolean checkRoleNameUnique(RoleDTO role);


    /**
     * 新增角色与权限信息
     * @param role 角色权限
     * @return 结果
     */
    public void insertRolePower(RolePowerDTO role);


//    /**
//     * 校验角色权限名称是否唯一
//     * @param role 角色信息
//     * @return 结果
//     */
//    public Boolean checkRoleKeyUnique(RoleDTO role);

    /**
     * 根据用户id查询角色
     * @param userId 用户id
     * @return 权限列表
     */
    public Set<String> selectRoleKeys(String userId);

    /**
     * 根据用户id查询角色
     * @param userId 用户id
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserId(String userId);

    /**
     * 根据用户id获取角色选择框列表
     * @param userId 用户id
     * @return 角色id列表
     */
    public String[] selectRoleListByUserId(String userId);

    /**
     * 取消授权用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return 结果
     */
    public int deleteAuthUser(String userId, String roleId);

    /**
     * 批量取消授权用户角色
     * @return 结果
     */
    public int deleteAuthUsers(RoleUsersDTO roleUsersDTO);

    /**
     * 批量选择授权用户角色
     * @return 结果
     */
    public int insertAuthUsers(RoleUsersDTO roleUsersDTO);


}
