package com.Bibo.system.model.service.impl;

import com.Bibo.system.model.pojo.dto.*;
import com.Bibo.system.model.pojo.entity.SysCustomField;
import com.Bibo.system.model.pojo.entity.SysCustomFieldValue;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Bibo.common.config.redisConfig.RedisUtil;
import com.Bibo.common.pojo.LoginUser;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.DateUtils;
import com.Bibo.system.model.mapper.SysCustomFormMapper;
import com.xdh.traffic_system.model.pojo.dto.*;
import com.Bibo.system.model.pojo.entity.SysCustomForm;
import com.Bibo.system.model.service.ISysCustomFieldService;
import com.Bibo.system.model.service.ISysCustomFieldValueService;
import com.Bibo.system.model.service.ISysCustomFormService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysCustomFormServiceImpl extends ServiceImpl<SysCustomFormMapper, SysCustomForm> implements ISysCustomFormService {

    @Autowired
    private ISysCustomFieldService fieldService;

    @Autowired
    private ISysCustomFieldValueService fieldValueService;


    @Override
    public Response saveForm(SysCustomFormFieldInsertDto dtoList) {
//        if (!(dtoList != null && dtoList.size() > 0)){
//            return Response.error("数据不能为空！");
//        }
//
//        for (SysCustomFormFieldInsertDto dto : dtoList) {
            Integer level = dtoList.getLevel();
            switch (level){
                case 1:
                    saveOrUpdateForm(dtoList);
                    break;
                case 2:
                    if(StringUtils.isBlank(dtoList.getParentId())){
                        return Response.error("父节点id不能为空！");
                    }
                    saveOrUpdateForm(dtoList);
                    break;
                case 3:
                    if(StringUtils.isBlank(dtoList.getParentId())){
                        return Response.error("父节点id不能为空！");
                    }
                    saveOrUpdateForm(dtoList);
                    break;
                case 4:
                    if(StringUtils.isBlank(dtoList.getParentId())){
                        return Response.error("父节点id不能为空！");
                    }
                    saveOrUpdateField(dtoList);
                    break;
                default:
                    return Response.error("level不能为空！");
            }

//        }
        return Response.success();
    }

    @Override
    public Response saveFormList(SysCustomFormFieldInsertObjDto obj) {
        List<SysCustomFormFieldInsertDto> dtoList = obj.getList();
        if (!(dtoList != null && dtoList.size() > 0)){
            return Response.error("数据不能为空！");
        }
        String parentId = obj.getParentId();
        Integer level = obj.getLevel();
        if(StringUtils.isBlank(parentId) || level == null){
            return Response.error("parentId或level不能为空！");
        }

        //如果是字段，先将全部字段软删
        if(level == 4){
            fieldService.deleteAllByFormId(parentId);
        }


        for (SysCustomFormFieldInsertDto dto : dtoList) {
            dto.setLevel(level);
            dto.setParentId(parentId);
            switch (level){
                case 1:
                    saveOrUpdateForm(dto);
                    break;
                case 2:
                    if(StringUtils.isBlank(dto.getParentId())){
                        return Response.error("父节点id不能为空！");
                    }
                    saveOrUpdateForm(dto);
                    break;
                case 3:
                    if(StringUtils.isBlank(dto.getParentId())){
                        return Response.error("父节点id不能为空！");
                    }
                    saveOrUpdateForm(dto);
                    break;
                case 4:
                    if(StringUtils.isBlank(dto.getParentId())){
                        return Response.error("父节点id不能为空！");
                    }
                    saveOrUpdateField(dto);
                    break;
                default:
                    return Response.error("level不能为空！");
            }
        }
        return Response.success();
    }

    @Override
    public Response updateStatus(SysCustomFormFieldStatusUpdateDto dto) {
        Integer status = dto.getStatus();
        Integer isDelete = dto.getIsDelete();
        if(dto.getLevel() == 1 || dto.getLevel() == 2 || dto.getLevel() == 3){
            SysCustomForm form = this.baseMapper.selectById(dto.getId());
            if(status != null){
                form.setStatus(status);
            }
            if(isDelete != null){
                form.setIsDelete(isDelete);
            }
            this.baseMapper.updateById(form);
        }
        if(dto.getLevel() == 4){
            SysCustomField field = fieldService.getById(dto.getId());
            if(status != null){
                field.setStatus(status);
            }
            if(isDelete != null){
                field.setIsDelete(isDelete);
            }
            fieldService.updateById(field);
        }
        return Response.success();
    }

    @Override
    public Response selectFormFieldList(Integer status) {
        //先查询第一层级的
        QueryWrapper<SysCustomForm> formQueryWrapper = new QueryWrapper<>();
        formQueryWrapper.eq("level",1);
        if(status != null){
            formQueryWrapper.eq("status",status);
        }
        formQueryWrapper.eq("is_delete",0);
        formQueryWrapper.orderByAsc("show_sort");
        List<SysCustomForm> sysCustomForms = this.baseMapper.selectList(formQueryWrapper);

        ArrayList<SysCustomFormListDto> sysCustomFormListDtos = new ArrayList<>();
        if (!(sysCustomForms != null && sysCustomForms.size() > 0)){
            return Response.success().data(sysCustomFormListDtos);
        }

        for (SysCustomForm form : sysCustomForms) {
            SysCustomFormListDto dto = new SysCustomFormListDto();
            BeanUtils.copyProperties(form,dto);
            dto.setFieldName(form.getName());
            sysCustomFormListDtos.add(dto);
        }


        //对第一层遍历
        for (SysCustomFormListDto listDto : sysCustomFormListDtos) {

            String id = listDto.getId();
            formQueryWrapper = new QueryWrapper<>();
            formQueryWrapper.eq("level",2);
            if(status != null){
                formQueryWrapper.eq("status",status);
            }
            formQueryWrapper.eq("is_delete",0);
            formQueryWrapper.orderByAsc("show_sort");
            formQueryWrapper.eq("parent_id",id);
            List<SysCustomForm> sysCustomForms2 = this.baseMapper.selectList(formQueryWrapper);
            ArrayList<SysCustomFormListDto> sysCustomFormListDto2s = new ArrayList<>();

            if (!(sysCustomForms2 != null && sysCustomForms2.size() > 0)){
                continue;
            }

            for (SysCustomForm form : sysCustomForms2) {
                SysCustomFormListDto dto = new SysCustomFormListDto();
                BeanUtils.copyProperties(form,dto);
                dto.setFieldName(form.getName());

                //封装第三层
                formQueryWrapper = new QueryWrapper<>();
                formQueryWrapper.eq("level",3);
                if(status != null){
                    formQueryWrapper.eq("status",status);
                }
                formQueryWrapper.eq("is_delete",0);
                formQueryWrapper.orderByAsc("show_sort");
                formQueryWrapper.eq("parent_id",dto.getId());
                List<SysCustomForm> sysCustomForms3 = this.baseMapper.selectList(formQueryWrapper);

                if (sysCustomForms3 != null && sysCustomForms3.size() > 0){
                    ArrayList<SysCustomFormListDto> sysCustomFormListDto3s = new ArrayList<>();
                    for (SysCustomForm sysCustomForm : sysCustomForms3) {
                        SysCustomFormListDto dto3 = new SysCustomFormListDto();
                        BeanUtils.copyProperties(sysCustomForm,dto3);
                        dto3.setFieldName(sysCustomForm.getName());

                        dto3.setFields(getFields(dto3.getId(),status));
                        //封装字段
                        sysCustomFormListDto3s.add(dto3);
                    }
                    //封装第三层  停用不封装
                   // if(dto.getStatus() != null && dto.getStatus() == 1){
                        dto.setForms(sysCustomFormListDto3s);
                   // }

                }

                sysCustomFormListDto2s.add(dto);
            }
            //封装第二层
           // if(listDto.getStatus() != null && listDto.getStatus() == 1){
                listDto.setForms(sysCustomFormListDto2s);
            //}
        }
        return Response.success().data(sysCustomFormListDtos);
    }

    List<SysCustomFieldListDto> getFields(String formId , Integer status){
        //对SysCustomFormTwoListDto 封装第四层
        QueryWrapper<SysCustomField> fieldQueryWrapper = new QueryWrapper<>();
        if(status != null){
            fieldQueryWrapper.eq("status",status);
        }
        fieldQueryWrapper.eq("is_delete",0);
        fieldQueryWrapper.orderByAsc("show_sort");
        fieldQueryWrapper.eq("form_id",formId);
        List<SysCustomField> sysCustomFields = fieldService.getBaseMapper().selectList(fieldQueryWrapper);
        if(sysCustomFields != null && sysCustomFields.size() > 0){
            ArrayList<SysCustomFieldListDto> sysCustomFieldListDtos = new ArrayList<>();
            for (SysCustomField field : sysCustomFields) {
                SysCustomFieldListDto dto1 = new SysCustomFieldListDto();
                BeanUtils.copyProperties(field,dto1);
                dto1.setLevel(4);
                sysCustomFieldListDtos.add(dto1);
            }
            return sysCustomFieldListDtos;
        }
        return new ArrayList<>();
    }




    @Override
    public Response saveFieldValue(SysCustomFieldValueInsertDto dto) {
        List<SysCustomFieldValueDto> dtoList = dto.getDtoList();
        if (!(dtoList != null && dtoList.size() > 0)){
            return Response.error("数据不能为空！");
        }

        String formId = dto.getFormId();
        Integer rowNo = fieldValueService.selectMaxRowNo(formId);
        rowNo = rowNo == null ? 1 : rowNo + 1;

        Integer isNew = 0;
        Date now = new Date();
        for (SysCustomFieldValueDto valueDto : dtoList) {
            valueDto.setFormId(formId);
            SysCustomFieldValue value = new SysCustomFieldValue();
            BeanUtils.copyProperties(valueDto,value);
            if(StringUtils.isBlank(value.getId())){
                //新增
                value.setCreateTime(now);
                value.setDefaultRowNo(rowNo);
                isNew = 1;
            }
            value.setUpdateTime(now);
            fieldValueService.saveOrUpdate(value);
        }

        //查询是否有创建时间字段
        List<SysCustomField> createTime = fieldService.selectFieldByFormIdAndFieldName(formId, "创建时间");
        if(createTime != null && createTime.size() > 0 && isNew == 1){
            SysCustomField sysCustomField = createTime.get(0);

            SysCustomFieldValue createTimeField = new SysCustomFieldValue();

            createTimeField.setFieldId(sysCustomField.getId());
            createTimeField.setFormId(sysCustomField.getFormId());
            createTimeField.setVarcharValue(DateUtils.getDateToStr(new Date()));
            createTimeField.setIsDelete(0);
            createTimeField.setCreateTime(new Date());
            createTimeField.setUpdateTime(new Date());
            createTimeField.setDefaultRowNo(rowNo);
            createTimeField.setFieldName(sysCustomField.getFieldName());
            fieldValueService.save(createTimeField);
        }

        //查询是否有创建人字段
        List<SysCustomField> createUser = fieldService.selectFieldByFormIdAndFieldName(formId, "创建人");
        if(createUser != null && createUser.size() > 0 && isNew == 1){
            SysCustomField sysCustomField = createUser.get(0);

            SysCustomFieldValue createTimeField = new SysCustomFieldValue();

            createTimeField.setFieldId(sysCustomField.getId());
            createTimeField.setFormId(sysCustomField.getFormId());
            createTimeField.setVarcharValue(RedisUtil.getUserByRedis().getUserName());
            createTimeField.setIsDelete(0);
            createTimeField.setCreateTime(new Date());
            createTimeField.setUpdateTime(new Date());
            createTimeField.setDefaultRowNo(rowNo);
            createTimeField.setFieldName(sysCustomField.getFieldName());
            fieldValueService.save(createTimeField);
        }


        return Response.success();
    }

    @Override
    public Response selectFieldValueList(SysCustomFieldValueListReqObjDto obj) {
        String formId = obj.getFormId();
        Integer pageSize = obj.getPageSize();
        Integer pageNum = obj.getPageNum();

        List<SysCustomFieldValueListReqDto> dtoList = obj.getDtoList();

        QueryWrapper<SysCustomFieldValue> wrapper = new QueryWrapper<>();
        if(dtoList != null && dtoList.size() > 0){

        }
        wrapper.eq("form_id",formId);
        wrapper.isNotNull("field_id");
        // wrapper.eq("is_delete",0);
        Integer maxRowNo = fieldValueService.selectMaxRowNo(formId);


        List<SysCustomFieldValue> sysCustomFieldValues = fieldValueService.getBaseMapper().selectList(wrapper);
        if (!(sysCustomFieldValues != null && sysCustomFieldValues.size() > 0)){
            return Response.success();
        }

        ArrayList<SysCustomFieldValueDto> sysCustomFieldValueDtos = new ArrayList<>();
        for (SysCustomFieldValue value : sysCustomFieldValues) {
            SysCustomFieldValueDto valueDto = new SysCustomFieldValueDto();
            BeanUtils.copyProperties(value,valueDto);
            sysCustomFieldValueDtos.add(valueDto);
        }

        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 1; i <= maxRowNo; i++) {

            JSONObject jsonObject = new JSONObject();

            for (SysCustomFieldValueDto valueDto : sysCustomFieldValueDtos) {

                if(valueDto.getDefaultRowNo() == i){
                    jsonObject.put("key" + valueDto.getFieldId(),valueDto.getVarcharValue());
                }
            }
            jsonObject.put("keydefaultRowNo",i);
            jsonObjects.add(jsonObject);
        }

        //对jsonObjects 分页
        Integer total = jsonObjects.size();
        Integer pageSum = total % pageSize == 0 ? total /pageSize : total /pageSize + 1;
        List<JSONObject> data = jsonObjects.stream().skip((pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());

        return Response.success().data(data,total.longValue(),pageSum.longValue());
    }

    @Override
    public Response selectFieldValueByRowNoAndFormId(String formId, Integer rowNo) {
        QueryWrapper<SysCustomFieldValue> wrapper = new QueryWrapper<>();
        wrapper.eq("form_id",formId);
        wrapper.eq("default_row_no",rowNo);
        List<SysCustomFieldValue> sysCustomFieldValues = fieldValueService.getBaseMapper().selectList(wrapper);
        ArrayList<SysCustomFieldValueDto> dtoList = new ArrayList<>();
        if(sysCustomFieldValues != null & sysCustomFieldValues.size() > 0){
            for (SysCustomFieldValue value : sysCustomFieldValues) {
                SysCustomFieldValueDto dto = new SysCustomFieldValueDto();
                BeanUtils.copyProperties(value,dto);
                dtoList.add(dto);
            }
        }
        return Response.success().data(dtoList);
    }


    void saveOrUpdateForm(SysCustomFormFieldInsertDto dto){
        if(StringUtils.isBlank(dto.getId())){
            //新增
            SysCustomForm form = new SysCustomForm();
            LoginUser user = RedisUtil.getUserByRedis();
            if(!ObjectUtils.isEmpty(user)){
                form.setCreateUserId(user.getUserId());
                form.setCreateUserName(user.getUserName());
            }
            form.setCreateTime(new Date());
            form.setUpdateTime(new Date());
            form.setShowSort(dto.getShowSort());
            form.setLevel(dto.getLevel());
            form.setParentId(dto.getParentId());
            form.setStatus(0);
            form.setIsDelete(0);
            form.setName(dto.getFieldName());
            this.baseMapper.insert(form);
        }else{
            //修改
            String id = dto.getId();
            SysCustomForm form = this.baseMapper.selectById(id);
            form.setName(dto.getFieldName());
            form.setShowSort(dto.getShowSort());
            form.setUpdateTime(new Date());
            form.setStatus(dto.getStatus());
            this.baseMapper.updateById(form);
        }
    }


    void saveOrUpdateField(SysCustomFormFieldInsertDto dto){
        if(StringUtils.isBlank(dto.getId())){
            //新增
            SysCustomField field = new SysCustomField();
            field.setFormId(dto.getParentId());
            field.setFieldName(dto.getFieldName());
            field.setFieldType(dto.getFieldType());
            field.setIsCondition(dto.getIsCondition());
            field.setIsEdit(dto.getIsEdit());
            field.setIsShow(dto.getIsShow());
            field.setDescription(dto.getDescription());
            field.setFieldsAttr(dto.getFieldsAttr());
            field.setShowSort(dto.getShowSort());
            field.setConditionSort(dto.getConditionSort());
            field.setEditSort(dto.getEditSort());
            field.setIsRequired(dto.getIsRequired());
            field.setDefaultValue(dto.getDefaultValue());
            field.setIsDelete(0);
            field.setStatus(dto.getStatus());
            field.setCreateTime(new Date());
            field.setUpdateTime(new Date());
            field.setStatus(1);
            fieldService.save(field);

        }else{
            //修改
            SysCustomField field = new SysCustomField();
            field.setId(dto.getId());
            field.setFormId(dto.getParentId());
            field.setFieldName(dto.getFieldName());
            field.setFieldType(dto.getFieldType());
            field.setIsCondition(dto.getIsCondition() == null ? 0 : dto.getIsCondition());
            field.setIsEdit(dto.getIsEdit() == null ? 0 : dto.getIsEdit());
            field.setIsShow(dto.getIsShow() == null ? 0 : dto.getIsShow());
            field.setDescription(dto.getDescription());
            field.setFieldsAttr(dto.getFieldsAttr());
            field.setShowSort(dto.getShowSort());
            field.setConditionSort(dto.getConditionSort());
            field.setEditSort(dto.getEditSort());
            field.setIsRequired(dto.getIsRequired());
            field.setDefaultValue(dto.getDefaultValue());
            field.setIsDelete(0);
            field.setStatus(1);
            field.setUpdateTime(new Date());
            fieldService.updateById(field);
        }
    }




}
