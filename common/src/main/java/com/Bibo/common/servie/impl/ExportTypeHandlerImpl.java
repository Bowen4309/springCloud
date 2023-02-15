package com.Bibo.common.servie.impl;

import com.Bibo.common.constant.ExportTypeEnum;
import com.Bibo.common.servie.ExportTypeHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 枚举查询接口实现类
 */
@Service
public class ExportTypeHandlerImpl implements ExportTypeHandler {

    @Override
    public String getValue(String type) throws Exception {
        if(StringUtils.isEmpty(type)){
            return "";
        }
        if(StringUtils.isEmpty(ExportTypeEnum.getDescription(type).getTypeDoc())){
            return "";
        }
        return ExportTypeEnum.getDescription(type).getTypeDoc();
    }
}
