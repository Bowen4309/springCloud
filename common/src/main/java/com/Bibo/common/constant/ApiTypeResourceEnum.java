package com.Bibo.common.constant;

public enum ApiTypeResourceEnum {

    RESOURCE_TITLE("RESOURCE_TITLE","资源目录"),
    RESOURCE_LIST("RESOURCE_LIST","获取资源地图列表数据"),
    RESOURCE_MAP_PAGE("RESOURCE_MAP_PAGE","分页获取资源数据列表数据"),
    RESOURCE_MAP_COUNT("RESOURCE_MAP_COUNT","分页获取资源数据列表数据"),
    RESOURCE_MAP_INFO("RESOURCE_MAP_INFO","获取资源信息数据"),
    RESOURCE_MAP_EDIT("RESOURCE_MAP_EDIT","编辑资源数据"),
    RESOURCE_MAP_ADD("RESOURCE_MAP_ADD","新增资源数据"),
    RESOURCE_MAP_DELETE("RESOURCE_MAP_DELETE","删除资源数据"),
    RESOURCE_MAP_TAG("RESOURCE_MAP_TAG","获取资源标签数据"),
    RESOURCE_MAP_BAYONET_PAGE("RESOURCE_MAP_BAYONET_PAGE","分页获取卡口列表数据"),
    RESOURCE_MAP_BAYONET_EDIT("RESOURCE_MAP_BAYONET_EDIT","编辑卡口数据"),
    RESOURCE_MAP_BAYONET_ADD("RESOURCE_MAP_BAYONET_ADD","编辑卡口数据"),
    RESOURCE_MAP_BAYONET_DELETE("RESOURCE_MAP_BAYONET_DELETE","删除卡口数据"),
    RESOURCE_DICT_LIST("RESOURCE_DICT_LIST","数据字典查询"),
    RESOURCE_BAYONET_LIST("RESOURCE_BAYONET_LIST","获取卡口资源标签数据"),
    DICT_TYPE_LIST("DICT_TYPE_LIST","数据字典类型查询");;

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeResourceEnum getDescription(String type){

        for(ApiTypeResourceEnum apiTypeEnum: ApiTypeResourceEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeResourceEnum(String apiType, String description){

        this.apiType =apiType;
        this.description = description;
    }
}
