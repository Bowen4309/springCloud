package com.Bibo.common.constant;

public enum ApiTypeTagDetailEnum {
    TAG_BAYONET_RULE("TAG_BAYONET_RULE","车辆出行规律卡口详情");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeTagDetailEnum getDescription(String type){

        for(ApiTypeTagDetailEnum apiTypeEnum: ApiTypeTagDetailEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeTagDetailEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
