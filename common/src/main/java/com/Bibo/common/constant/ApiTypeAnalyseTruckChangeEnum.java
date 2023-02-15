package com.Bibo.common.constant;

public enum ApiTypeAnalyseTruckChangeEnum {

    TRUCK_CHANGE_LIST("TRUCK_CHANGE_LIST","分页查询货车泥头车辆列表"),
    TRUCK_CHANGE_TOTAL("TRUCK_CHANGE_TOTAL","泥头车分析统计"),
    TRUCK_CHANGE_TOTAL_DETAIL_PAGE("TRUCK_CHANGE_TOTAL_DETAIL_PAGE","泥头车分析统计"),
    DEPART_ACTIVE_TRUCK_CHANGE_TOTAL("DEPART_ACTIVE_TRUCK_CHANGE_TOTAL","各大队活跃车辆情况"),
    DEPART_APPROVE_TRUCK_CHANGE_TOTAL("DEPART_APPROVE_TRUCK_CHANGE_TOTAL","各大队活跃车辆情况"),
    SYNCHRONOUS_TRUCK_CHANGE_DATA("SYNCHRONOUS_TRUCK_CHANGE_DATA","同步泥头车车辆信息"),
    CAR_TYPE_GROUP("CAR_TYPE_GROUP","查询车辆类型"),
    TRUCK_CHANGE_UPDATE_RESULT("TRUCK_CHANGE_UPDATE_RESULT","修改泥头车审核的状态");

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

    ApiTypeAnalyseTruckChangeEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
