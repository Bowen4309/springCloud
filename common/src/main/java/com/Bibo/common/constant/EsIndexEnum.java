package com.Bibo.common.constant;

public enum EsIndexEnum {

    OBJECT_CAR_NAME("机动车","gzjj_car"),
    OBJECT_DRIVER_NAME("驾驶人","gzjj_driver"),
    OBJECT_FACILITY_NAME("交通设施","gzjj_facility"),
    OBJECT_COMPANY_NAME("企业","gzjj_company"),
    OBJECT_POLICE_NAME("警员","gzjj_police");


    private String objectName;

    private String index;

    public String getObjectName() {
        return objectName;
    }

    public String getIndex() {
        return index;
    }

    public static EsIndexEnum getDescription(String objectName){

        for(EsIndexEnum esIndexEnum: EsIndexEnum.values()){
            if(esIndexEnum.getObjectName().equals(objectName)){
                return  esIndexEnum;
            }
        }
        return null;
    }

    EsIndexEnum(String objectName, String index){
        this.objectName =objectName;
        this.index = index;
    }

}
