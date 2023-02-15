package com.Bibo.system.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Bibo.system.model.pojo.entity.SysCustomFieldValue;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface SysCustomFieldValueMapper extends BaseMapper<SysCustomFieldValue> {


    @Select(" select max(default_row_no) from sys_custom_field_value where form_id =  #{formId}")
    Integer selectMaxRowNo(@Param("formId") String formId);

    @Select(" select * from sys_custom_field_value where form_id = #{formId} and field_name = '创建时间' and default_row_no = #{rowNo} ")
    SysCustomFieldValue selectCreateTime(@Param("rowNo") Integer rowNo, @Param("formId") String formId);

}
