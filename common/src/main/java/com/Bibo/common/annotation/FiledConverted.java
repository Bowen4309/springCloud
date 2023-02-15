package com.Bibo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
public @interface FiledConverted {

    /**
     * 字典类型（填写数据库创建的dictionary_code）
     */
    String dictType() default "";
    /**
     * 字段值为null时替换默认字符串
     */
    String defaultValue() default "";
    /**
     * 获取对照后的target_code还是target_name(0取code,1取name)
     */
    int getCodeOrName() default 0;
    /**
     * 该字段是否必须有对照结果 默认是，如果找不到对照项抛异常
     */
    boolean isRequired() default true;
}
