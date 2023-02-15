package com.Bibo.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 */
@Target(ElementType.METHOD) //注解放置的目标位置，METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪阶段执行
@Documented //生成文档
public @interface DataScope {
    /**
     * 部门表别名
     */
    public String deptAlias() default "";

    /**
     * 用户表别名
     */
    public String userAlias() default "";
}
