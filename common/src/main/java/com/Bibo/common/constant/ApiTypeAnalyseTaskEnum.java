package com.Bibo.common.constant;

/**
 * 专题分析审批清单
 */
public enum ApiTypeAnalyseTaskEnum {

    ANALYSE_TASK_PAGE("ANALYSE_TASK_PAGE","分页查询任务单"),
    ANALYSE_TASK_SETTLE("ANALYSE_TASK_SETTLE","办结任务单"),
    ANALYSE_COMPANY_VEHICLE_TASK_SEND("ANALYSE_COMPANY_VEHICLE_TASK_SEND","发起注销企业车辆任务单"),
    ANALYSE_TASK_SETTLE_ACCOUNT("ANALYSE_TASK_SETTLE_ACCOUNT","任务单的办结分布情况"),
    ANALYSE_TASK_CARTYPE_CREATE("ANALYSE_TASK_CARTYPE_CREATE","任务单的专题生成"),
    ANALYSE_TASK_STATUS_UPDATE("ANALYSE_TASK_STATUS_UPDATE","任务单状态修改"),
    ANALYSE_TASK_DETAIL_PAGE("ANALYSE_TASK_DETAIL_PAGE","分页查询任务单详情"),
    ANALYSE_TASK_PROCEDURE_LIST("ANALYSE_TASK_PROCEDURE_LIST","任务单操作记录"),
    ANALYSE_TASK_DETAIL_DELETE("ANALYSE_TASK_DETAIL_DELETE","任务单子任务删除"),
    ANALYSE_TASK_RESOURCE_INFO("ANALYSE_TASK_RESOURCE_INFO","任务单资源信息");

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

    ApiTypeAnalyseTaskEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
