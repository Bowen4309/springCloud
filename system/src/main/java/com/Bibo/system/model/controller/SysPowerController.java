package com.Bibo.system.model.controller;


import cn.hutool.core.bean.BeanUtil;
import com.Bibo.system.model.pojo.entity.SysRolePower;
import com.Bibo.system.model.pojo.entity.SysUserPower;
import com.Bibo.system.model.service.ISysPowerService;
import com.Bibo.system.model.service.ISysRolePowerService;
import com.Bibo.system.model.service.ISysUserPowerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.constant.UserConstants;
import com.Bibo.common.pojo.entity.SysPower;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.system.model.pojo.dto.PowerButtonAddDTO;
import com.Bibo.system.model.pojo.dto.PowerButtonEditDTO;
import com.Bibo.system.model.pojo.dto.PowerDTO;
import com.Bibo.system.model.pojo.dto.PowerListDTO;
import com.Bibo.system.model.pojo.vo.PowerListVO;
import com.Bibo.system.model.pojo.vo.PowerTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xdh
 * @since 2021-09-17
 */
@Api(tags = "权限目录信息管理")
@RestController
@RequestMapping(value = "/power")
public class SysPowerController extends BaseController {

    @Autowired
    private ISysPowerService powerService;

    @Autowired
    private ISysUserPowerService userPowerService;

    @Autowired
    private ISysRolePowerService rolePowerService;


    @SysLog(title="获取菜单下拉树列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取菜单下拉树列表")
    @ApiImplicitParam(value = "系统类型",name = "systemType")
    @GetMapping("/treeSelect")
    public Response<List<PowerTreeVO>> treeSelect(String systemType,String powerType){
        List<SysPower> powers = null;
        if (UserConstants.TRAFFIC_ALL_TYPE.equals(systemType)){
            powers =powerService.list(new QueryWrapper<SysPower>().ne("power_type","3"));
        }else {
            powers = powerService.list(new QueryWrapper<SysPower>().eq("power_type",powerType).eq("system_type", systemType));

        }
        return success().data(powerService.buildPowerTreeSelect(powers));
    }



    @SysLog(title="根据条件查询权限列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据条件查询权限列表")
    @GetMapping("/list")
    public Response<List<PowerListVO>> list(PowerListDTO powerListDTO){
        return getDataTable(powerService.selectPowerPageList(powerListDTO));
    }


//    @ApiOperation(value = "测试获取路由信息")
//    @GetMapping("/getRouters")
//    public Response<List<RouterVO>> getRouters(){
//        List<PowerTreeVO> powerTreeVOList = powerService.selectPowerTreeByUserId(RedisUtil.getUserByRedis().getUserId());
//        return success().data(powerService.buildRouterMenus(powerTreeVOList));
//    }


    @SysLog(title="获取按钮或者目录列表信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取按钮或者目录列表信息")
    @ApiImplicitParam(value = "菜单id",name = "powerId")
    @GetMapping("/button/get")
    public Response<PowerListVO> getPowerButtonInfo(String powerId,String powerType){
        List<SysPower> powerList = powerService.list(new QueryWrapper<SysPower>().eq("parent_id", powerId).eq("power_type",powerType).orderByAsc("remark"));
        if(powerType.equals("3")){
            List<PowerListVO> powers = powerList.stream().map(p -> {
                PowerListVO power = new PowerListVO();
                BeanUtil.copyProperties(p, power);
                return power;
            }).collect(Collectors.toList());
            return success().data(powers);
        }else if(powerType.equals("2")){
            List<String> list =powerList.stream().map(SysPower::getPowerId).collect(Collectors.toList());
            List<SysPower> childPowerList = new ArrayList<SysPower>();
            if(list.size()>0){
                childPowerList = powerService.list(new QueryWrapper<SysPower>().in("parent_id",list).orderByAsc("remark"));
            }
            List<PowerListVO> result =new ArrayList<PowerListVO>();
                List<SysPower> finalChildPowerList = childPowerList;
                result = powerList.stream().map(parentPower ->{
                        PowerListVO parentPowerVO = new PowerListVO();
                        BeanUtils.copyProperties(parentPower,parentPowerVO);
                        parentPowerVO.setChildren(new ArrayList<PowerListVO>());
                    if(null != finalChildPowerList && finalChildPowerList.size()>0) {
                        List<PowerListVO> childList = finalChildPowerList.stream().map(childPower -> {
                            if (parentPower.getPowerId().equals(childPower.getParentId())) {
                                PowerListVO power = new PowerListVO();
                                power.setChildren(new ArrayList<PowerListVO>());
                                BeanUtils.copyProperties(childPower, power);
                                return power;
                            } else {
                                return null;
                            }
                        }).collect(Collectors.toList());
                       //childList.stream().sorted(Comparator.comparing(PowerListVO::getRemark).reversed()).collect(Collectors.toList());
                        childList.removeAll(Collections.singleton(null));
                        parentPowerVO.setChildren(childList);
                    }
                    return parentPowerVO;
                }).collect(Collectors.toList());
            return success().data(result);
        }else{
            return Response.error("参数错误");
        }
    }


    @SysLog(title="新增目录或者按钮", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增目录或者按钮")
    @PostMapping("/button/add")
    public Response addButton(@RequestBody PowerButtonAddDTO button){
//        if (powerService.count(
//                new QueryWrapper<SysPower>().eq("parent_id",button.getParentId())
//                        .eq("power_name",button.getPowerName()))>0){
//            return error("新增按钮'"+button.getPowerName()+"'失败,此菜单下按钮名称已存在");
//        }
        String powerId = UUID.randomUUID().toString().replace("-","");

        SysPower p = new SysPower();
        p.setPowerId(powerId);
        SysPower parent = powerService.getById(button.getParentId());
        p.setAncestors(parent.getAncestors()+","+powerId);

        p.setParentId(button.getParentId());
        p.setPowerName(button.getPowerName());
        p.setSystemType(button.getSystemType());
        p.setPowerType(button.getPowerType());
        p.setCreateTime(new Date());
        p.setComponentName(button.getComponentName());
        p.setRemark(button.getRemark());
        p.setCreateBy(RedisUtil.getUserByRedis().getUserName());
        return toResponse(powerService.save(p)).data(p.getPowerId());
    }





    @SysLog(title="编辑按钮或目录", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "编辑按钮或目录")
    @PostMapping("/button/edit")
    public Response editButton(@RequestBody PowerButtonEditDTO button){
//        if (powerService.count(
//                new QueryWrapper<SysPower>().ne("power_id",button.getPowerId())
//                        .eq("power_name",button.getPowerName()))>0){
//            return error("新增按钮'"+button.getPowerName()+"'失败,此菜单下按钮名称已存在");
//        }
        SysPower p = powerService.getById(button.getPowerId());
        p.setPowerName(button.getPowerName());
        p.setUpdateBy(RedisUtil.getUserByRedis().getUserName());
        p.setUpdateTime(new Date());
        p.setComponentName(button.getComponentName());
        p.setRemark(button.getRemark());
        return toResponse(powerService.saveOrUpdate(p));
    }


    @SysLog(title="新增菜单", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增菜单")
    @PostMapping("/add")
    public Response add(@RequestBody PowerDTO power){
        if (powerService.checkPowerNameUnique(power)){
            return error("新增菜单'"+power.getPowerName()+"'失败,菜单名称已存在");
        }
        SysPower p = new SysPower();
        BeanUtil.copyProperties(power, p);
        String powerId = UUID.randomUUID().toString().replace("-","");
        p.setPowerId(powerId);
        if ("0".equals(power.getParentId())){
            p.setAncestors("0,"+powerId);
        }else {
            SysPower parent = powerService.getById(power.getParentId());
            p.setAncestors(parent.getAncestors()+","+powerId);
            // 修改父节点权限为子节点
            userPowerService.update(new UpdateWrapper<SysUserPower>().set("power_id",powerId).eq("power_id",power.getParentId()));
            rolePowerService.update(new UpdateWrapper<SysRolePower>().set("power_id",powerId).eq("power_id",power.getParentId()));
        }

        p.setCreateBy(RedisUtil.getUserByRedis().getUserName());
        p.setCreateTime(new Date());
        return toResponse(powerService.save(p));
    }

    @SysLog(title="编辑菜单", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "编辑菜单")
    @PostMapping("/edit")
    public Response edit(@RequestBody PowerDTO power){
        if (powerService.checkPowerNameUnique(power)){
            return error("修改菜单'"+power.getPowerName()+"'失败,菜单名称已存在");
        }else if (power.getPowerId().equals(power.getParentId())){
            return error("修改菜单'" + power.getPowerName() + "'失败,上级菜单不能选择自身");
        }
        SysPower p = new SysPower();
        BeanUtil.copyProperties(power, p);
        p.setUpdateBy(RedisUtil.getUserByRedis().getUserName());
        p.setUpdateTime(new Date());
        return toResponse(powerService.saveOrUpdate(p));
    }

    @SysLog(title="删除菜单", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    @GetMapping("/delete")
    public Response delete(String id){
        if (powerService.hashChildByMenuId(id)){
            return error("存在子菜单，不允许删除");
        }
//       if (powerService.checkPowerExistRole(id)){
//            return error("菜单已分配，不允许删除");
//        }
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("power_id",id);
       userPowerService.removeByMap(map);
        return toResponse(powerService.removeById(id));
    }
}
