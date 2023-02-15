package com.Bibo.common.constant;

public enum ApiTypeAnalyseDrugDriverEnum {

    ANALYSE_DRUG_DRIVER_PAGE("ANALYSE_DRUG_DRIVER_PAGE","吸毒驾驶人分页列表"),
    ANALYSE_DRUG_DRIVER_APPROVE_PAGE("ANALYSE_DRUG_DRIVER_APPROVE_PAGE","吸毒驾驶人审批分页列表"),
    ANALYSE_DRUG_DRIVER_COUNT("ANALYSE_DRUG_DRIVER_COUNT","指标统计"),
    ANALYSE_DRUG_DRIVER_ID_DETAIL("ANALYSE_DRUG_DRIVER_ID_DETAIL","根据id查询"),
    ANALYSE_DRUG_DRIVER_APPROVE_RESULT("ANALYSE_DRUG_DRIVER_APPROVE_RESULT","研判结果"),
    ANALYSE_DRUG_DRIVER_CONTROL_STATUS_LIST("ANALYSE_DRUG_DRIVER_CONTROL_STATUS_LIST","管控状态集合")
    ;

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseDrugDriverEnum getDescription(String type){
        for(ApiTypeAnalyseDrugDriverEnum apiTypeEnum: ApiTypeAnalyseDrugDriverEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseDrugDriverEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
