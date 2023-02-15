package com.Bibo.system.model.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.system.model.mapper.SysUserPowerMapper;
import com.Bibo.system.model.pojo.dto.UserPowerDTO;
import com.Bibo.system.model.pojo.entity.SysUserPower;
import com.Bibo.system.model.service.ISysUserPowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-10-22
 */
@Service
public class SysUserPowerServiceImpl extends ServiceImpl<SysUserPowerMapper, SysUserPower> implements ISysUserPowerService {

    /**
     * 对用户进行授权
     * @param userPowerDTO 授权对象
     * @return 改变行数
     */
    @Override
    @Transactional
    public void authUserPower(UserPowerDTO userPowerDTO){
        // 先删除用户原先绑定权限
        this.baseMapper.delete(new QueryWrapper<SysUserPower>().eq("user_id",userPowerDTO.getUserId()));
        // 判断是否存在权限，存在就拼接成SQL
        if (userPowerDTO.getPowerIds().size() > 0){
            StringBuilder stringBuilder = new StringBuilder();
            Set<String> powerSet = new HashSet<>();
            powerSet.add("0");
            for (String powerId : userPowerDTO.getPowerIds()){
                for (String id : powerId.split(",")){
                    if (powerSet.contains(id)){
                        continue;
                    }else {
                        powerSet.add(id);
                        stringBuilder.append("('").append(userPowerDTO.getUserId()).append("','").append(id).append("'),");
                    }

                }
            }
            String sql = stringBuilder.substring(0, stringBuilder.length()-1);
            this.baseMapper.authUserPower(sql);
        }
    }
}
