package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysBusinessTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Bibo.system.model.pojo.dto.BusinessTotalApproveDTO;
import com.Bibo.common.pojo.entity.SysBusinessUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface SysBusinessTotalMapper extends BaseMapper<SysBusinessTotal> {

    @Insert("<script>" +
            "insert into sys_business_user(id, business_total_id, user_id,is_default,status,create_time ) values" +
            "<foreach collection='sysBusinessUser' item = 'item' index='index' separator = ','  >" +
            "   ( #{item.id}, #{item.businessTotalId}, #{item.userId},#{item.isDefault},#{item.status},#{item.createTime} )" +
            "</foreach> "+
            "</script>")
    void saveUserBusinessTotal(@Param("sysBusinessUser") List<SysBusinessUser> sysBusinessUsers);

    @Select("select u.user_id  from sys_user u left join sys_business_user b on b.user_id = u.user_id " +
            "where b.id is null GROUP BY u.user_id HAVING count(b.id)<1")
    List<String> getNoDefault();

    @Delete("delete from sys_business_user where business_total_id = #{businessTotalId} and is_default = '0'")
    void removeUserChoose(String businessTotalId);

    @Update("<script>" +
            "update sys_business_total set status = #{status} where id in (" +
            "<foreach collection='ids' item = 'item' index='index' separator = ','  >" +
            "  #{item} " +
            "</foreach> )"+
            "</script>")
    void updateStatus(@Param("ids") List<String> ids,@Param("status") String status);


    @Select("<script>" +
            "select  * from sys_business_total where id in (" +
            "<foreach collection='idList' item = 'item' index='index' separator = ','  >" +
            "  #{item} " +
            "</foreach> )"+
            "</script>")
    List<SysBusinessTotal> findByIndexIds(@Param("idList") List<String> idList);

    @Select("select count(0) as cn, business_total_id  from sys_business_user group by business_total_id")
    List<Map<String,Object>> totalUse();


    @Select("(select a.status,b.name,b.id,b.parent_name,b.level,b.show_type,b.create_time,b.remark\n" +
            "from sys_approve a LEFT JOIN sys_business_total b on position(b.id  in a.approve_object_detail_id) = 1  " +
            "where a.type = '3' and a.user_id = #{userId} and b.id is not null order by a.create_time asc )\n" +
            "UNION ALL\n" +
            "(select (CASE when u.status ='1'  then '2' else '4' end ) as status,b.name,b.id,b.parent_name,b.level,b.show_type,b.create_time,b.remark \n" +
            "from  sys_business_user as u LEFT JOIN sys_business_total b  on b.id = u.business_total_id  " +
            "LEFT join sys_approve a on a.approve_object_detail_id = b.id and a.type = '3' " +
            "where u.user_id = #{userId} and a.id is null order by u.create_time asc)")
    List<BusinessTotalApproveDTO> findApproveData(@Param("userId") String userId);

    @Delete("delete from sys_business_user where business_total_id  = #{id} and user_id = #{userId}")
    void removeIndex(@Param("id") String id,@Param("userId") String userId);


    @Select("SELECT b.*  FROM sys_business_user u LEFT JOIN sys_business_total b ON b.ID = u.business_total_id " +
            "where u.status  = '1' and u.user_id = #{userId} and b.parent_name = #{parentName} ")
    List<SysBusinessTotal> getUserIndex(@Param("userId") String userId,@Param("parentName") String parentName);


}
