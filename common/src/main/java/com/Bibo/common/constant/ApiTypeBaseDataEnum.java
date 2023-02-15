package com.Bibo.common.constant;

public enum ApiTypeBaseDataEnum {
    BASE_CAR_INFOMATION("BASE_CAR_INFOMATION","车辆基本信息"),
    BASE_DRIVER_INFOMATION("BASE_DRIVER_INFOMATION","驾驶人基本信息"),
    BASE_CAR_BAYONET_TRAIL("BASE_CAR_BAYONET_TRAIL","车辆卡口轨迹信息"),
    BASE_COMPANY_CAR_INFO("BASE_COMPANY_CAR_INFO","企业车辆信息"),
    BASE_SYN_COMPANY_INFO("BASE_SYN_COMPANY_INFO","同步企业信息"),
    BASE_CAR_CHECK_DETAIL("BASE_CAR_CHECK_DETAIL","查询车辆检查详情数据"),
    BASE_ILLEGAL_DETAIL("BASE_ILLEGAL_DETAIL","查询车辆查处详情数据"),
    BASE_ILLEGAL_TRANSIT_DETAIL("BASE_ILLEGAL_TRANSIT_DETAIL","查询车辆违法时间通行行驶详情数据"),
    BASE_FIND_SAVE_ILLEGAL_TRANSIT("BASE_FIND_SAVE_ILLEGAL_TRANSIT","查询保存限行区域数据"),
    BASE_FIND_SAVE_STOP("BASE_FIND_SAVE_STOP","查询保存违法通行时间行驶数据"),
    BASE_STOP_DETAIL("BASE_STOP_DETAIL","查询车辆闯限行详情数据"),
    BASE_SAVE_ILLEGAL_TRANSIT("BASE_SAVE_ILLEGAL_TRANSIT","保存限行区域或者违法通行时间行驶数据"),
    BASE_DELETE_ILLEGAL_TRANSIT("BASE_DELETE_ILLEGAL_TRANSIT","删除限行区域或者违法通行时间行驶数据"),
    BASE_FACILITY_INFOMATION("BASE_FACILITY_INFOMATION","交通设施基本信息");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeBaseDataEnum getDescription(String type){

        for(ApiTypeBaseDataEnum apiTypeEnum: ApiTypeBaseDataEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeBaseDataEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
