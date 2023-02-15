package com.Bibo.system.model.controller;

import cn.hutool.core.util.ObjectUtil;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.constant.UserConstants;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysRole;
import com.Bibo.common.pojo.vo.RoleListVO;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.JwtUtils;
import com.Bibo.common.util.ServletUtils;
import com.Bibo.system.model.pojo.dto.LoginDTO;
import com.Bibo.system.model.service.ISysPowerService;
import com.Bibo.system.model.service.ISysRoleService;
import com.Bibo.system.model.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = "登陆退出接口")
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPowerService powerService;

    @Autowired
    private ISysRoleService roleService;

    @SysLog(title="登陆", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "登陆")
    @PostMapping("/loginUser")
    public Response<LoginUser> login(@RequestBody LoginDTO loginUser) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String token = "";
        LoginUser user = null;

        if (UserConstants.TRAFFIC_ADMIN_TYPE.equals(loginUser.getType())){
            user = userService.selectUserByUserName(loginUser.getUserName());
            if (ObjectUtil.isNull(user) || !JwtUtils.matchesPassword(loginUser.getPassword(), user.getPassword()))
            {
                return Response.error("登陆错误，用户名或密码错误");
            }
            else if ("1".equals(user.getStatus()))
            {
                return Response.error("对不起，您的账号：" + loginUser.getUserName() + " 已停用");
            }
            // 查询用户在权限
            List<String> pems = powerService.selectPowerPermsByUserId(user.getUserId());
            user.setPermissions(pems);
            user.setStrongPassWord(JwtUtils.checkPassWord(loginUser.getPassword()));
            token = JwtUtils.getJwtToken(user);
        }else if (UserConstants.TRAFFIC_GRID_TYPE.equals(loginUser.getType())){
            user = userService.selectUserByUserName(loginUser.getUserName());
            if (ObjectUtil.isNull(user) || !JwtUtils.matchesPassword(loginUser.getPassword(), user.getPassword()))
            {
                return Response.error("登陆错误，用户名或密码错误");
            }
            else if ("1".equals(user.getStatus()))
            {
                return Response.error("对不起，您的账号：" + loginUser.getUserName() + " 已停用");
//                throw new APIException("对不起，您的账号：" + loginUser.getUserName() + " 已停用");
            }
            System.out.println(user.getUserId());
            //查询用户角色
            List<SysRole> roles = roleService.selectRolesByUserId(user.getUserId());
            List<RoleListVO> roleListVOS =  roles.stream().map(r ->{
                RoleListVO roleListVO = new RoleListVO();
                roleListVO.setRoleName(r.getRoleName());
                roleListVO.setRoleId(r.getRoleId());
                roleListVO.setRoleKey(r.getRoleKey());
                return roleListVO;
            }).collect(Collectors.toList());
             user.setRoleList(roleListVOS);

            // 查询用户在权限
            List<String> pems = powerService.selectPowerPermsByUserId(user.getUserId());

            user.setPermissions(pems);

            List<String> gridDepartALl =powerService.getGridDepartAll();
            if(null == gridDepartALl){
                return Response.error("平台转发请求异常，请联系系统管理员");
            }

            System.out.println(gridDepartALl.toString());
            RedisUtil.set("depart",gridDepartALl);
            user.setStrongPassWord(JwtUtils.checkPassWord(loginUser.getPassword()));
            token = JwtUtils.getJwtToken(user);
        }
        return Response.success().data(token);
    }

//    @ApiOperation(value = "获取路由")
//    @GetMapping("/getRouters")
//    public Response<List<RouterVO>> getRouters(){
//        // 获得该用户的路由
//        List<PowerTreeVO> powerTreeVOList = powerService.selectPowerTreeByUserId(RedisUtil.getUserByRedis().getUserId());
//        List<RouterVO> routers = powerService.buildRouterMenus(powerTreeVOList);
//        return Response.success().data(routers);
//    }


    @SysLog(title="获取用户信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUser")
    public Response getUser(){
        LoginUser user = RedisUtil.getUserByRedis();
        if (ObjectUtil.isNull(user.getUserId())){
            if (ObjectUtil.isEmpty(user.getPoliceCode())){
                return Response.error("请登录");
            }
            user = userService.selectLoginUserCode(user.getPoliceCode());
            // 查询用户在权限
            List<String> pems = powerService.selectPowerPermsByUserId(user.getUserId());
            user.setPermissions(pems);
            RedisUtil.setUserByRedis(user);
        }
        System.out.println("获取到的用户信息："+user);
        return Response.success().data(user);
    }


    @SysLog(title="获取路由菜单", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取路由菜单")
    @GetMapping("/getRouters")
    public Response<Set<String>> getRouters(){

        // 获得该用户的路由
//        List<String> pems = powerService.selectPowerPermsByUserId(RedisUtil.getUserByRedis().getUserId());
//        return Response.success().data(pems);

        Map<String, Object> data = userService.selectRouterByUserId(RedisUtil.getUserByRedis().getUserId());
        return Response.success().data(data);
    }



//    @ApiOperation(value = "获取路由目录下的按钮")
//    @GetMapping("/getButton")
//    public Response<Set<String>> getButton(){
//        Map<String, Object> data = userService.selectRouterByUserId(RedisUtil.getUserByRedis().getUserId());
//        return Response.success().data(data);
//    }
    @ApiOperation(value = "获取token")
    @GetMapping("/getToken")
    public Response getToken(){
        HttpServletRequest request = ServletUtils.getRequest();
        // 获取请求携带的令牌
        String token = request.getHeader("RZZX-USERTOKEN");
        if (ObjectUtil.isEmpty(token)){
            token= "";
        }
        return Response.success().data(token);
    }

    @ApiOperation(value = "登出")
    @GetMapping("/logoutUser")
    public Response logout(){
        RedisUtil.delUserInfo();
        return Response.success("登出成功");
    }
}
