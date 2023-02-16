package com.Bibo.system.model.service.impl;

import com.Bibo.system.model.pojo.entity.SysCustomField;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.system.model.mapper.SysCustomFieldMapper;
import com.Bibo.system.model.service.ISysCustomFieldService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysCustomFieldServiceImpl extends ServiceImpl<SysCustomFieldMapper, SysCustomField> implements ISysCustomFieldService {


    @Override
    public List<SysCustomField> selectFieldByFormIdAndFieldName(String formId, String fieldName) {
        return this.baseMapper.selectFieldByFormIdAndFieldName(formId, fieldName);
    }

    @Override
    public void deleteAllByFormId(String formId) {
        this.baseMapper.deleteAllByFormId(formId);
    }
}
