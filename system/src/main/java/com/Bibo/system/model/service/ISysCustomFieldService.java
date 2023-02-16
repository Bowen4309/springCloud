package com.Bibo.system.model.service;

import com.Bibo.system.model.pojo.entity.SysCustomField;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ISysCustomFieldService extends IService<SysCustomField> {

    List<SysCustomField> selectFieldByFormIdAndFieldName(String formId,  String fieldName);

    void deleteAllByFormId(String formId);;


}
