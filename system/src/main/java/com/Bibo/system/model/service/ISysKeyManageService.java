package com.Bibo.system.model.service;

import com.Bibo.system.model.pojo.entity.SysKeyManage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.system.model.pojo.dto.KeyManageDTO;
import com.Bibo.system.model.pojo.dto.KeyManagePageReqDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdh
 * @since 2021-09-17
 */
public interface ISysKeyManageService extends IService<SysKeyManage> {

    public IPage<KeyManageDTO> selectKeyManagePageList(KeyManagePageReqDTO dtp);

}
