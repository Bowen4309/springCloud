package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.system.model.pojo.dto.UserPowerDTO;
import com.Bibo.system.model.pojo.entity.SysUserPower;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
public interface ISysUserPowerService extends IService<SysUserPower> {

    /**
     * 对用户进行授权
     * @param userPowerDTO 授权对象
     * @return 改变行数
     */
    void authUserPower(UserPowerDTO userPowerDTO);
}
