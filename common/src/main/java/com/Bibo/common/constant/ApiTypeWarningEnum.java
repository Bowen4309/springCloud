package com.Bibo.common.constant;

public enum ApiTypeWarningEnum {

    ALL_WARNING_DATA("ALL_WARNING_DATA","全量预警驾驶人信息"),
    ANALYSE_WARNING("ANALYSE_WARNING","同步天算数据到基础数据表");


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

    ApiTypeWarningEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
