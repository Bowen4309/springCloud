package com.Bibo.common.constant;

public enum ApiTypeAnalyseDisqualifyDriverEnum {

    ANALYSE_DISQUALIFY_DRIVER_PAGE("ANALYSE_DISQUALIFY_DRIVER_PAGE","失格驾驶人分页列表"),
    ANALYSE_DISQUALIFY_DRIVER_APPROVE_PAGE("ANALYSE_DISQUALIFY_DRIVER_APPROVE_PAGE","失格驾驶人审核分页列表"),
    ANALYSE_DISQUALIFY_DRIVER_COUNT("ANALYSE_DISQUALIFY_DRIVER_COUNT","指标统计"),
    ANALYSE_DISQUALIFY_DRIVER_ID_DETAIL("ANALYSE_DISQUALIFY_DRIVER_ID_DETAIL","根据id查询"),
    ANALYSE_DISQUALIFY_DRIVER_DEPART_RANK("ANALYSE_DISQUALIFY_DRIVER_DEPART_RANK","部门排行"),
    ANALYSE_DISQUALIFY_DRIVER_LIST_IDCARD("ANALYSE_DISQUALIFY_DRIVER_LIST_IDCARD","根据idcard获取身份证"),
    ANALYSE_DRIVER_SYN("ANALYSE_DRIVER_SYN","驾驶人同步数据")
    ;

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseDisqualifyDriverEnum getDescription(String type){
        for(ApiTypeAnalyseDisqualifyDriverEnum apiTypeEnum: ApiTypeAnalyseDisqualifyDriverEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseDisqualifyDriverEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
