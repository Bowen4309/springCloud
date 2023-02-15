package com.Bibo.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExportAnnotation {

    String value() default "";
    
    String type() default "";

}
