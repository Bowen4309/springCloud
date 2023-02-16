package com.Bibo.system.model.controller;


import com.Bibo.system.model.pojo.entity.SysRolePower;
import com.Bibo.system.model.pojo.entity.SysUserRole;
import com.Bibo.system.model.service.ISysPowerService;
import com.Bibo.system.model.service.ISysRolePowerService;
import com.Bibo.system.model.service.ISysRoleService;
import com.Bibo.system.model.service.ISysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.pojo.entity.SysPower;
import com.Bibo.common.pojo.entity.SysRole;
import com.Bibo.common.pojo.vo.RoleListVO;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.RoleDTO;
import com.Bibo.system.model.pojo.dto.RoleListDTO;
import com.Bibo.system.model.pojo.dto.RolePowerDTO;
import com.Bibo.system.model.pojo.dto.RoleUsersDTO;
import com.Bibo.system.model.pojo.vo.RolePowerTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xdh
 * @since 2021-09-17
 */
@Api(tags = "角色信息管理")
@RestController
@RequestMapping(value = "/role")
public class SysRoleController extends BaseController {
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysRolePowerService rolePowerService;

    @Autowired
    private ISysPowerService powerService;

    @SysLog(title="根据角色id获取详细信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据角色id获取详细信息")
    @GetMapping("/get")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    public Response<SysRole> getInfo(String id){
        return success().data(roleService.getById(id));
    }

    @SysLog(title="根据条件查询角色列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据条件查询角色列表")
    @GetMapping("/list")
    public Response<List<RoleListVO>> list(RoleListDTO roleListDTO){
        IPage page =roleService.selectRolePageList(roleListDTO);

        return getDataTable(page);
    }


    @SysLog(title="获取加载对应角色树列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取加载对应角色树列表")
    @ApiImplicitParam(value = "roleId",name = "roleId",required = false)
    @GetMapping("/roleTreeSelect")
    public Response<RolePowerTreeVo> roleTreeSelect(String roleId){
        List<SysPower> powers = powerService.list();
        RolePowerTreeVo rolePowerTreeVo = new RolePowerTreeVo(powerService.selectPowerListByRoleId(roleId),powerService.buildPowerTreeSelect(powers));
        return success().data(rolePowerTreeVo);
    }

    @SysLog(title="新增角色", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "新增角色")
    @PostMapping("/add")
    public Response add(@RequestBody RoleDTO role){
        if (!roleService.checkRoleNameUnique(role)){
            return error("新增角色'"+role.getRoleName()+"'失败,角色名称已存在");
//        }else if (!roleService.checkRoleKeyUnique(role)){
//            return error("新增角色'"+role.getRoleName()+"'失败,角色权限已存在");
        }
        return toResponse(roleService.saveOrUpdateRole(role));
    }


    @SysLog(title="编辑角色", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "编辑角色")
    @PostMapping("/edit")
    public Response edit(@RequestBody RoleDTO role){
        SysRole oldRole = roleService.getById(role.getRoleId());
        if (!role.getRoleName().equals(oldRole.getRoleName()) && !roleService.checkRoleNameUnique(role)){
            return error("修改角色'"+role.getRoleName()+"'失败,角色名称已存在");
//        }else if (!role.getRoleKey().equals(oldRole.getRoleKey()) && !roleService.checkRoleKeyUnique(role)){
//            return error("修改角色'"+role.getRoleName()+"'失败,角色权限已存在");
        }
        return toResponse(roleService.saveOrUpdateRole(role));
    }


    @SysLog(title="删除角色", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    @GetMapping("/delete")
    public Response delete(String id){
        if (userRoleService.count(new QueryWrapper<SysUserRole>().eq("role_id", id))>0){
            return error("角色已被分配，不允许删除");
        }
        rolePowerService.remove(new QueryWrapper<SysRolePower>().eq("role_id", id));
        return toResponse(roleService.removeById(id));
    }


    @SysLog(title="角色配置权限", operatorType= OperatorTypeEnum.GRANT)
    @ApiOperation(value = "角色配置权限")
    @PostMapping("/authRole/insert")
    public Response cancelAuthUser(@RequestBody RolePowerDTO rolePowerDTO){
        roleService.insertRolePower(rolePowerDTO);
        return success();
    }




    @SysLog(title="获取角色权限", operatorType= OperatorTypeEnum.GRANT)
    @ApiOperation(value = "获取角色权限")
    @ApiImplicitParam(value = "roleId",name = "roleId",required = false)
    @GetMapping("/power")
    public Response getPowerSetById(String roleId){
        Set<String> powerIds = rolePowerService.list(
                new QueryWrapper<SysRolePower>().eq("role_id",roleId))
                .stream().map(s->{return s.getPowerId();}).collect(Collectors.toSet());
        return success().data(powerIds);
    }


    @SysLog(title="取消授权用户", operatorType= OperatorTypeEnum.GRANT)
    @ApiOperation(value = "取消授权用户")
    @ApiImplicitParams ({
        @ApiImplicitParam(value = "用户id",name = "userId",required = true),
        @ApiImplicitParam(value = "角色id",name = "roleId",required = true)
    })
    @GetMapping("/authUser/cancel")
    public Response cancelAuthUser(String userId,String roleId){
        return toResponse(roleService.deleteAuthUser(userId, roleId));
    }

    @SysLog(title="批量取消授权用户", operatorType= OperatorTypeEnum.GRANT)
    @ApiOperation(value = "批量取消授权用户")
    @PostMapping("/authUser/cancelAll")
    public Response cancelAuthUserAll(@RequestBody RoleUsersDTO roleUsersDTO){
        return toResponse(roleService.deleteAuthUsers(roleUsersDTO));
    }

    @SysLog(title="批量选择用户授权", operatorType= OperatorTypeEnum.GRANT)
    @ApiOperation(value = "批量选择用户授权")
    @PostMapping("/authUser/insertAll")
    public Response insertAuthUserAll(@RequestBody RoleUsersDTO roleUsersDTO){
        return toResponse(roleService.insertAuthUsers(roleUsersDTO));
    }
}
