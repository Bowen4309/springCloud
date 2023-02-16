package com.Bibo.system.model.service;

import com.Bibo.system.model.pojo.entity.SysCustomFieldValue;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ISysCustomFieldValueService extends IService<SysCustomFieldValue> {


    Integer selectMaxRowNo(String formId);

    SysCustomFieldValue selectCreateTime(Integer rowNo, String formId);


}
