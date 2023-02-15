package com.Bibo.system.model.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.ApiTypeGridEnum;
import com.Bibo.common.constant.UserConstants;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysPower;
import com.Bibo.common.pojo.entity.SysUser;
import com.Bibo.common.pojo.vo.PowerButtonVO;
import com.Bibo.common.response.ApiResponse;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.ApiRequestParamsUtils;
import com.Bibo.common.util.HttpRequestUtil;
import com.Bibo.common.util.RequestParamsUtil;
import com.Bibo.system.model.mapper.*;
import com.Bibo.system.model.pojo.dto.RoleUsersDTO;
import com.Bibo.system.model.pojo.dto.UserDTO;
import com.Bibo.system.model.pojo.dto.UserListDTO;
import com.Bibo.system.model.pojo.entity.SysUserPower;
import com.Bibo.system.model.pojo.entity.SysUserRole;
import com.Bibo.system.model.pojo.vo.UserListVO;
import com.Bibo.system.model.service.ISysRoleService;
import com.Bibo.system.model.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPowerMapper powerMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPowerMapper userPowerMapper;

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 获取用户的菜单路由
     * @param userId 用户ID
     */
    @Override
    public Map<String,Object> selectRouterByUserId(String userId){
        List<String> powerAncestors = this.baseMapper.selectRouterByUserId(userId);
        Set<String> powerIds = new HashSet<>();
        for (String power : powerAncestors){
            powerIds.addAll(Arrays.asList(power.split(",")));
        }
        powerIds.remove("0");

        Map<String, Object> map = new HashMap();

        if (powerIds.isEmpty()){
            return map;
        }

        List<SysPower> powerList = this.powerMapper.selectList(new QueryWrapper<SysPower>().in("power_id",powerIds));

        List<SysPower> parentList = powerList.stream().filter(p -> !UserConstants.TYPE_BUTTON.equals(p.getPowerType())).collect(Collectors.toList());

        for (SysPower parent : parentList){
            List<PowerButtonVO> buttons = powerList.stream()
                    .filter(p -> parent.getPowerId().equals(p.getParentId()))
                    .map(p -> {
                        PowerButtonVO buttonVO = new PowerButtonVO();
                        BeanUtil.copyProperties(p, buttonVO);
                        return buttonVO;
                    })
                    .collect(Collectors.toList());
            map.put(parent.getComponentName(), buttons);
        }
        return map;
    }


    /**
     * 根据条件查询分页用户列表
     * @param user 查询条件
     * @return 用户列表
     */
    @Override
    public IPage<UserListVO> selectUserPageList(UserListDTO user) {
        Page page = new Page(user.getPageNum(), user.getPageSize());
        return this.baseMapper.selectUserPageList(page, user);
    }


    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 结果
     */
    @Override
    public LoginUser selectUserByUserName(String username){
        LoginUser user = this.baseMapper.selectUserByUserName(username);
        return user;
    }

    /**
     * 校验部门是否存在用户
     * @param deptId 部门id
     * @return 结果
     */
    @Override
    public Boolean checkDeptExistUser(String deptId){
        int result = this.baseMapper.selectCount(
                new QueryWrapper<SysUser>().eq("dept_id", deptId).eq("status",UserConstants.TRAFFIC_STATUS_NORMAL));
        return result>0?true:false;
    }


    /**
     * 校验用户名称是否唯一
     * @param userName 警员姓名
     * @return 结果
     */
    public Boolean checkUserNameUnique(String userName){
        int result = this.baseMapper.selectCount(
                new QueryWrapper<SysUser>().eq("user_name", userName).eq("status",UserConstants.TRAFFIC_STATUS_NORMAL));
        return result>0?true:false;
    }

    /**
     * 校验警号是否唯一
     * @param policeCode 警员姓名
     * @return 结果
     */
    public Boolean checkPoliceCode(String policeCode){
        int result = this.baseMapper.selectCount(
                new QueryWrapper<SysUser>().eq("police_code", policeCode).eq("status",UserConstants.TRAFFIC_STATUS_NORMAL));
        return result>0?true:false;
    }


    /**
     * 用户授权角色
     * @param userId 用户id
     * @param roleIds 角色id列表
     */
    @Override
    @Transactional
    public void insertUserAuth(String userId, List<String> roleIds){
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id",userId));
        insertUserRole(userId, roleIds);
    }

    @Override
    @Transactional
    public void insertUserAuth(List<String> userId, List<String> roleIds){
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().in("user_id",userId));
        userPowerMapper.delete(new QueryWrapper<SysUserPower>().in("user_id",userId));
        for(String roleId : roleIds){
            RoleUsersDTO roleUsersDTO = new RoleUsersDTO();
            roleUsersDTO.setRoleId(roleId);
            roleUsersDTO.setUserIds(userId);
            sysRoleService.insertAuthUsers(roleUsersDTO);
        }
        //insertUserRole(userId, roleIds);
    }
    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(UserDTO user){
        String userId = user.getUserId();
        SysUser u = new SysUser();
        BeanUtil.copyProperties(user, u);

        //删除用户与角色关联
//        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        //新增用户与角色管理
//        insertUserRole(userId, user.getRoleIds());
//        u.setCreateBy(RedisUtil.getUserByRedis().getUserName());
        u.setUpdateTime(new Date());
        return this.baseMapper.updateById(u);
    }



    /**
     * 添加用户信息
     * @param userDTO 用户信息
     * @return 结果
     */
    @Override
    public boolean saveUser(UserDTO userDTO) {
        SysUser u = new SysUser();
        BeanUtil.copyProperties(userDTO, u);
        u.setCreateBy(RedisUtil.getUserByRedis().getUserName());
        u.setCreateTime(new Date());
        return this.save(u);
    }

    /**
     * 新增用户角色信息
     * @param userId
     * @param roleIds
     */
    @Transactional
    public void insertUserRole(String userId, List<String> roleIds){
        // 先获取用户的权限并删除，获取角色列表的set权限并合并在一起再存入用户权限
        Set<String> userPower = userPowerMapper.selectPowerList(userId);
        userPowerMapper.delete(new QueryWrapper<SysUserPower>().eq("user_id", userId));
        StringBuilder powerBuilder = new StringBuilder();
        // 添加用户与角色关系
        if(ObjectUtil.isNotEmpty(roleIds)){
            StringBuilder list = new StringBuilder();
            for (String roleId:roleIds){
                list.append("('").append(userId).append("','").append(roleId).append("'),");
                powerBuilder.append("'").append(roleId).append("',");
            }
            userRoleMapper.batchUserRole(list.toString().substring(0,list.length()-1));
        }
        Set<String> rolePower = new HashSet<>();
        if (powerBuilder.length()>0){
            String sql = powerBuilder.substring(0, powerBuilder.length()-1);
            rolePower = roleMapper.selectRolePower(sql);
        }
        // 合并角色权限跟用户权限
        userPower.addAll(rolePower);

        if (ObjectUtil.isNotEmpty(userPower)){
            StringBuilder power = new StringBuilder();
            for (String powerId:userPower){
                power.append("('").append(userId).append("','").append(powerId).append("'),");
            }
            userPowerMapper.authUserPower(power.substring(0, power.length()-1));
        }
    }

    /**
     * 获取用户的权限
     */
    @Override
    public LoginUser selectLoginUserCode(String policeCode){
        return this.baseMapper.selectLoginUserCode(policeCode);
    }

    @Override
    public Response getAllGridList(String level) {
        try {
            //获取网格信息
            if(StringUtils.isBlank(level)){
                level = "";
            }
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(), ApiRequestParamsUtils.insertParamsString(ApiTypeGridEnum.ALL_GRID_LIST.getApiType(),level));
            if(apiResponse.isSuccess()){
                return Response.success().data(apiResponse.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("天河接口错误");
    }

    @Override
    public List<SysUser> getAllByDeptId(String deptId) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("dept_id",deptId);
        List<SysUser> sysUsers = this.baseMapper.selectList(qw);
        return sysUsers;
    }

    @Override
    public List<Map<String, Object>> orcelPolice() {
        return this.baseMapper.getPoliceData();
    }

    @Override
    @DS("branch")
    public Response synchronousPolice(List<Map<String,Object>> polices) {
//        polices.forEach(o ->{
//            this.baseMapper.selectLoginUserCode(o.get(""));
//        });
        return Response.success().data(polices);
    }

}
