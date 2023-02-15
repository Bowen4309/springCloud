package com.Bibo.plug.model.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface EsTagMapper {


    @Select("<script>" +
            "select tag_type_name as tagTypeName,tag_name as tagName,detail,describe from sys_tag where " +
            "<foreach collection='list' item = 'item' index='index' open='(' close=')'  separator = 'or' >" +
            "   tag_name  = #{item} " +
            "</foreach> "+
            "</script>")
    List<Map<String,Object>> getTagsType(@Param("list") List<String> tags);

    @Select("<script>" +
            "select tag_type_name as tagTypeName,tag_name as tagName,detail,describe from sys_tag where " +
            "<foreach collection='list' item = 'item' index='index' open='(' close=')'  separator = 'or' >" +
            "   tag_name  = #{item} " +
            "</foreach> " +
            " and (tag_type_name like concat('%',#{tagTypeName},'%') or tag_type_name like '%基本属性%' )"+
            "</script>")
    List<Map<String,Object>> getFacilityTagsType(@Param("list") List<String> tags, @Param("tagTypeName") String tagTypeName);

    @Select(" select dept_name from sys_dept where dept_id = '${deptId}' ")
    String getDeptNameByDeptId(@Param("deptId") String deptId);

    @Select(" select user_name as name, t2.dept_name , t1.police_code , case when sex = '1' then '男' when sex = '2' then '女' end as sex,t1.grid_id ,t1.grid_name \n" +
            "from sys_user t1\n" +
            "left join sys_dept t2 on t1.dept_id = t2.dept_id\n" +
            "where police_code = '${policeCode}' ")
    Map<String,String> getPoliceInfoByPoliceCode(@Param("policeCode") String policeCode);





}
