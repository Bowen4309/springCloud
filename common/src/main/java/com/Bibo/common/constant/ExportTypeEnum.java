package com.Bibo.common.constant;

public enum ExportTypeEnum {

    SEX("1","男");

    private String typeCode;

    private String typeDoc;

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeDoc() {
        return typeDoc;
    }

    public static ExportTypeEnum getDescription(String type){

        for(ExportTypeEnum exportTypeEnum: ExportTypeEnum.values()){
            if(exportTypeEnum.getTypeCode().equals(type)){
                return  exportTypeEnum;
            }
        }
        return null;
    }

    ExportTypeEnum(String typeCode, String typeDoc){
        this.typeCode =typeCode;
        this.typeDoc = typeDoc;
    }
}
