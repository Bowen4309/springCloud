package com.Bibo.system.model.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.MenuDTO;
import com.Bibo.system.model.pojo.entity.SysMenu;
import com.Bibo.system.model.service.ISysMenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xdh
 * @since 2021-09-16
 */
//@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 获取全部菜单
     * @return
     */
    @SysLog(title="获取全部菜单", operatorType= OperatorTypeEnum.SEARCH)
    @GetMapping("getMenuList")
    @ApiOperation(notes = "获取全部菜单",value = "获取菜单")
    public Response getMenuList(){

        List<SysMenu> sysMenuList = sysMenuService.list();

        return Response.success().data(sysMenuList);
    }


    /**
     * 根据类型获取菜单获取菜单
     * @param type
     * @return
     */
    @SysLog(title="根据类型获取菜单获取菜单", operatorType= OperatorTypeEnum.SEARCH)
    @GetMapping("getMenuListByType")
    @ApiOperation(notes = "根据类型获取菜单获取菜单",value = "获取部分菜单")
    @ApiImplicitParam(value = "菜单类型",name = "type",required = false)
    public Response getMenuListByType(String type){

        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        sysMenuQueryWrapper.eq("type",type);
        List<SysMenu> sysMenuList = sysMenuService.list(sysMenuQueryWrapper);

        return Response.success().data(sysMenuList);
    }

    /**
     * 新增菜单
     * @param menuDTO
     * @return
     */
    @SysLog(title="新增或修改菜单", operatorType= OperatorTypeEnum.INSERT)
    @PostMapping("saveOrUpdateMenu")
    @ApiOperation(notes = "新增或修改菜单",value = "新增或修改菜单")
    public Response saveOrUpdateMenu(@RequestBody MenuDTO menuDTO){
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(menuDTO,sysMenu);
        sysMenuService.saveOrUpdate(sysMenu);
        return Response.success();
    }

}
