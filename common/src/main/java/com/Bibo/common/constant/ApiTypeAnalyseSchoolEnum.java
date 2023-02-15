package com.Bibo.common.constant;

public enum ApiTypeAnalyseSchoolEnum {

    SCHOOL_LIST_PAGE("SCHOOL_LIST_PAGE","分页查询校车列表"),
    SCHOOL_COUNT("SCHOOL_COUNT","指标统计查询"),
    SCHOOL_APPROVE_RANK("SCHOOL_APPROVE_RANK","校车部门审核排名"),
    SCHOOL_ACT_RANK("SCHOOL_ACT_RANK","校车部门活跃车辆排名"),
    SCHOOL_APPROVE_LIST("SCHOOL_APPROVE_LIST","校车审核列表"),
    SCHOOL_DETAIL_LIST("SCHOOL_DETAIL_LIST","校车详情列表"),
    SCHOOL_USE_WAY_LIST("SCHOOL_USE_WAY_LIST","校车使用性质列表"),
    SCHOOL_SYNCHRONOUS_DATA("SCHOOL_SYNCHRONOUS_DATA","校车数据同步"),
    ANALYSE_CAR_INSTORAGE_RANK("ANALYSE_CAR_INSTORAGE_RANK","专题车辆入库排名"),
    ANALYSE_CAR_APPROVE_RANK("ANALYSE_CAR_APPROVE_RANK","专题车辆审核排名"),
    ANALYSE_CAR_ANALYSE_RANK("ANALYSE_CAR_ANALYSE_RANK","专题车辆研判排名"),
    SCHOOL_Illegal_LIST("SCHOOL_Illegal_LIST","校车研判列表"),
    SCHOOL_UPDATE_RESULT("SCHOOL_UPDATE_RESULT","修改校车审核的状态");

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

    ApiTypeAnalyseSchoolEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
