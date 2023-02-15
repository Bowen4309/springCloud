package com.Bibo.system.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.common.pojo.entity.SysDept;
import com.Bibo.common.pojo.vo.DeptListVO;
import com.Bibo.system.model.pojo.dto.DeptListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {


    /**
     * 根据条件查询部门列表
     * @param deptDTO 查询条件
     * @return 部门列表
     */
    @Select("<script>" +
            "SELECT d.dept_id,d.dept_name,d.dept_address,d.parent_id,sd.dept_name parent_name,d.status,dd2.dict_label status_name,dd1.dict_value administrative,dd1.dict_label administrative_value,d.create_by,d.update_by,d.create_time,d.update_time \n" +
            "FROM sys_dept d LEFT JOIN sys_dept sd ON d.parent_id = sd.dept_id \n" +
            "LEFT JOIN sys_dict_data dd1 ON d.administrative = dd1.dict_value AND dd1.dict_type = 'sys_administrative' \n" +
            "LEFT JOIN sys_dict_data dd2 ON d.status = dd2.dict_value AND dd2.dict_type = 'sys_object_status' \n" +
            " WHERE d.status='0' " +
            "<if test=\"dept.deptName != null and dept.deptName != ''\">AND d.dept_name LIKE concat('%',#{dept.deptName},'%') </if>\n" +
            "<if test=\"dept.parentId != null and dept.parentId != ''\">AND d.parent_id =#{dept.parentId} </if>\n" +
            "<if test=\"dept.id != null and dept.id != ''\">AND d.dept_id =#{dept.deptId} </if>\n" +
            "ORDER BY d.create_time DESC" +
            "</script>")
    public IPage<DeptListVO> selectDeptPageList(Page<SysDept> page, @Param("dept") DeptListDTO deptDTO);

    /**
     * 修改部门子元素关系
     * @param deptStr 拼接后字符串的 case ~ end
     * @param deptIdStr 拼接后字符串
     * @return 结果
     */
    @Update("update sys_dept set ancestors = ${deptStr} where dept_id in ${deptIdStr}")
    public int updateDeptChildren(@Param("deptStr") String deptStr, @Param("deptIdStr") String deptIdStr);

    /**
     * 修改该部门的父级部门状态
     * @param deptStr 拼接后字符串
     * @return 结果
     */
    @Update("update sys_dept set status = '0' where dept_id in ${deptStr}")
    public int updateParentDeptStatusNormal(@Param("deptStr") String deptStr);


    @Select("select *from trff_app.v_frm_department_sjspb")
    List<Map<String,Object>> getDepartData();


    @Select("select * from trff_app.v_drivinglicense_sjspb where xm = '黄斌'")
    List<Map<String,Object>> getDriver();
}
