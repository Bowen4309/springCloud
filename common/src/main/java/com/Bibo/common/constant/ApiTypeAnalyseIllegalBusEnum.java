package com.Bibo.common.constant;

public enum ApiTypeAnalyseIllegalBusEnum {

    BUS_LIST("BUS_LIST","分页查询大客车车辆列表"),
    ILLEGAL_BUS_LIST("ILLEGAL_BUS_LIST","分页查询非法营运客车列表"),
    BUS_UPDATE_RESULT("BUS_UPDATE_RESULT","修改审核状态"),
    SYNCHRONOUS_BUS_DATA("SYNCHRONOUS_BUS_DATA","同步大客车车辆数据信息"),
    BUS_CAR_BAYONET_DATA("BUS_CAR_BAYONET_DATA","大客车卡口流水数据"),
    BUS_CAR_SUM_DATA("BUS_CAR_SUM_DATA","大客车统计数据"),
    ACCOUNT_BUS_MESSAGE_DATA("ACCOUNT_BUS_MESSAGE_DATA","分页获取客车分析详情"),
    BUS_ACT_RANK_DATA("BUS_ACT_RANK_DATA","客车活跃车辆排名数据"),
    APPROVE_BUS_SETTLE_ACCOUNT("APPROVE_BUS_SETTLE_ACCOUNT","客车大队审核研判车辆状况"),
    BUS_STATUS_ACCOUNT("BUS_STATUS_ACCOUNT","获取客车车辆状态"),
    BUS_USE_WAY_ACCOUNT("BUS_USE_WAY_ACCOUNT","获取客车车辆使用性质"),
    BUS_ILLEGAL_RANK_DATA("BUS_ILLEGAL_RANK_DATA","客车非营运研判排名数据"),
    BUS_CAR_SYNCHRONOUS("BUS_CAR_SYNCHRONOUS","大客车数据同步"),
    BUS_CARNUMBER_LIST("BUS_CARNUMBER_LIST","大客车车牌号集合")
    ;


    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseIllegalBusEnum getDescription(String type){

        for(ApiTypeAnalyseIllegalBusEnum apiTypeEnum: ApiTypeAnalyseIllegalBusEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseIllegalBusEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
