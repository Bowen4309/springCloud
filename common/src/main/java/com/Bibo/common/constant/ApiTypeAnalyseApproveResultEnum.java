package com.Bibo.common.constant;

/**
 * 专题分析审批结果
 */
public enum ApiTypeAnalyseApproveResultEnum {

    APPROVE_RESULT_ADD("APPROVE_RESULT_ADD","新增审批结果");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseCompanyEnum getDescription(String type){

        for(ApiTypeAnalyseCompanyEnum apiTypeEnum: ApiTypeAnalyseCompanyEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseApproveResultEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
