package com.Bibo.gateway.constant;

public class UrlEnum {

    //用户令牌检验
    public  static String CHECK_USER_URL= "https://68.32.104.219:7008/token-server/sapi/validateusertoken";

    //获取用户信息
    public  static String GET_LOGIN_USER_URL= "https://68.32.104.219:7008/token-server/sapi/queryusertoken";

    //应用令牌检验
    public  static String CHECK_APP_URL = "https://68.32.104.219:7008/token-server/sapi/validateapptoken";

    //获取用户详情数据
    public  static String GET_USER_INFO_URL= "https://68.32.104.219:7008/uac/openAPI/queryPerson/v2";

}
