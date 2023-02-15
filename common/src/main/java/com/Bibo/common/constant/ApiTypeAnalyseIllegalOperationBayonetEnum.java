package com.Bibo.common.constant;

public enum ApiTypeAnalyseIllegalOperationBayonetEnum {

    ANALYSE_ILLEGAL_OPERATION_BAYONET_PAGE("ANALYSE_ILLEGAL_OPERATION_BAYONET_PAGE","车辆卡口信息列表"),
    ANALYSE_ILLEGAL_OPERATION_BAYONET_LIST("ANALYSE_ILLEGAL_OPERATION_BAYONET_LIST","车辆卡口信息列表"),
    ANALYSE_ILLEGAL_OPERATION_BAYONET_BATCH_ADD("ANALYSE_ILLEGAL_OPERATION_BAYONET_BATCH_ADD","车辆卡口信息列表"),
    ANALYSE_BAYONET_CAR_PASS_COUNT("ANALYSE_BAYONET_CAR_PASS_COUNT","卡口过车排名"),
    BAYONET_TOTAL_PAGE("BAYONET_TOTAL_PAGE","卡口统计过车排名分页"),
    BAYONET_TOTAL_MAP("BAYONET_TOTAL_MAP","地图卡口统计过车"),
    REMOVE_DUPLICATE_DATE("REMOVE_DUPLICATE_DATE","去除卡口重复数据");


    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseIllegalOperationBayonetEnum getDescription(String type){

        for(ApiTypeAnalyseIllegalOperationBayonetEnum apiTypeEnum: ApiTypeAnalyseIllegalOperationBayonetEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseIllegalOperationBayonetEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
