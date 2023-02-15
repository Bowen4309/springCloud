package com.Bibo.common.constant;

public enum ApiTypeAnalyseIllegalOperationEnum {


    ANALYSE_ILLEGAL_OPERATION_PAGE("ANALYSE_ILLEGAL_OPERATION_PAGE","违法运营车辆分页"),
    ANALYSE_ILLEGAL_OPERATION_LIST("ANALYSE_ILLEGAL_OPERATION_LIST","违法运营车辆"),
    ANALYSE_ILLEGAL_OPERATION_APPROVE_INFO("ANALYSE_ILLEGAL_OPERATION_APPROVE_INFO","违法运营车辆审核详情页"),
    ANALYSE_ILLEGAL_OPERATION_APPROVE("ANALYSE_ILLEGAL_OPERATION_APPROVE_INFO","违法运营车辆审核"),
    ANALYSE_ILLEGAL_OPERATION_COUNT("ANALYSE_ILLEGAL_OPERATION_COUNT","违法运营车辆审核指标统计"),
    ANALYSE_ILLEGAL_OPERATION_BAYONET("ANALYSE_ILLEGAL_OPERATION_BAYONET","面包车卡口流水数据"),
    ANALYSE_ILLEGAL_OPERATION_COUNT_DETAIL("ANALYSE_ILLEGAL_OPERATION_COUNT_DETAIL","车辆指标统计詳情展示"),
    ANALYSE_ILLEGAL_OPERATION_COUNT_ACTIVE("ANALYSE_ILLEGAL_OPERATION_COUNT_ACTIVE","活跃面包车辆统计"),
    ANALYSE_ILLEGAL_OPERATION_COUNT_APPROVE("ANALYSE_ILLEGAL_OPERATION_COUNT_APPROVE","研判面包车辆审批"),
    ANALYSE_ILLEGAL_OPERATION_EXPORT("ANALYSE_ILLEGAL_OPERATION_EXPORT","研判面包车辆导出"),
    ANALYSE_ILLEGAL_OPERATION_SYNCHRONOUS("ANALYSE_ILLEGAL_OPERATION_SYNCHRONOUS","面包车辆同步"),


    ANALYSE_ILLEGAL_OPERATION_COUNT_NEW("ANALYSE_ILLEGAL_OPERATION_COUNT_NEW","非法营运统计概况"),
    ANALYSE_ILLEGAL_OPERATION_ACTIVE_RANK("ANALYSE_ILLEGAL_OPERATION_ACTIVE_RANK","非法营运活跃排名"),
    ANALYSE_ILLEGAL_OPERATION_APPROVE_RANK("ANALYSE_ILLEGAL_OPERATION_APPROVE_RANK","非法营运研判排名"),
    ANALYSE_ILLEGAL_OPERATION_PAGE_LIST("ANALYSE_ILLEGAL_OPERATION_PAGE_LIST","非法营运研判详情页")
    ;

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseIllegalOperationEnum getDescription(String type){
        for(ApiTypeAnalyseIllegalOperationEnum apiTypeEnum: ApiTypeAnalyseIllegalOperationEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseIllegalOperationEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
