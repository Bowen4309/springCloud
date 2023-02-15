package com.Bibo.common.constant;

/**
 * 专题分析审批清单
 */
public enum ApiTypeAnalyseApproveEnum {

    APPROVE_ADD("APPROVE_ADD","新增审批单"),
    APPROVE_SETTLE_ACCOUNT("APPROVE_SETTLE_ACCOUNT","各大队审核车辆状况"),
    APPROVE_CAR_PICTURE_SAVE("APPROVE_CAR_PICTURE_SAVE","审核车辆保存"),
    CAR_PICTURE_SELECT_BY_CARNUMBER("CAR_PICTURE_SELECT_BY_CARNUMBER","根据车牌号码查询车辆保存图片"),
    CAR_PICTURE_SELECT_BY_ID("CAR_PICTURE_SELECT_BY_ID","根据id查询图片"),
    CAR_BASE_PICTURE_UPDATE("CAR_BASE_PICTURE_SAVE","车辆备案图片保存"),
    CAR_BASE_PICTURE_SELECT("CAR_BASE_PICTURE_SELECT","车辆备案图片查询"),
    APPROVE_DISQUALIFY_PICTURE_SAVE("APPROVE_DISQUALIFY_PICTURE_SAVE","失格图片保存"),
    DISQUALIFY_PICTURE_SELECT_BY_IDCARD("DISQUALIFY_PICTURE_SELECT_BY_IDCARD","根据id查询图片"),
    ADD_SIGN("ADD_SIGN","新增审核图片无法显示标记");

    private String apiType;

    private String description;

    public String getApiType() {
        return apiType;
    }

    public String getDescription() {
        return description;
    }

    public static ApiTypeAnalyseCompanyEnum getDescription(String type){

        for(ApiTypeAnalyseCompanyEnum apiTypeEnum: ApiTypeAnalyseCompanyEnum.values()){
            if(apiTypeEnum.getApiType().equals(type)){
                return  apiTypeEnum;
            }
        }
        return null;
    }

    ApiTypeAnalyseApproveEnum(String apiType, String description){
        this.apiType =apiType;
        this.description = description;
    }
}
