package com.Bibo.common.constant;

public enum ApiTypeBusinessIndexEnum {
    GET_INDEX_DATA("GET_INDEX_DATA","获取个人指标数据");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeBusinessIndexEnum getDescription(String type){

        for(ApiTypeBusinessIndexEnum apiTypeEnum: ApiTypeBusinessIndexEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeBusinessIndexEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
