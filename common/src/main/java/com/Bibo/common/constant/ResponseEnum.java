package com.Bibo.common.constant;

/**
 * @program: spring_cloud_traffic
 * @description: 返回对象枚举信息
 * @author: yuanbo
 * @create: 2021-07-22 15:01
 **/
public enum  ResponseEnum {
    //接口正常返回成功
    SUCCESS("200","操作成功"),
    ERROR("201","用户域处理请求异常，请联系系统管理员"),

    API_ERROR("10001","接口错误"),

    /* 用户错误 */
    USER_NOT_LOGIN("201", "用户未登录"),
    USER_NOT_PASSWORD("202", "新旧密码不可重复"),
    EMAIL_LOSE_TIME("203","验证码已经失效！！！"),
    EMAIL_NOT_CODE("204","验证码不正确！！！"),
    EMAIL_NOT_LIVE("205","邮箱地址重复"),

    /* 业务错误 */
    NO_PERMISSION("403", "没有权限"),
    VALIDATE_FAILED("404", "参数检验失败"),
    UNAUTHORIZED("401", "暂未登录或token已经过期"),
    METHOD_NOT_ALLOWED("405", "不支持当前请求方法"),
    UNSUPPORTED_MEDIA_TYPE("415","不支持当前媒体类型"),
    UNPROCESSABLE_ENTITY("422","所上传文件大小超过最大限制，上传失败！"),
    INTERNAL_SERVER_ERROR("500","服务内部异常"),

    /* 定時任務 */
    INIT("600","record init"),
    TASKNO_EXIST("1001","该任务编号已经存在"),
    PARAM_EMPTY("6001","parameter is empty"),
    FROZEN("10001","FROZEN"),
    UNFROZEN("10002","UNFROZEN"),
    RUN_NOW_FAIL("7001","立即运行失败"),
    HTTP("10003","http"),
    KAFKA("10004","kafka"),
    UPDATE_FAIL("1002","更新失败"),
    NO_DATA("1003","无此定时任务编号"),
    ;


    private String code;
    private String msg;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
