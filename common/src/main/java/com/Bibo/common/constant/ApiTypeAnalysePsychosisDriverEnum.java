package com.Bibo.common.constant;

public enum ApiTypeAnalysePsychosisDriverEnum {

    ANALYSE_PSYCHOSIS_DRIVER_PAGE("ANALYSE_PSYCHOSIS_DRIVER_PAGE","精神病驾驶人分页列表"),
    ANALYSE_PSYCHOSIS_DRIVER_APPROVE_PAGE("ANALYSE_PSYCHOSIS_DRIVER_APPROVE_PAGE","精神病驾驶人审核分页列表"),
    ANALYSE_PSYCHOSIS_DRIVER_COUNT("ANALYSE_PSYCHOSIS_DRIVER_COUNT","指标统计"),
    ANALYSE_PSYCHOSIS_DRIVER_ID_DETAIL("ANALYSE_PSYCHOSIS_DRIVER_ID_DETAIL","根据id查询"),
    ANALYSE_PSYCHOSIS_DRIVER_APPROVE_RESULT("ANALYSE_PSYCHOSIS_DRIVER_APPROVE_RESULT","研判结果"),
    ;

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalysePsychosisDriverEnum getDescription(String type){
        for(ApiTypeAnalysePsychosisDriverEnum apiTypeEnum: ApiTypeAnalysePsychosisDriverEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalysePsychosisDriverEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
