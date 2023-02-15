package com.Bibo.common.constant;

public enum ApiTypeUnusualEnum {

    SYNCHRONOUS_UNUSUAL_DATA("SYNCHRONOUS_UNUSUAL_DATA","异常车辆数据同步");


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

    ApiTypeUnusualEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
