package com.Bibo.common.annotation;

import com.Bibo.common.constant.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * 日志
 */
@Target({ElementType.PARAMETER, ElementType.METHOD}) //注解放置的目标位置，METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪阶段执行
@Documented //生成文档
public @interface SysLog {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能类别
     */
    public OperatorTypeEnum operatorType() default OperatorTypeEnum.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
