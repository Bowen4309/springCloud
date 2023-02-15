package com.Bibo.common.constant;

public enum ApiTypeAnalyseDeathDriverEnum {

    DEATH_DRIVER_LIST("DEATH_DRIVER_LIST","分页查询死亡驾驶人列表"),
    DEATH_DRIVER_ID("DEATH_DRIVER_ID","分页查询死亡驾驶人列表"),
    DEATH_DRIVER_APPROVE("DEATH_DRIVER_APPROVE","分页查询死亡驾驶人列表"),
    DEATH_DRIVER_ILLEGAL("DEATH_DRIVER_ILLEGAL","死亡驾驶人数据确认"),
    DEATH_DRIVER_COUNT("DEATH_DRIVER_COUNT","死亡驾驶人数据统计"),
    DEATH_DRIVER_SYNCHRONOUS("DEATH_DRIVER_SYNCHRONOUS","死亡驾驶人数据同步")
    ;


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

    ApiTypeAnalyseDeathDriverEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
