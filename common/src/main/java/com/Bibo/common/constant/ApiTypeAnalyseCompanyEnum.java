package com.Bibo.common.constant;

public enum ApiTypeAnalyseCompanyEnum {

    ANALYSE_CANCEL_COMPANY_PAGE("ANALYSE_CANCEL_COMPANY_PAGE","分页查询注销企业"),
    ANALYSE_COMPANY_VEHICLE_PAGE("ANALYSE_COMPANY_VEHICLE_PAGE","分页查询注销企业的车辆"),
    ANALYSE_VIOLATE_VEHICLE_PAGE("ANALYSE_VIOLATE_VEHICLE_PAGE","分页查询车辆违法信息"),
    ANALYSE_COMPANY_STATISTICAL_COUNT("ANALYSE_COMPANY_STATISTICAL_COUNT","统计概况"),
    ANALYSE_COMPANY_VEHICLE_RANK("ANALYSE_COMPANY_VEHICLE_RANK","注销企业运输车辆总数排名"),
    ANALYSE_VEHICLE_TYPE_ACCOUNT("ANALYSE_VEHICLE_TYPE_ACCOUNT","注销企业运输车辆类型占比"),
    ANALYSE_VEHICLE_QUALITY_ACCOUNT("ANALYSE_VEHICLE_QUALITY_ACCOUNT","车辆总质量分布情况"),
    ANALYSE_VIOLATE_ACCOUNT("ANALYSE_VIOLATE_ACCOUNT","任务办结情况"),
    ANALYSE_VEHICLE_STATUS("ANALYSE_VEHICLE_STATUS","车辆所拥有车辆状态"),
    SYNCHRONOUS_DATA_COMPANY_VEHICLE("SYNCHRONOUS_DATA_COMPANY_VEHICLE","注销企业数据表同步");

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

    ApiTypeAnalyseCompanyEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
