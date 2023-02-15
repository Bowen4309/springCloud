package com.Bibo.common.constant;

public enum ApiTypePublicJobEnum {

    CHECK_CAR_APPROVE_TIME("CHECK_CAR_APPROVE_TIME","更新需要重审数据"),
    SYNCHRONOUS_FTP_CAR_DATA("SYNCHRONOUS_FTP_CAR_DATA","获取车牌库同步到FTP数据");

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

    ApiTypePublicJobEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
