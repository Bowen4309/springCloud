package com.Bibo.common.constant;

public enum ApiTypeBusinessEnum {

    RESIDENCE_PERMIT_SERCH("RESIDENCE_PERMIT_SERCH","居住证查询");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeBusinessEnum getDescription(String type){

        for(ApiTypeBusinessEnum apiTypeEnum: ApiTypeBusinessEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeBusinessEnum(String apiType, String descirption){

        this.apiType =apiType;
        this.description = description;
    }
}
