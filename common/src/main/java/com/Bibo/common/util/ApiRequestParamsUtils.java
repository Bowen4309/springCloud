package com.Bibo.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestParamsUtils {

    private Object params;

    private String apiType;

    private String resourceId;

    public static ApiRequestParamsUtils insertParamsObject(String apiType, Object params){
        ApiRequestParamsUtils apiRequestParamsUtils = new ApiRequestParamsUtils();
        apiRequestParamsUtils.setParams(JSONObject.toJSONString(params));
        apiRequestParamsUtils.setApiType(apiType);
        return apiRequestParamsUtils;
    }

    public static ApiRequestParamsUtils insertParamsString(String apiType,String params){
        ApiRequestParamsUtils apiRequestParamsUtils = new ApiRequestParamsUtils();
        apiRequestParamsUtils.setApiType(apiType);
        apiRequestParamsUtils.setParams(params);
        return apiRequestParamsUtils;
    }

    public static ApiRequestParamsUtils insertParamsString(String apiType, Object params,String resourceId){
        ApiRequestParamsUtils apiRequestParamsUtils = new ApiRequestParamsUtils();
        apiRequestParamsUtils.setApiType(apiType);
        apiRequestParamsUtils.setParams(params);
        apiRequestParamsUtils.setResourceId(resourceId);
        return apiRequestParamsUtils;
    }

    public static ApiRequestParamsUtils insertParamsObject(String apiType, Object params,String resourceId){
        ApiRequestParamsUtils apiRequestParamsUtils = new ApiRequestParamsUtils();
        apiRequestParamsUtils.setParams(JSONObject.toJSONString(params));
        apiRequestParamsUtils.setApiType(apiType);
        apiRequestParamsUtils.setResourceId(resourceId);
        return apiRequestParamsUtils;
    }
}
