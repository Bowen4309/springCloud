package com.Bibo.common.constant;

public enum ApiTypeAnalyseSmallModifiedCarEnum {

    SMALL_MODIFIED_LIST_PAGE("SMALL_MODIFIED_LIST_PAGE","分页查询小型改装车列表"),
    SMALL_MODIFIED_COUNT("SMALL_MODIFIED_COUNT","指标统计查询"),
    SMALL_MODIFIED_APPROVE_RANK("SMALL_MODIFIED_APPROVE_RANK","小型改装车部门审核车辆排名"),
    SMALL_MODIFIED_ACT_RANK("SMALL_MODIFIED_ACT_RANK","小型改装车部门活跃车辆排名"),
    SMALL_MODIFIED_APPROVE_LIST("SMALL_MODIFIED_APPROVE_LIST","小型改装车审核列表"),
    SMALL_MODIFIED_Illegal_LIST("SMALL_MODIFIED_Illegal_LIST","小型改装车研判列表"),
    SMALL_MODIFIED_DETAIL_LIST("SMALL_MODIFIED_DETAIL_LIST","小型改装车详情列表"),
    SMALL_MODIFIED_USE_WAY_LIST("SMALL_MODIFIED_USE_WAY_LIST","小型改装车使用性质列表"),
    SMALL_MODIFIED_SYNCHRONOUS_DATA("SMALL_MODIFIED_SYNCHRONOUS_DATA","小型改装车数据同步"),
    ANALYSE_CAR_INSTORAGE_RANK("ANALYSE_CAR_INSTORAGE_RANK","专题车辆入库排名"),
    ANALYSE_CAR_APPROVE_RANK("ANALYSE_CAR_APPROVE_RANK","专题车辆审核排名"),
    ANALYSE_CAR_ANALYSE_RANK("ANALYSE_CAR_ANALYSE_RANK","专题车辆研判排名"),
    SMALL_MODIFIED_UPDATE_RESULT("SMALL_MODIFIED_UPDATE_RESULT","修改小型改装车审核的状态");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseSmallModifiedCarEnum getDescription(String type){

        for(ApiTypeAnalyseSmallModifiedCarEnum apiTypeEnum: ApiTypeAnalyseSmallModifiedCarEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseSmallModifiedCarEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
