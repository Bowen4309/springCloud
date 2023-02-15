package com.Bibo.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.Bibo.common.response.ApiResponse;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;

public class ApiResponseUtils {

    /**
     * 解析接口返回数据
     *
     * @param responseJsonStr
     * @return
     */
    public static ApiResponse analyseResponseData(String responseJsonStr) {
        JSONObject response = JSONObject.parseObject(responseJsonStr);
        if(null == response){
            return ApiResponse.builder().isSuccess(false).msg("平台转发请求异常，请联系系统管理员").build();
        }
        if (response.get("code").toString().equals("1")) {
            Map<String, Integer> map = (Map<String, Integer>) response.get("states");
            if (ObjectUtil.isEmpty(map)){
                if (ObjectUtils.isEmpty(response.get("count"))) {
                    return ApiResponse.builder()
                            .isSuccess(true)
                            .msg(response.get("msg").toString())
                            .data(response.get("data"))
                            .build();
                } else {
                    return ApiResponse.builder()
                            .isSuccess(true)
                            .msg(response.get("msg").toString())
                            .data(response.get("data"))
                            .count(Long.valueOf((Integer) response.get("count")))
                            .totalPages(Long.valueOf((Integer) response.get("totalPages")))
                            .build();
                }
            }
            return ApiResponse.builder()
                    .isSuccess(true)
                    .msg(response.get("msg").toString())
                    .data(response.get("data"))
                    .count(Long.valueOf(map.get("total")))
                    .totalPages(Long.valueOf(map.get("total")))
                    .build();
        }

        return ApiResponse.builder().isSuccess(false).msg(response.get("msg").toString()).build();
    }
}