package com.Bibo.common.constant;

public enum CrashTableTypeEnum {

    STAIX("staix","出租车"),
    SPOLICE("spolice","警车"),
    SBUS("sbus","公交车"),
    S800M("s800m","800M对讲机"),
    SDTCAR("sdtcar","危运车"),
    STOURISTBUS("stouristbus","旅游包车"),
    SHEAVYTRUCK("sheavytruck","重型货运车"),
    TRANSPORTBUS("transportBus","货运车"),
    STATTOO("stattoo","驾培车"),
    SBULKMATERIAL("sbulkmaterial","散体物料车"),
    SPASSENGER("spassenger","客运车"),
    ONLINECAR("onlinecar","网约车");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static CrashTableTypeEnum getDescription(String type){

        for(CrashTableTypeEnum apiTypeEnum: CrashTableTypeEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    CrashTableTypeEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }

}
