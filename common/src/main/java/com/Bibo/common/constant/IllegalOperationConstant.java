package com.Bibo.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 非法营运 车辆审核的取值范围
 */
public class IllegalOperationConstant {

    /**
     * 主要用途的取值范围
     */
    public static List<String> MAIN_USE_VALUE = Arrays.asList("GUEST","GOODS","GUEST_GOODS","UNKNOWN");

    /**
     * 入员变换度的取值范围
     */
    public static List<String> STAFF_CHANGE_VALUE = Arrays.asList("HIGH","LOW","UNKNOWN");

    public static List<String> IS_OVERSTAFFED_VALUE = Arrays.asList("YES","NO","UNKNOWN");
}
