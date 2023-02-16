package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SysTagMapper extends BaseMapper<SysTag> {


    @Update("update sys_tag set status = #{status} where id =#{id}")
    void updateTagStatus(@Param("id") String id, @Param("status") String status);
}
