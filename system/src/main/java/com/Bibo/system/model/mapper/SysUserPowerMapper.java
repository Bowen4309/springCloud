package com.Bibo.system.model.mapper;

import com.Bibo.system.model.pojo.entity.SysUserPower;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
public interface SysUserPowerMapper extends BaseMapper<SysUserPower> {

    /**
     * 对用户进行授权
     * @param sql 语句
     * @return 改变行数
     */
    @Insert("insert into sys_user_power(user_id, power_id) values ${sql}")
    public int authUserPower(@Param("sql") String sql);

    /**
     * 查询用户的权限
     * @param userId 用户ID
     * @return 结果
     */
    @Select("select power_id from sys_user_power where user_id = #{userId}")
    public Set<String> selectPowerList(@Param("userId") String userId);
}
