package com.Bibo.common.constant;

public enum ApiTypeAnalyseLicencePlateEnum {

    ANALYSE_LICENCE_PLATE_APPROVE_PAGE("ANALYSE_LICENCE_PLATE_APPROVE_PAGE","审核分页列表"),
    ANALYSE_LICENCE_PLATE_ID("ANALYSE_LICENCE_PLATE_ID","主键查询"),
    ANALYSE_LICENCE_SYNCHRONIZE("ANALYSE_ILLEGAL_OPERATION_APPROVE_INFO","同步数据"),
    ANALYSE_ILLEGAL_OPERATION_COUNT("ANALYSE_ILLEGAL_OPERATION_COUNT","违法运营车辆指标统计")

    ;


    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseLicencePlateEnum getDescription(String type){

        for(ApiTypeAnalyseLicencePlateEnum apiTypeEnum: ApiTypeAnalyseLicencePlateEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseLicencePlateEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
