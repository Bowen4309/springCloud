package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysRolePower;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface SysRolePowerMapper extends BaseMapper<SysRolePower> {
    /**
     * 插入角色与权限关系
     * @param rolePower 拼接后的字符串
     * @return 结果
     */
    @Select("insert into sys_role_power(role_id, power_id) values ${rolePower}")
    public void insertRolePower(@Param("rolePower") String rolePower);
}
