package com.Bibo.common.constant;

public enum KafkaMessageTypeEnum {

    EARLY_WARNING_DATA("EARLY_WARNING_DATA","全量预警驾驶人信息"),
    EARLY_WARNING_DATA_DETAIL_SAVE("EARLY_WARNING_DATA_DETAIL_SAVE","全量预警驾驶人信息保存"),
    EARLY_WARNING_DATA_DETAIL_FILL("EARLY_WARNING_DATA_DETAIL_FILL","全量预警驾驶人信息填充"),
    BAYONET_DATA("BAYONET_DATA","市局卡口信息");


    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static KafkaMessageTypeEnum getDescription(String type){

        for(KafkaMessageTypeEnum apiTypeEnum: KafkaMessageTypeEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    KafkaMessageTypeEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
