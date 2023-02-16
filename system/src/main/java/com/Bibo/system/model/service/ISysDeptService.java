package com.Bibo.system.model.service;

import com.Bibo.system.model.pojo.dto.TreeSelect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.pojo.entity.SysDept;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.DeptDTO;
import com.Bibo.system.model.pojo.dto.DeptListDTO;
import com.Bibo.common.pojo.vo.DeptListVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 根据条件查询部门列表
     * @param deptDTO 查询条件
     * @ret部门分页列表
     */
    public IPage<DeptListVO> selectDeptPageList(DeptListDTO deptDTO);

    /**
     * 校验部门名称是否唯一
     * @param dept 部门信息
     * @return 结果  true为存在  false为不存在
     */
    public boolean checkDeptNameUnique(DeptDTO dept);

    /**
     * 根据id查询所有子部门（正常状态）
     * @param deptId 部门id
     * @return 结果
     */
    public int selectNormalChildrenDeptById(String deptId);

    /**
     * 查询系统部门列表
     * @param dept 部门详细
     * @return 部门列表
     */
    public List<SysDept> selectDeptList(DeptListDTO dept);


    /**
     * 构建前段所需下拉树结构
     * @param depts 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 新增保存部门信息
     * @param deptDTO 部门信息
     * @return 结果
     */
    public boolean insertDept(DeptDTO deptDTO);

    /**
     * 修改编辑部门信息
     * @param deptDTO 部门信息
     * @return 结果
     */
    public Boolean updateDept(DeptDTO deptDTO);


    /**
     * 更改部门信息到Redis
     */
    public void renewDataToRedis();

    /**
     * 同步六合一部门表
     */
    List<Map<String,Object>> orcelDepart();


    Response synchronousDepart(List<Map<String,Object>> depts);
}
