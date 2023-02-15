package com.Bibo.system.model.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.system.model.mapper.SysCustomFieldValueMapper;
import com.Bibo.system.model.pojo.entity.SysCustomFieldValue;
import com.Bibo.system.model.service.ISysCustomFieldValueService;
import org.springframework.stereotype.Service;

@Service
public class SysCustomFieldValueServiceImpl extends ServiceImpl<SysCustomFieldValueMapper, SysCustomFieldValue> implements ISysCustomFieldValueService {

    @Override
    public Integer selectMaxRowNo(String formId) {
        return this.baseMapper.selectMaxRowNo(formId);
    }

    @Override
    public SysCustomFieldValue selectCreateTime(Integer rowNo, String formId) {
        return this.baseMapper.selectCreateTime(rowNo, formId);
    }

}
