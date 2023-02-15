package com.Bibo.system.model.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.system.model.mapper.SysKeyManageMapper;
import com.Bibo.system.model.pojo.dto.KeyManageDTO;
import com.Bibo.system.model.pojo.dto.KeyManagePageReqDTO;
import com.Bibo.system.model.pojo.entity.SysKeyManage;
import com.Bibo.system.model.service.ISysKeyManageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author xdh
 * @since 2021-10-19
 */
@Service
public class SysKeyManageServiceImpl extends ServiceImpl<SysKeyManageMapper, SysKeyManage> implements ISysKeyManageService {


    @Override
    public IPage<KeyManageDTO> selectKeyManagePageList(KeyManagePageReqDTO dtp) {
        Page<KeyManageDTO> page = new Page(dtp.getPageNum(), dtp.getPageSize());
        return this.baseMapper.selectKeyManagePageList(page,dtp);
    }
}
