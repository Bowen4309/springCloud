package com.Bibo.common.constant;

public enum ApiTypeGridEnum {

    MAP_GRID("MAP_GRID","网格数据"),
    BUSSINESS_MAP_GRID("BUSSINESS_MAP_GRID","限行网格数据"),
    BUSSINESS_GRID_UPDATE("BUSSINESS_GRID_UPDATE","修改限行网格数据"),
    GRID_RANGE("GRID_RANGE","网格范围"),
    BUSSINESS_GRID_RANGE("BUSSINESS_GRID_RANGE","获取坐标点所在限行网格"),
    ADD_GRID_ASSIGN("ADD_GRID_ASSIGN","给经纬度赋值网格ID"),
    PUBLIC_RESOURCE_GRID("PUBLIC_RESOURCE_GRID","公共资源所在网格"),
    GRID_TEAM_LIST("GRID_TEAM_LIST","网格大队列表"),
    GRID_LIST("GRID_LIST","网格结构列表"),
    ALL_GRID_LIST("ALL_GRID_LIST","网格结构列表"),
    BUSSINESS_RESOURCE_GRID("BUSSINESS_RESOURCE_GRID","资源业务网格查询"),
    BUSSINESS_RESOURCE_GRID_BIND("BUSSINESS_RESOURCE_GRID_BIND","网格结构列表"),
    HIGH_ETC_ROAD("HIGH_ETC_ROAD","ETC高速路线"),
    UPDATE_GRID_DATA("UPDATE_GRID_DATA","修改网格数据"),
    INSERT_GRID_DATA("INSERT_GRID_DATA","新增网格数据"),
    ROAD_LIST("ROAD_LIST","车辆起始卡口"),
    ROAD_SYN("ROAD_SYN","车辆起始卡口数据同步"),
    ROAD_TRAIL("ROAD_TRAIL","车辆起始卡口轨迹"),
    ROAD_UPDATE("ROAD_UPDATE","车辆起始卡口数据更新"),
    TEST("TEST","消耗资源"),;

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeGridEnum getDescription(String type){

        for(ApiTypeGridEnum apiTypeEnum: ApiTypeGridEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeGridEnum(String apiType, String descirption){

        this.apiType =apiType;
        this.description = description;
    }
}
