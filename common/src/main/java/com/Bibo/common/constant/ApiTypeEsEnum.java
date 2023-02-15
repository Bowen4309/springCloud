package com.Bibo.common.constant;

public enum ApiTypeEsEnum {

    SYNC_DATA_TO_ES("SYNC_DATA_TO_ES","同步数据到ES"),
    SAVE_CAR_TAG("SAVE_CAR_TAG","保存车辆标签信息"),
    SEARCH_DATA_BY_TAG("SEARCH_DATA_BY_TAG","根据标签查询数据"),
    SEARCH_COMPANY_INFO("SEARCH_COMPANY_INFO","查询企业信息"),
    SEARCH_TAG_BY_KEY_WORDS("SEARCH_TAG_BY_KEY_WORDS","根据关键字查询标签");


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

    ApiTypeEsEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
