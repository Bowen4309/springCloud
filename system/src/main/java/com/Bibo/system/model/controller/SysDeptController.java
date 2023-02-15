package com.Bibo.system.model.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.Bibo.common.annotation.SysLog;
import com.Bibo.common.constant.OperatorTypeEnum;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.pojo.entity.SysDept;
import com.Bibo.common.pojo.vo.DeptListVO;
import com.Bibo.common.request.BaseController;
import com.Bibo.common.response.Response;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.system.model.pojo.dto.DeptDTO;
import com.Bibo.system.model.pojo.dto.DeptListDTO;
import com.Bibo.system.model.pojo.dto.TreeSelect;
import com.Bibo.system.model.service.ISysDeptService;
import com.Bibo.system.model.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xdh
 * @since 2021-09-17
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService userService;

    @SysLog(title="根据查询条件查询部门列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据查询条件查询部门列表")
    @GetMapping("/list")
    public Response<List<DeptListVO>> list(DeptListDTO deptDTO){
        return getDataTable(deptService.selectDeptPageList(deptDTO));
    }

    @SysLog(title="根据部门id获取详细信息", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "根据部门id获取详细信息")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    @GetMapping("/info")
    public Response<SysDept> getInfo(String id){
        return success().data(deptService.getById(id));
    }


    @SysLog(title="获取部门下拉树列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取部门下拉树列表")
    @GetMapping("/treeSelect")
    public Response<List<TreeSelect>> treeSelect(DeptListDTO deptDTO){
        List<SysDept> depts = deptService.selectDeptList(deptDTO);
        if (ObjectUtil.isNotEmpty(depts)){
            return success().data(deptService.buildDeptTreeSelect(depts));
        }
        return success().data(new ArrayList<>());
    }


    @SysLog(title="获取支队部门列表", operatorType= OperatorTypeEnum.SEARCH)
    @ApiOperation(value = "获取支队部门列表")
    @GetMapping("/team")
    public Response<List<TreeSelect>> listTeam(){
        String deptId = "440100000000";
        LoginUser user = RedisUtil.getUserByRedis();
//        if (ObjectUtil.isNotEmpty(user)){
//            deptId = user.getDept().getDeptId();
//        }
        List<SysDept> depts = deptService.list(new QueryWrapper<SysDept>().eq("parent_id", deptId).orderByAsc("dept_id"));
        if (ObjectUtil.isNotEmpty(depts)){
//            return success().data(deptService.buildDeptTreeSelect(depts));
            return success().data(depts.stream().map(s -> {
                TreeSelect select = new TreeSelect();
                select.setId(s.getDeptId());
                select.setLabel(s.getDeptName());
                select.setLevel(s.getDeptLevel());
                return select;
            }).collect(Collectors.toList()));
        }
        return success().data(new ArrayList<>());
    }


    @SysLog(title="新增部门", operatorType= OperatorTypeEnum.INSERT)
    @ApiOperation(value = "新增部门")
    @PostMapping("/add")
    public Response add(@RequestBody DeptDTO deptDTO){
        if (deptService.checkDeptNameUnique(deptDTO)){
            return error("新增部门'"+deptDTO.getDeptName()+"'失败,部门名称已存在");
        }
        if (!"0".equals(deptDTO.getParentId())&&ObjectUtil.isNull(deptService.getById(deptDTO.getParentId()))){
            return error("新增部门'"+deptDTO.getDeptName()+"'失败,上级部门id"+deptDTO.getParentId()+"不存在");
        }
        return toResponse(deptService.insertDept(deptDTO));
    }


    @SysLog(title="编辑部门", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "编辑部门")
    @PostMapping("/edit")
    public Response edit(@RequestBody DeptDTO deptDTO){
        if (deptService.checkDeptNameUnique(deptDTO)){
            return error("修改部门'"+deptDTO.getDeptName()+"'失败,部门名称已存在");
        }else if (deptDTO.getDeptId().equals(deptDTO.getParentId())) {
            return error("修改部门'" + deptDTO.getDeptName() + "'失败,上级部门不能选择自身");
//        }else if (ObjectUtil.isNotNull(deptDTO.getStatus())&&deptDTO.getStatus().equals("1") && deptService.selectNormalChildrenDeptById(deptDTO.getDeptId())>0){
//            return error("该部门包含未停用的子部门");
        }
        return toResponse(deptService.updateDept(deptDTO));
    }

    @SysLog(title="删除部门", operatorType= OperatorTypeEnum.DELETE)
    @ApiOperation(value = "删除部门")
    @ApiImplicitParam(value = "id",name = "id",required = false)
    @GetMapping("/delete")
    public Response delete(String id){
        if (deptService.selectNormalChildrenDeptById(id)>1){
            return error("存在下级部门，不允许删除");
        }
        if (userService.checkDeptExistUser(id)){
            return error("部门存在用户，不允许删除");
        }
        return toResponse(deptService.removeById(id));
    }


    @SysLog(title="更改部门信息到Redis", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "更改部门信息到Redis")
    @GetMapping("/renewData")
    public Response renewDataToRedis(){
        deptService.renewDataToRedis();
        return success();
    }

    @SysLog(title="同步六合一部门表", operatorType= OperatorTypeEnum.UPDATE)
    @ApiOperation(value = "同步六合一部门表")
    @GetMapping("/synchronousDepart")
    public Response synchronousDepart(){
//        return deptService.synchronousDepart(deptService.orcelDepart());
        return Response.success().data(deptService.orcelDepart());
    }
}
