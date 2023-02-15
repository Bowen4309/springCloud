package com.Bibo.common.constant;

public class UserConstants {


    /**
     * 状态-正常
     */
    public static final String TRAFFIC_STATUS_NORMAL = "0";

    /**
     * 状态-已停用
     */
    public static final String TRAFFIC_STATUS_DISABLE = "1";

    /**
     * 状态-已删除
     */
    public static final String TRAFFIC_STATUS_DELETE = "2";

    /**
     * 菜单类型-菜单
     */
    public static final String TYPE_MENU = "1";

    /**
     * 菜单类型-目录
     */
    public static final String TYPE_DIR = "2";

    /**
     * 菜单类型-按钮
     */
    public static final String TYPE_BUTTON = "3";


    /**
     * 系统类型-全部
     */
    public static final String TRAFFIC_ALL_TYPE = "0";

    /**
     * 系统类型-后台
     */
    public static final String TRAFFIC_ADMIN_TYPE = "1";

    /**
     * 系统类型-网格
     */
    public static final String TRAFFIC_GRID_TYPE = "2";

    /**
     * 系统类型-APP
     */
    public static final String TRAFFIC_APP_TYPE = "3";


    /**
     * 意见反馈查看-发送
     */
    public static final String OPINION_READER_TYPE = "1";

    /**
     * 意见反馈查看-接受
     */
    public static final String OPINION_RECEIVER_TYPE = "2";

    /**
     * 用户登录前缀
     */
    public static final String LOGIN_USER_ID = "login_user_id:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "user_token:";

    /**
     * 登录用户 redis key
     */
    public static final String DEPT_ID_KEY = "dept:";

    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "RZZX-USERTOKEN";

    /**
     * 外部调用接口对象 redis key
     */
    public static final String KEY_MANAGE = "key_manage:";
}
