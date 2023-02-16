package com.Bibo.system.model.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.Bibo.system.model.pojo.entity.SysRolePower;
import com.Bibo.system.model.pojo.entity.SysUserPower;
import com.Bibo.system.model.pojo.entity.SysUserRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.pojo.entity.SysRole;
import com.Bibo.common.pojo.vo.RoleListVO;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.system.model.mapper.SysRoleMapper;
import com.Bibo.system.model.mapper.SysRolePowerMapper;
import com.Bibo.system.model.mapper.SysUserPowerMapper;
import com.Bibo.system.model.mapper.SysUserRoleMapper;
import com.Bibo.system.model.pojo.dto.RoleDTO;
import com.Bibo.system.model.pojo.dto.RoleListDTO;
import com.Bibo.system.model.pojo.dto.RolePowerDTO;
import com.Bibo.system.model.pojo.dto.RoleUsersDTO;
import com.Bibo.system.model.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRolePowerMapper rolePowerMapper;

    @Autowired
    private SysUserPowerMapper userPowerMapper;


    /**
     * 分页查询角色列表
     * @param roleListDTO 角色条件
     * @return 结果
     */
    @Override
    public IPage<RoleListVO> selectRolePageList(RoleListDTO roleListDTO){
        Page<RoleListVO> page = new Page(roleListDTO.getPageNum(), roleListDTO.getPageSize());
        return this.baseMapper.selectRolePageList(page, roleListDTO);
    }


    /**
     * 新增修改角色信息
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int saveOrUpdateRole(RoleDTO role){
        SysRole r = new SysRole();
        BeanUtil.copyProperties(role, r);
        r.setStatus("0");
        if (ObjectUtil.isNull(r.getRoleId())){
            // 后期添加userid获取
            r.setCreateBy(RedisUtil.getUserByRedis().getUserName());
            r.setCreateTime(new Date());
        }else {
            // 后期添加userid获取
            r.setUpdateBy(RedisUtil.getUserByRedis().getUserName());
            r.setUpdateTime(new Date());
        }
        // 新增或修改
        this.saveOrUpdate(r);
        if (ObjectUtil.isEmpty(role.getRoleId())){
            role.setRoleId(r.getRoleId());
        }
        // 删除角色与权限的关系
        rolePowerMapper.delete(
                new QueryWrapper<SysRolePower>().eq("role_id", role.getRoleId()));
//        if (ObjectUtil.isNotNull(role.getPowerIds()) && role.getPowerIds().length>0){
//            return insertRolePower(role);
//        }
        return 1;
    }

    /**
     * 新增角色与权限信息
     * @param role 角色权限
     * @return 结果
     */
    @Transactional
    @Override
    public void insertRolePower(RolePowerDTO role){
        // 清空原先的角色与权限关系
        rolePowerMapper.delete(new QueryWrapper<SysRolePower>().eq("role_id", role.getRoleId()));
        // 新增角色与权限关系
        StringBuilder str = new StringBuilder();
        for (String powerId:role.getPowerIds()){
            str.append("('").append(role.getRoleId()).append("','").append(powerId).append("')").append(",");
        }
        if (str.length()>0){
            rolePowerMapper.insertRolePower(str.substring(0,str.length()-1));
        }
        //查询该角色的用户，并重新配置用户的权限
        List<SysUserRole> userRoles =userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("role_id",role.getRoleId()));
        List<String> userIds = new ArrayList<>();
        userRoles.forEach(userRole ->{
            userIds.add(userRole.getUserId());
        });
        //清空之前用户的角色权限
        userPowerMapper.delete(new QueryWrapper<SysUserPower>().in("user_id",userIds));
        //获取新的角色权限,重新配置用户的角色权限
        Set<String> powerList = this.baseMapper.selectRolePower("'"+ role.getRoleId()+"'");
        StringBuilder rolePowerList = new StringBuilder();
        userIds.forEach(userId ->{
            for (String powerId : powerList){
                rolePowerList.append("('").append(userId).append("','").append(powerId).append("'),");
            }
            //配置用户权限
            userPowerMapper.authUserPower(rolePowerList.substring(0, rolePowerList.length()-1));
        });

    }

    /**
     * 校验角色名称是否唯一
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public Boolean checkRoleNameUnique(RoleDTO role){
        String roleId = ObjectUtil.isNull(role.getRoleId())?"0":role.getRoleId();
        SysRole info = this.baseMapper.selectOne(new QueryWrapper<SysRole>().eq("role_name",role.getRoleName()));
        if (ObjectUtil.isNotNull(info) && info.getRoleId() != roleId){
            return false;
        }
        return true;
    }

//    /**
//     * 校验角色权限名称是否唯一
//     * @param role 角色信息
//     * @return 结果
//     */
//    public Boolean checkRoleKeyUnique(RoleDTO role){
//        String roleId = ObjectUtil.isNull(role.getRoleId())?"0":role.getRoleId();
//        SysRole info = this.baseMapper.selectOne(new QueryWrapper<SysRole>().eq("role_key",role.getRoleKey()));
//        if (ObjectUtil.isNotNull(info) && info.getRoleId() != roleId){
//            return false;
//        }
//        return true;
//    }

    /**
     * 根据用户id查询角色
     * @param userId 用户id
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(String userId){
        List<SysRole> perms = this.baseMapper.selectRolesByUserId(userId);
        HashSet<String> permsSet = new HashSet<>();
        for (SysRole perm : perms){
            if (ObjectUtil.isNotNull(perm)){
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户id查询角色
     * @param userId 用户id
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(String userId){
        List<SysRole> userRoles = this.baseMapper.selectRolesByUserId(userId);
        return userRoles;
    }

    /**
     * 根据用户id获取角色选择框列表
     * @param userId 用户id
     * @return 角色id列表
     */
    @Override
    public String[] selectRoleListByUserId(String userId){
        return this.baseMapper.selectRoleListByUserId(userId);
    }

    /**
     * 取消授权用户角色
     * @param userId 用户id
     * @param roleId 角色id
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAuthUser(String userId, String roleId){
        Set<String> powerList = this.baseMapper.selectRolePower("'"+roleId+"'");
        userPowerMapper.delete(new QueryWrapper<SysUserPower>().eq("user_id", userId).in("power_id",powerList));
        return userRoleMapper.delete(new QueryWrapper<SysUserRole>()
                .eq("user_id",userId).eq("role_id",roleId));
    }

    /**
     * 批量取消授权用户角色
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteAuthUsers(RoleUsersDTO roleUsersDTO){
        return userRoleMapper.delete(new QueryWrapper<SysUserRole>()
                .in("user_id", roleUsersDTO.getUserIds()).eq("role_id", roleUsersDTO.getRoleId()));
    }

    /**
     * 批量选择授权用户角色
     * @return 结果
     */
    @Transactional
    @Override
    public int insertAuthUsers(RoleUsersDTO roleUsersDTO){
        Set<String> powerList = this.baseMapper.selectRolePower("'"+ roleUsersDTO.getRoleId()+"'");
        //新增用户与角色管理
        StringBuilder roleList = new StringBuilder();
        StringBuilder rolePowerList = new StringBuilder();
        for (String userId: roleUsersDTO.getUserIds()){
            roleList.append("('").append(userId).append("','").append(roleUsersDTO.getRoleId()).append("'),");
            for (String powerId : powerList){
                //int row =this.rolePowerMapper.selectCount(new QueryWrapper<SysRolePower>().eq("role_id",roleUsersDTO.getRoleId()).eq("power_id",powerId));
                int row =this.userPowerMapper.selectCount(new QueryWrapper<SysUserPower>().eq("user_id",userId).eq("power_id",powerId));
                if(row == 0){
                    rolePowerList.append("('").append(userId).append("','").append(powerId).append("'),");
                }
            }
        }
        if(StringUtils.isNotEmpty(rolePowerList)){
            userPowerMapper.authUserPower(rolePowerList.substring(0, rolePowerList.length()-1));
        }
        return userRoleMapper.batchUserRole(roleList.substring(0,roleList.length()-1));
    }

}
