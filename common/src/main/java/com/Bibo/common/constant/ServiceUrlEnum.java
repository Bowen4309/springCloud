package com.Bibo.common.constant;

public class ServiceUrlEnum {

    //居住证数据接口服务地址
    public static String RESIDENCE_PERMIT_URL="http://68.60.0.116:8000/gdgzhzldrkjzzxx";

    //车辆卡口通过
    public static String VEHICLE_CHECKPOINT_URL="http://68.60.0.116:8000/api/res/R-440100000000-80000342/default";

    //智慧搜接口服务地址
    public static String INTELLIGENCE_SEARCH= "http://68.60.0.116:8000/ui/sassService";


    public static String CK_SEARCH_SERVICE= "http://68.60.0.116:8000/search/CkSearchService/scroll/keyTimeSearch";

    //车辆GPS轨迹数据接口
    public static String CK_SEARCH_SERVICE_GEOCRASHWITHCONDITIONS = "http://68.60.0.116:8000/search/CkSearchService/scroll/geoCrashWithConditions";

    //GPS实时定位接口
    public static String CK_SEARCH_SERVICE_REALTIME_LOCATION="http://68.60.0.116:8000/search/CkSearchService/rediscall/getIdcardInfo";

    //图层搜索接口服务
    public static String MAP_POI_SERVICE="http://68.60.0.116:8000/search/EzPoiSearchS/PoiSearchV1REST?layers=XZQH_CZC_PG&type=FQ&method=FULL&scope=_NAMES&pageIndex=0&pageSize=300";

    //天河接口post接口
    public static String POST_DATA_URL="http://68.60.0.116:8000/search/traffic/apiData/postData";



}
