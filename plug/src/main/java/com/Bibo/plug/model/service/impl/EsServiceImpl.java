package com.Bibo.plug.model.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.Bibo.common.constant.ApiTypeAnalysePsychosisDriverEnum;
import com.Bibo.common.constant.ApiTypeBaseDataEnum;
import com.Bibo.common.constant.ApiTypeEsEnum;
import com.Bibo.common.constant.EsIndexEnum;
import com.Bibo.common.response.ApiResponse;
import com.Bibo.common.response.Response;
import com.Bibo.common.util.ApiRequestParamsUtils;
import com.Bibo.common.util.DateUtils;
import com.Bibo.common.util.HttpRequestUtil;
import com.Bibo.common.util.RequestParamsUtil;
import com.Bibo.plug.model.dao.*;
import com.Bibo.plug.model.entity.EsCarTag;
import com.Bibo.plug.model.entity.TagBaseCarInfomationDTO;
import com.Bibo.plug.model.entity.TagBaseDriverInfomationDTO;
import com.Bibo.plug.model.mapper.EsTagMapper;
import com.Bibo.plug.model.service.EsService;
import com.Bibo.plug.model.service.FileDownService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private EsTagMapper esTagMapper;

    @Autowired
    private FileDownService fileDownService;


    @Override
    public Response saveTag(EsCarTag esCarTag) {
        try {
        ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                ApiRequestParamsUtils.insertParamsObject(ApiTypeEsEnum.SAVE_CAR_TAG.getApiType(),esCarTag));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response findDataByTag(EsSearchDataByTagsDTO esSearchDataByTagsDTO) {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsObject(ApiTypeEsEnum.SEARCH_DATA_BY_TAG.getApiType(), esSearchDataByTagsDTO));
            if(apiResponse.isSuccess()){
                JSONArray jsonArray = JSONObject.parseArray(apiResponse.getData().toString());

                List<Map<String,Object>> data = new ArrayList<>();
                jsonArray.forEach(hit ->{
                    //JSONObject object = JSONObject.parseObject(hit.toString());//
                    //CarIndexResponseDTO sourceAsMap = JSONObject.parseObject(object.get("sourceAsMap").toString(),CarIndexResponseDTO.class);

                    Map<String,Object> map = new HashMap<>();
                    JSONObject object = JSONObject.parseObject(hit.toString());//
                    JSONObject sourceAsMap = JSONObject.parseObject(object.get("sourceAsMap").toString());
                    String tags = sourceAsMap.getString("tag");
                    List<Map<String,Object>> tagsType = getTagsType(tags);
                    map.put("tagsType",tagsType);
                    if(EsIndexEnum.OBJECT_CAR_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){
                        map.put("carNumber",sourceAsMap.getString("carNumber"));
                        map.put("carNumberTypeCode",sourceAsMap.getString("carNumberTypeCode"));
                        map.put("carNumberType",sourceAsMap.getString("carNumberType"));
                        map.put("carNumberColor",sourceAsMap.getString("carNumberColor"));
                        map.put("tag",sourceAsMap.getString("tag"));
                        map.put("id",sourceAsMap.getString("id"));
                    }else if(EsIndexEnum.OBJECT_DRIVER_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){
                        map.put("idCard",sourceAsMap.getString("idCard"));
                        map.put("driverName",sourceAsMap.getString("driverName"));
                    }else if(EsIndexEnum.OBJECT_FACILITY_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){
                        tagsType = getFacilityTagsType(tags);
                        map.put("tagsType",tagsType);
                        map.put("id",sourceAsMap.getString("id"));
                        map.put("resourceId",sourceAsMap.getString("resourceId"));
                        map.put("facilityId",sourceAsMap.getString("facilityId"));
                        map.put("tag",sourceAsMap.getString("tag"));
                        map.put("name",sourceAsMap.getString("name"));
                        map.put("latitude",sourceAsMap.getString("latitude"));
                        map.put("longitude",sourceAsMap.getString("longitude"));
                        //map.put("sysTag",sourceAsMap.getString("sysTag"));
                        map.put("tagName",sourceAsMap.getString("handworkTag"));
                        map.put("tagId",sourceAsMap.getString("tagId"));
                        map.put("resourceName",sourceAsMap.getString("resourceName"));
                        map.put("deptId",sourceAsMap.getString("deptId"));
                        if(ObjectUtils.isNotEmpty(map.get("deptId"))){
                            map.put("deptName",esTagMapper.getDeptNameByDeptId(map.get("deptId").toString()));
                        }


                    }else if(EsIndexEnum.OBJECT_POLICE_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){
                        map.put("policeCode",sourceAsMap.getString("policeCode"));
                        map.put("name",sourceAsMap.getString("name"));
                        map = getPoliceInfoByPoliceCode(map);
                    }else if(EsIndexEnum.OBJECT_COMPANY_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){
                        map.put("name",sourceAsMap.getString("name"));
                        map.put("code",sourceAsMap.getString("code"));
                        map.put("address",sourceAsMap.getString("address"));
                        map.put("phone",sourceAsMap.getString("phone"));
                        map.put("placeDept",sourceAsMap.getString("placeDept"));
                        map.put("activeDept",sourceAsMap.getString("activeDept"));
                        map.put("carNum",sourceAsMap.getString("carNum"));
                    }
                    data.add(map);
                });
                return Response.success().data(data,apiResponse.getCount(),apiResponse.getTotalPages());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }


    Map<String,Object> getPoliceInfoByPoliceCode(Map<String,Object> map){
        Object policeCode = map.get("policeCode");
        if(ObjectUtils.isEmpty(policeCode)){
            return map;
        }
        String s = policeCode.toString();
        Map<String, String> info = esTagMapper.getPoliceInfoByPoliceCode(s);
        map.put("deptName",info.get("dept_name"));
        map.put("sex",info.get("sex"));
        map.put("gridId",info.get("grid_id"));
        map.put("gridName",info.get("grid_name"));
        map.put("name",info.get("name"));
        map.put("policeCode",info.get("police_code"));
        return map;
    }

    @Override
    public Response findTagByKeyWrods(EsSearchTagByKeyWordsDTO esSearchTagByKeyWordsDTO) {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsObject(ApiTypeEsEnum.SEARCH_TAG_BY_KEY_WORDS.getApiType(), esSearchTagByKeyWordsDTO));
            if(apiResponse.isSuccess() && ObjectUtils.isNotEmpty(apiResponse.getData())){
                JSONArray jsonArray = JSONObject.parseArray(apiResponse.getData().toString());
                List<Map<String,Object>> data = new ArrayList<>();
                jsonArray.forEach(hit ->{
                    Map<String,Object> map = new HashMap<>();
                    JSONObject object = JSONObject.parseObject(hit.toString());//
                    JSONObject sourceAsMap = JSONObject.parseObject(object.get("sourceAsMap").toString());
                    String tags = sourceAsMap.getString("tag");
                    List<Map<String,Object>> tagsType = getTagsType(tags);
                    map.put("tagsType",tagsType);
                    if(EsIndexEnum.OBJECT_CAR_NAME.getObjectName().equals(esSearchTagByKeyWordsDTO.getObjectName())){
                        map.put("carNumber",sourceAsMap.getString("carNumber"));
                        map.put("carNumberTypeCode",sourceAsMap.getString("carNumberTypeCode"));
                        map.put("carNumberType",sourceAsMap.getString("carNumberType"));
                        map.put("carNumberColor",sourceAsMap.getString("carNumberColor"));
                    }else if(EsIndexEnum.OBJECT_DRIVER_NAME.getObjectName().equals(esSearchTagByKeyWordsDTO.getObjectName())){
                        map.put("idCard",sourceAsMap.getString("idCard"));
                        map.put("driverName",sourceAsMap.getString("driverName"));
                    }else if(EsIndexEnum.OBJECT_FACILITY_NAME.getObjectName().equals(esSearchTagByKeyWordsDTO.getObjectName())){
                        tagsType = getFacilityTagsType(tags);
                        map.put("tagsType",tagsType);
                        map.put("id",sourceAsMap.getString("id"));
                        map.put("resourceId",sourceAsMap.getString("resourceId"));
                        map.put("facilityId",sourceAsMap.getString("facilityId"));
                        map.put("tag",sourceAsMap.getString("tag"));
                        map.put("name",sourceAsMap.getString("name"));
                        map.put("latitude",sourceAsMap.getString("latitude"));
                        map.put("longitude",sourceAsMap.getString("longitude"));
                        //map.put("sysTag",sourceAsMap.getString("sysTag"));
                        map.put("tagName",sourceAsMap.getString("handworkTag"));
                        map.put("tagId",sourceAsMap.getString("tagId"));
                        map.put("resourceName",sourceAsMap.getString("resourceName"));
                        map.put("deptId",sourceAsMap.getString("deptId"));
                        if(ObjectUtils.isNotEmpty(map.get("deptId"))){
                            map.put("deptName",esTagMapper.getDeptNameByDeptId(map.get("deptId").toString()));
                        }
                    }else if(EsIndexEnum.OBJECT_POLICE_NAME.getObjectName().equals(esSearchTagByKeyWordsDTO.getObjectName())){
                        map.put("policeCode",sourceAsMap.getString("policeCode"));
                        map.put("name",sourceAsMap.getString("name"));
                        map = getPoliceInfoByPoliceCode(map);
                    }else if(EsIndexEnum.OBJECT_COMPANY_NAME.getObjectName().equals(esSearchTagByKeyWordsDTO.getObjectName())){
                        map.put("name",sourceAsMap.getString("name"));
                        map.put("code",sourceAsMap.getString("code"));
                        map.put("address",sourceAsMap.getString("address"));
                        map.put("phone",sourceAsMap.getString("phone"));
                        map.put("placeDept",sourceAsMap.getString("placeDept"));
                        map.put("activeDept",sourceAsMap.getString("activeDept"));
                        map.put("carNum",sourceAsMap.getString("carNum"));
                    }
                    data.add(map);
                });
                return Response.success().data(data,apiResponse.getCount(),apiResponse.getTotalPages());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response syncDataToEs() {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeEsEnum.SYNC_DATA_TO_ES.getApiType(), ""));
            if(apiResponse.isSuccess()){
                return Response.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error();
    }

    @Override
    public Response findPoliceInfo(String policeCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("policeCode",policeCode);
        return Response.success().data(getPoliceInfoByPoliceCode(map));
    }

    @Override
    public Response findCompanyInfo(String code, String name) {
        //解析接口返回数据
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeEsEnum.SEARCH_COMPANY_INFO.getApiType(), code + ";" + name));
            if(apiResponse.isSuccess()){
                CompanyBaseInfoDTO companyBaseInfoDTO = JSONObject.parseObject(apiResponse.getData().toString(), CompanyBaseInfoDTO.class);
                return Response.success().data(companyBaseInfoDTO);
            }else {
                return Response.error(apiResponse.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("天河接口错误");
    }

    @Override
    @Async("defaultThreadPoolExecutor")
    public Response exportDataByTag(EsSearchDataByTagsDTO esSearchDataByTagsDTO, HttpServletResponse response, SysFileDown sysFileDown) {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(), RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsObject(ApiTypeEsEnum.SEARCH_DATA_BY_TAG.getApiType(), esSearchDataByTagsDTO));
            if(apiResponse.isSuccess() && ObjectUtils.isNotEmpty(apiResponse.getData())){

                JSONArray jsonArray = JSONObject.parseArray(apiResponse.getData().toString());
                List<Object> data = new ArrayList<>();
                for (Object o : jsonArray) {
                    JSONObject object = JSONObject.parseObject(o.toString());
                    JSONObject sourceAsMap = JSONObject.parseObject(object.get("sourceAsMap").toString());
                    if(EsIndexEnum.OBJECT_FACILITY_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){

                        String resourceId = sourceAsMap.getString("resourceId");
                        String facilityId = sourceAsMap.getString("facilityId");
                        FacilityInfoDTO facility = baseFacilityInfo(facilityId, resourceId);

                        FacilityExportDTO dto = new FacilityExportDTO();
                        dto.setAddress(facility.getAddress());
                        dto.setDeptId(sourceAsMap.getString("deptId"));
                        dto.setFacilityId(sourceAsMap.getString("facilityId"));
                        dto.setTag(sourceAsMap.getString("tag"));
                        dto.setGridId(facility.getGridId());
                        dto.setDeptName(facility.getDeptName());
                        dto.setGridName(facility.getGridName());
                        dto.setLatitude(facility.getLatitude());
                        dto.setLongitude(facility.getLongitude());
                        dto.setName(sourceAsMap.getString("name"));

                        data.add(dto);
                    }else if(EsIndexEnum.OBJECT_POLICE_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){


                        String policeCode = sourceAsMap.getString("policeCode");
                        Map<String, String> policeInfo = esTagMapper.getPoliceInfoByPoliceCode(policeCode);

                        PoliceExportDTO dto = new PoliceExportDTO();
                        dto.setSex(policeInfo.get("sex"));
                        dto.setPoliceCode(policeCode);
                        dto.setTag(sourceAsMap.getString("tag"));
                        dto.setDeptName(policeInfo.get("dept_name"));
                        dto.setGridId(policeInfo.get("grid_id"));
                        dto.setGridName(policeInfo.get("grid_name"));
                        dto.setName(sourceAsMap.getString("name"));

                        data.add(dto);
                    }else if(EsIndexEnum.OBJECT_COMPANY_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){

                        CompanyExportDTO dto = new CompanyExportDTO();
                        dto.setActiveDept(sourceAsMap.getString("activeDept"));
                        dto.setCarNum(sourceAsMap.getString("carNum"));
                        dto.setCode(sourceAsMap.getString("code"));
                        dto.setPhone(sourceAsMap.getString("phone"));
                        dto.setPlaceDept(sourceAsMap.getString("placeDept"));
                        dto.setTag(sourceAsMap.getString("tag"));
                        dto.setAddress(sourceAsMap.getString("address"));
                        dto.setName(sourceAsMap.getString("name"));

                        data.add(dto);
                    }else if(EsIndexEnum.OBJECT_CAR_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){
                        TagBaseCarInfomationDTO tagBaseCarInfomationDTO =baseCarInfomation(sourceAsMap.getString("carNumber"),sourceAsMap.getString("carNumberTypeCode"));
                        tagBaseCarInfomationDTO.setTags(sourceAsMap.getString("tag"));
                        data.add(tagBaseCarInfomationDTO);
                    }else if(EsIndexEnum.OBJECT_DRIVER_NAME.getObjectName().equals(esSearchDataByTagsDTO.getObjectName())){
                        TagBaseDriverInfomationDTO tagBaseDriverInfomationDTO =baseDriverInfomation(sourceAsMap.getString("idCard"));
                        tagBaseDriverInfomationDTO.setTags(sourceAsMap.getString("tag"));
                        data.add(tagBaseDriverInfomationDTO);
                    }
                }
                fileDownService.uploadDataFile(data,response,sysFileDown);
                return Response.success();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return Response.error();

    }


    public List<Map<String,Object>> getTagsType(String tags){
        List<String> tagList = Arrays.asList(tags.split(";"));
        return esTagMapper.getTagsType(tagList);
    }


    public List<Map<String,Object>> getFacilityTagsType(String tags){
        List<String> tagList = Arrays.asList(tags.split(";"));
        for (String s : tagList) {
            String tagTypeName = "";
            switch (s){
                case "卡口":
                    tagTypeName = "卡口";
                    break;
                case "ETC":
                    tagTypeName = "ETC";
                    break;
                case "电子警察":
                    tagTypeName = "电子警察";
                    break;
                case "信号灯":
                    tagTypeName = "信号灯";
                    break;
            }
            if(StringUtils.isNotBlank(tagTypeName)){
                return esTagMapper.getFacilityTagsType(tagList,tagTypeName);
            }

        }
        return esTagMapper.getTagsType(tagList);
    }


    private FacilityInfoDTO baseFacilityInfo(String facilityId, String resourceId) {
        String facilityRequest = facilityId+";"+resourceId;
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeBaseDataEnum.BASE_FACILITY_INFOMATION.getApiType(), facilityRequest));
            if(apiResponse.isSuccess()){
                FacilityInfoDTO facilityBaseInfoDTO= JSONObject.parseObject(apiResponse.getData().toString(), FacilityInfoDTO.class);
                if(org.apache.commons.lang.StringUtils.isNotBlank(facilityBaseInfoDTO.getDeptId())){
                    facilityBaseInfoDTO.setDeptName(esTagMapper.getDeptNameByDeptId(facilityBaseInfoDTO.getDeptId()));
                }
                return facilityBaseInfoDTO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FacilityInfoDTO();
    }

    public TagBaseCarInfomationDTO baseCarInfomation(String carNumber, String carNumberTypeCode) {
        String carRequest = carNumber+";"+carNumberTypeCode;
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeBaseDataEnum.BASE_CAR_INFOMATION.getApiType(), carRequest));
            if(apiResponse.isSuccess()){
                TagBaseCarInfomationDTO baseCarInfomationDTOS= JSONObject.parseObject(apiResponse.getData().toString(), TagBaseCarInfomationDTO.class);
                return baseCarInfomationDTOS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TagBaseDriverInfomationDTO baseDriverInfomation(String idCar) {
        try {
            ApiResponse apiResponse = HttpRequestUtil.getByDataArea(
                    RequestParamsUtil.getUrlApi(),RequestParamsUtil.getUrlToken(),
                    ApiRequestParamsUtils.insertParamsString(ApiTypeBaseDataEnum.BASE_DRIVER_INFOMATION.getApiType(), idCar));
            if(apiResponse.isSuccess()){
                TagBaseDriverInfomationDTO driverInfomationDTO= JSONObject.parseObject(apiResponse.getData().toString(),TagBaseDriverInfomationDTO.class);
                return driverInfomationDTO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
