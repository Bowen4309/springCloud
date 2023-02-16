package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysCustomField;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysCustomFieldMapper extends BaseMapper<SysCustomField> {


    @Select("select * from sys_custom_field where form_id = #{formId} and field_name = #{fieldName}")
    List<SysCustomField> selectFieldByFormIdAndFieldName(@Param("formId") String formId,@Param("fieldName") String field_name);

    @Delete(" update sys_custom_field set is_delete = 1 where form_id = #{formId}")
    void deleteAllByFormId(@Param("formId") String formId);

}
