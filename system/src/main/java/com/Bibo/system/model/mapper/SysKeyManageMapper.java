package com.Bibo.system.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.Bibo.system.model.pojo.dto.KeyManageDTO;
import com.Bibo.system.model.pojo.dto.KeyManagePageReqDTO;
import com.Bibo.system.model.pojo.entity.SysKeyManage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface SysKeyManageMapper extends BaseMapper<SysKeyManage> {



    @Select("<script>" +
            " select * from sys_key_manage where 1=1 " +
            "<if test=\"dto.depart != null and dto.depart != ''\">AND depart LIKE concat('%',#{dto.depart},'%') </if> " +
            " order by create_time desc" +
            "</script>")
    public IPage<KeyManageDTO> selectKeyManagePageList(Page page, @Param("dto") KeyManagePageReqDTO dto);



}
