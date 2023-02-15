package com.Bibo.common.constant;

public enum ApiTypeTagEnum {

     TAG_DATA_LIST("TAG_DATA_LIST","标签目录"),
    TAG_DATA_RESOURCE("TAG_DATA_RESOURCE","资源对应标签目录"),
    TAG_DATA_PAGE("TAG_DATA_PAGE","获取标签分页列表数据"),
    TAG_DATA_ADD_RESOURCE("TAG_DATA_ADD_RESOURCE","为资源对象添加标签"),
    TAG_DATA_EDIT("TAG_DATA_EDIT","编辑标签数据"),
    TAG_DATA_ADD("TAG_DATA_ADD","新增标签数据"),
    TAG_DATA_DELETE("TAG_DATA_DELETE","删除标签数据");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeTagEnum getDescription(String type){

        for(ApiTypeTagEnum apiTypeEnum: ApiTypeTagEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeTagEnum(String apiType, String description){

        this.apiType =apiType;
        this.description = description;
    }
}
