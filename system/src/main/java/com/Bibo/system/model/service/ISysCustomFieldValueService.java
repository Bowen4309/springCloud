package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.system.model.pojo.entity.SysCustomFieldValue;
import org.apache.ibatis.annotations.Param;

public interface ISysCustomFieldValueService extends IService<SysCustomFieldValue> {


    Integer selectMaxRowNo(String formId);

    SysCustomFieldValue selectCreateTime(Integer rowNo, String formId);


}
