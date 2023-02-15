package com.Bibo.common.constant;

public enum ApiTypeAnalyseCarGpsEnum {

    INSERT_OR_UPDATE("INSERT_OR_UPDATE","gps轨迹数据插入或者修改"),
    DELETE("DELETE","gps轨迹数据删除"),
    BATCH_INSERT("BATCH_INSERT","实时定位批量插入"),
    BATCH_UPDATE("BATCH_UPDATE","实时定位批量更新"),
    GPS_CAR_LIST("GPS_CAR_LIST","获取需要查询gps的车辆"),
    GPS_LOCAL_BY_CARNUMER("GPS_LOCAL_BY_CARNUMER","根据车牌号获取gps实时定位数据"),
    GPS_LOCAL_BY_COMPANY("GPS_LOCAL_BY_COMPANY","根据企业名称获取企业下车辆gps定位数据"),
    GPS_TRAIL_BY_CARNUMER("GPS_TRAIL_BY_CARNUMER","根据车牌号获取gps历史轨迹数据"),
    GPS_TRAIL_BY_COMPANY("GPS_TRAIL_BY_COMPANY","根据企业名称获取企业下车辆gps历史轨迹数据")
    ;

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseCarGpsEnum getDescription(String type){

        for(ApiTypeAnalyseCarGpsEnum apiTypeEnum: ApiTypeAnalyseCarGpsEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseCarGpsEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
