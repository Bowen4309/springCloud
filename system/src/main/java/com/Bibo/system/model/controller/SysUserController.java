package com.Bibo.system.model.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.constant.UserConstants;
import com.Bibo.common.pojo.entity.SysUser;
import com.Bibo.common.pojo.vo.GridListTreeVO;
import com.Bibo.common.pojo.vo.UserVO;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.JwtUtils;
import com.Bibo.system.model.pojo.dto.UserDTO;
import com.Bibo.system.model.pojo.dto.UserListDTO;
import com.Bibo.system.model.pojo.dto.UserPowerDTO;
import com.Bibo.system.model.pojo.dto.UserRolesDTO;
import com.Bibo.system.model.pojo.entity.SysUserPower;
import com.Bibo.system.model.pojo.entity.SysUserRole;
import com.Bibo.system.model.pojo.vo.UserListVO;
import com.Bibo.system.model.service.ISysRoleService;
import com.Bibo.system.model.service.ISysUserPowerService;
import com.Bibo.system.model.service.ISysUserRoleService;
import com.Bibo.system.model.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xdh
 * @since 2021-09-17
 */
@Api(tags = "用户信息管理")
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserPowerService userPowerService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @SysLog(title="新增用户", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public Response add(@RequestBody UserDTO userDTO) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (userService.checkUserNameUnique(userDTO.getUserName()))
        {
            return error("新增用户信息失败'" + userDTO.getUserName() + "'失败，登录账号已存在");
        }
        if (userService.checkPoliceCode(userDTO.getPoliceCode()))
        {
            return error("新增用户信息失败,警号'" + userDTO.getPoliceCode() + "'已存在");
        }
        userDTO.setPassword(JwtUtils.encryptPassword("123456"));
        return toResponse(userService.saveUser(userDTO));
    }

    @SysLog(title="重置密码", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "重置密码")
    @GetMapping("/initPassWord")
    @ApiImplicitParam(value = "userId",name = "userId",required = true)
    public Response initPassWord(String userId) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setPassword(JwtUtils.encryptPassword("123456"));
        return toResponse(userService.updateById(sysUser));
    }


    @SysLog(title="修改用户", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "修改用户")
    @PostMapping("/edit")
    public Response edit(@RequestBody UserDTO userDTO) throws InvalidKeySpecException, NoSuchAlgorithmException {
        //
        if(StringUtils.isNotEmpty(userDTO.getPassword())){
            SysUser user =userService.getById(userDTO.getUserId());
            if(!user.getPassword().equals(JwtUtils.encryptPassword(userDTO.getOldPassWord()))){
                return Response.error("旧密码输入错误,请重新输入!");
            }
            if(!JwtUtils.checkPassWord(userDTO.getPassword())){
                return Response.error("密码不包含字母大小写、数字和字符,请重新填写!");
            }else{
                userDTO.setPassword(JwtUtils.encryptPassword(userDTO.getPassword()));
            }
        }
        return toResponse(userService.updateUser(userDTO));
    }


    @SysLog(title="删除用户", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    @GetMapping("/delete")
    public Response delete(String id){
        userRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id",id));
        userPowerService.remove(new QueryWrapper<SysUserPower>().eq("user_id",id));
        String token = (String) RedisUtil.get(UserConstants.LOGIN_USER_ID+id);
        JwtUtils.delUserToken(token);
        return toResponse(userService.update(
                new UpdateWrapper<SysUser>().set("status","2").eq("user_id",id)));
    }

    @SysLog(title="根据条件查询用户", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据条件查询用户")
    @PostMapping("/list")
    public Response<List<UserListVO>> list(@RequestBody UserListDTO userDTO){
        return getDataTable(userService.selectUserPageList(userDTO));
    }


    @SysLog(title="获取用户信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    @GetMapping("/get")
    public Response<UserDTO> getInfo(String id){
        UserVO userVo = new UserVO();
        SysUser user = userService.getById(id);
        BeanUtil.copyProperties(user, userVo);
//        userVo.setRoleList(roleService.list());//获取全部角色信息
        String[] roleIds = roleService.selectRoleListByUserId(id);
        userVo.setRoleIds(roleIds);
        userVo.setRoleId(ObjectUtil.isEmpty(roleIds)?"":roleIds[0]);
        SysUser byId = userService.getById(user.getLeaderId());
        if(byId != null){userVo.setLeaderName(user.getLeaderId().equals("0")?"无": byId.getUserName());}
        return success().data(userVo);
    }

    @SysLog(title="用户授权角色", operatorType= OperatorTypeEnum.GRANT)
    @ApiOperation(value = "用户授权角色")
    @PostMapping("/authRole")
    public Response insertAuthRole(@RequestBody UserRolesDTO userRolesDTO){
        userService.insertUserAuth(userRolesDTO.getUserId(), userRolesDTO.getRoleList());
        return success();
    }


    @SysLog(title="用户权限授权", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "用户权限授权")
    @PostMapping("/authUserPower")
    public Response authUserPower(@RequestBody UserPowerDTO userPowerDTO){
        userPowerService.authUserPower(userPowerDTO);
        return success();
    }

    @SysLog(title="获取用户权限", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取用户权限")
    @ApiImplicitParam(value = "userId",name = "userId",required = false)
    @GetMapping("/power")
    public Response getPowerSetById(String userId){
        Set<String> powerIds = userPowerService.list(
                new QueryWrapper<SysUserPower>().eq("user_id",userId))
                .stream().map(s->{return s.getPowerId();}).collect(Collectors.toSet());
        return success().data(powerIds);
    }

    @SysLog(title="获取网格列表", operatorType= OperatorTypeEnum.SEARCH)
    @GetMapping("getAllGridList")
    @ApiOperation(value = "获取所有网格列表",notes = "获取网格列表")
    public Response<List<GridListTreeVO>> getAllGridList(String name){
        return userService.getAllGridList(name);
    }


    @SysLog(title="同步六合一警员表", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "同步六合一警员表")
    @GetMapping("/synchronousPolice")
    public Response synchronousPolice(){
        return userService.synchronousPolice(userService.orcelPolice());
    }
}
