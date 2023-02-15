package com.Bibo.system.model.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.constant.UserConstants;
import com.Bibo.common.pojo.entity.SysDept;
import com.Bibo.common.pojo.vo.DeptListVO;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.pojo.vo.DeptRedisVO;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.mapper.SysDeptMapper;
import com.Bibo.system.model.pojo.dto.DeptDTO;
import com.Bibo.system.model.pojo.dto.DeptListDTO;
import com.Bibo.system.model.pojo.dto.TreeSelect;
import com.Bibo.system.model.pojo.vo.DeptTreeVO;
import com.Bibo.system.model.service.ISysDeptService;
import org.springframework.stereotype.Service;

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
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    /**
     * 根据条件查询部门列表
     * @param deptDTO 查询条件
     * @return 部门列表
     */
    public IPage<DeptListVO> selectDeptPageList(DeptListDTO deptDTO) {
        Page<SysDept> page = new Page(deptDTO.getPageNum(), deptDTO.getPageSize());
        return this.baseMapper.selectDeptPageList(page, deptDTO);
    }

    /**
     * 校验部门名称是否唯一
     * @param dept 部门信息
     * @return 结果  true为存在  false为不存在
     */
    public boolean checkDeptNameUnique(DeptDTO dept){
        String deptId = ObjectUtil.isNull(dept.getDeptId())?"-1":dept.getDeptId();
        SysDept info = this.baseMapper.selectOne(new QueryWrapper<SysDept>()
                .eq("parent_id",dept.getParentId())
                .eq("dept_name",dept.getDeptName()));
        return ObjectUtil.isNotNull(info)&&!info.getDeptId().equals(deptId)?true:false;
    }

    /**
     * 根据id查询所有子部门（正常状态）
     * @param deptId 部门id
     * @return 结果
     */
    public int selectNormalChildrenDeptById(String deptId){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>().eq("status","0");
        queryWrapper.like(true,"ancestors", deptId);
        return count(queryWrapper);
    }

    /**
     * 新增保存部门信息
     * @param deptDTO 部门信息
     * @return 结果
     */
    public boolean insertDept(DeptDTO deptDTO){
        SysDept parentDept = getById(deptDTO.getParentId());
        // 如果父节点不是正常状态，则无法新增子节点
        if (ObjectUtil.isNull(parentDept)&&!deptDTO.getParentId().equals("0")){
            log.error("不存在父节点");
            return false;
        } else if (ObjectUtil.isNotNull(parentDept)&&!parentDept.getStatus().equals("0")){
            log.error("部门停用，不允许添加子节点");
//            throw new APIException("部门停用，不允许添加子节点");
            return false;
        }
        String id = RandomUtil.randomNumbers(12);
        SysDept dept = new SysDept();
        BeanUtil.copyProperties(deptDTO, dept);
        dept.setDeptId(id);
        dept.setCreateBy(RedisUtil.getUserByRedis().getUserName());
        dept.setCreateTime(new Date());
        if (dept.getParentId().equals("0")){
            dept.setAncestors("0"+","+dept.getDeptId());
        }else{
            dept.setAncestors(parentDept.getAncestors()+","+dept.getDeptId());
        }
        return save(dept);
    }


    /**
     * 更改部门信息到Redis
     */
    public void renewDataToRedis(){
        RedisUtil.del(UserConstants.DEPT_ID_KEY+"*");
        List<SysDept> deptList = list();
        for (SysDept dept : deptList){
            String deptIds = this.baseMapper.selectList(new QueryWrapper<SysDept>().like(true, "ancestors", dept.getDeptId()).orderByAsc("dept_id"))
                    .stream().map(s->"'"+s.getDeptId()+"'").reduce((a,b) -> a+","+b).orElse("");
            if (deptIds.equals("")){
                System.out.println(dept.getDeptId());
            }
            String childSql = "("+deptIds+")";
            DeptRedisVO deptRedisVO = new DeptRedisVO();
            BeanUtil.copyProperties(dept, deptRedisVO);
            deptRedisVO.setChildStrSql(childSql);
            RedisUtil.set(UserConstants.DEPT_ID_KEY+dept.getDeptId(), deptRedisVO);
        }
    }

    @Override
    @DS("branch")
    public List<Map<String,Object>> orcelDepart() {
        List<Map<String,Object>> departData = this.baseMapper.getDepartData();
        return departData;
    }

    @Override
    public Response synchronousDepart(List<Map<String, Object>> depts) {
        List<SysDept> sysDeptList = new ArrayList<>();
        List<String> newDept = new ArrayList<>();
        depts.forEach(depart ->{
            SysDept sysDept = this.baseMapper.selectById(depart.get("SJBM").toString());
            if(null != sysDept){
                sysDept.setDeptId(depart.get("SJBM").toString());
                sysDept.setParentId(null != depart.get("SJSGZDBM")? depart.get("SJSGZDBM").toString():"");
                sysDept.setDeptName(depart.get("BMMC").toString());
                sysDeptList.add(sysDept);
            }
            newDept.add(depart.get("SJBM").toString()+"-----"+depart.get("BMMC").toString());
        });
        newDept.forEach(o ->{
            System.out.println(o);
        });
        return Response.success().data(sysDeptList);
    }


    /**
     * 修改编辑部门信息
     * @param deptDTO 部门信息
     * @return 结果
     */
    public Boolean updateDept(DeptDTO deptDTO){
        SysDept newParentDept = getById(deptDTO.getParentId());
        SysDept oldDpt = getById(deptDTO.getDeptId());
        SysDept dept = new SysDept();
        BeanUtil.copyProperties(deptDTO, dept);
        dept.setStatus(oldDpt.getStatus());
        dept.setAncestors(oldDpt.getAncestors());
        // 后期添加userid获取
        dept.setUpdateBy(RedisUtil.getUserByRedis().getUserName());
        dept.setUpdateTime(new Date());
        if (ObjectUtil.isNotNull(newParentDept) && ObjectUtil.isNotNull(oldDpt)){
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDpt.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        // 节点状态正常
        if (!dept.getParentId().equals("0")&&dept.getStatus().equals("0")){
            updateParentDeptStatusNormal(dept);
        }
        return updateById(dept);
    }

    /**
     * 修改部门子元素关系
     * @param deptId 被修改的部门id
     * @param newAncestors 新的父id集合
     * @param oldAncestors 旧的父id集合
     */
    private void updateDeptChildren(String deptId, String newAncestors, String oldAncestors){
        List<SysDept> children = list(new QueryWrapper<SysDept>().like(true, "ancestors",deptId));
        if (children.size()>0){
            // 查询子元素id的拼接in语句，拼接后为 ('123','124')
            StringBuilder deptIdStr = new StringBuilder().append("('");
            // 拼接 不同id匹配不同ancestors，拼接后为case dept_id when '123' then '0,1' when '124' then '0,1,2' end
            StringBuilder deptStr = new StringBuilder().append("case dept_id ");
            for (SysDept child:children){
                child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
                deptIdStr.append(child.getDeptId()).append("','");
                deptStr.append("when '").append(child.getDeptId()).append("' then '").append(child.getAncestors()).append("' ");
            }
            deptStr.append("end");
            // 去除后面的的 ,'两个字符并添加 ）结尾
            this.baseMapper.updateDeptChildren(deptStr.toString(), deptIdStr.substring(0,deptIdStr.toString().length()-2)+")");
        }
    }

    /**
     * 修改该部门的父级部门状态
     * @param dept 部门信息
     */
    public void updateParentDeptStatusNormal(SysDept dept){
        String ancestors = dept.getAncestors();
        String[] deptIds = ancestors.split(",");
        // 修改父元素id的拼接in语句，拼接后为 ('123','124')
        StringBuilder deptStr = new StringBuilder().append("('");
        for (String deptId:deptIds) {
            deptStr.append(deptId).append("'").append(",'");
        }
        this.baseMapper.updateParentDeptStatusNormal(deptStr.substring(0,deptStr.toString().length()-2)+")");
    }

    /**
     * 查询系统部门列表
     * @param dept 部门详细
     * @return 部门列表
     */
    public List<SysDept> selectDeptList(DeptListDTO dept){
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>().eq("status","0").orderByDesc("create_time");
        if (ObjectUtil.isNotNull(dept.getParentId()) && !dept.getParentId().equals("")){
            queryWrapper.eq("parent_id", dept.getParentId());
        }
        if (ObjectUtil.isNotNull(dept.getDeptName()) && !dept.getDeptName().equals("")){
            queryWrapper.like(true, "dept_name", dept.getDeptName());
        }
        return list(queryWrapper);
    }

    /**
     * 构建前段所需下拉树结构
     * @param depts 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts){
        List<DeptTreeVO> deptTree = buildDeptTree(depts);
        return deptTree.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前段所需的树结构
     * @param depts 菜单列表
     * @return 树结构列表
     */
    private List<DeptTreeVO> buildDeptTree(List<SysDept> depts){
        List<DeptTreeVO> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (SysDept dept : depts){
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();){
            SysDept dept = iterator.next();
            DeptTreeVO deptDTO = new DeptTreeVO();
            BeanUtil.copyProperties(dept, deptDTO);
            //如果是顶级节点，遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())){
                recursionFn(depts, deptDTO);
                returnList.add(deptDTO);
            }
        }
        if (returnList.isEmpty()){
            return null;
        }
        return returnList;
    }

    /**
     * 递归列表
     * @param list 权限列表
     * @param dept 权限信息
     */
    private void recursionFn(List<SysDept> list, DeptTreeVO dept){
        List<DeptTreeVO> childList = getChildList(list, dept);
        dept.setChildren(childList);
        for(DeptTreeVO pChild : childList){
            if (getChildList(list, pChild).size()>0){
                recursionFn(list, pChild);
            }
        }
    }

    /**
     * 获取子节点列表
     * @param list 权限列表
     * @param dept 权限信息
     * @return 子节点列表
     */
    private List<DeptTreeVO> getChildList(List<SysDept> list, DeptTreeVO dept){
        List<DeptTreeVO> pList = new ArrayList<>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()){
            SysDept d = it.next();
            DeptTreeVO deptDTO = new DeptTreeVO();
            BeanUtil.copyProperties(d, deptDTO);
            if (deptDTO.getParentId().equals(dept.getDeptId())){
                pList.add(deptDTO);
            }
        }
        return pList;
    }
}
