package com.Bibo.common.constant;

public enum ApiTypeAnalyseDangerousEnum {

    DANGEROUS_LIST("DANGEROUS_LIST","分页查询危化品车辆列表"),
    DANGEROUS_UPDATE_RESULT("DANGEROUS_UPDATE_RESULT","修改审核状态"),
    DANGEROUS_TOTAL("DANGEROUS_TOTAL","危化品车辆统计"),
    SYNCHRONOUS_DANGEROUS_DATA("SYNCHRONOUS_DANGEROUS_DATA","同步危化品车辆数据信息"),
    DANGEROUS_DEPART_TOTAL("DANGEROUS_DEPART_TOTAL","危化品车辆各大队统计情况"),
    DANGEROUS_DEPART_TOTAL_DETAIL("DANGEROUS_DEPART_TOTAL_DETAIL","危化品车辆各大队统计情况详细列表"),
    DANGEROUS_CHECK_TOTAL("DANGEROUS_CHECK_TOTAL","危化品车辆查处统计分析"),
    DANGEROUS_CHECK_DETAIL("DANGEROUS_CHECK_DETAIL","危化品车辆查处导出详情"),
    DANGEROUS_DEPART_APPROVE_TOTAL("DANGEROUS_DEPART_APPROVE_TOTAL","危化品车辆各大队审核统计情况详细列表"),
    DANGEROUS_CAR_BAYONET_DATA("DANGEROUS_CAR_BAYONET_DATA","危化品卡口流水数据"),
    DANGEROUS_TRESPASS_AREA_PAGE_LIST("DANGEROUS_TRESPASS_AREA_PAGE_LIST","危化品限行区域分页查询"),
    DANGEROUS_TRESPASS_TIME_PAGE_LIST("DANGEROUS_TRESPASS_TIME_PAGE_LIST","危化品限行时间分页查询");

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

    ApiTypeAnalyseDangerousEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
