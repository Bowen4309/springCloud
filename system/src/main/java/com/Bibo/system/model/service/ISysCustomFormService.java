package com.Bibo.system.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Bibo.common.response.Response;
import com.Bibo.system.model.pojo.dto.*;
import com.Bibo.system.model.pojo.entity.SysCustomForm;
import org.apache.ibatis.annotations.Param;

public interface ISysCustomFormService extends IService<SysCustomForm> {

    /**
     * 表格编辑
     * @param dtoList
     * @return
     */
    Response saveForm(SysCustomFormFieldInsertDto dtoList);

    Response saveFormList(SysCustomFormFieldInsertObjDto dtoList);


    Response updateStatus(SysCustomFormFieldStatusUpdateDto dto);


    Response selectFormFieldList(Integer status);


    Response saveFieldValue(SysCustomFieldValueInsertDto dtoList);

    Response selectFieldValueList(SysCustomFieldValueListReqObjDto dtoList);


    Response selectFieldValueByRowNoAndFormId(String formId,Integer rowNo);




}
