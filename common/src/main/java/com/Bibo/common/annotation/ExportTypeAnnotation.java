package com.Bibo.common.annotation;

import com.Bibo.common.servie.ExportTypeHandler;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExportTypeAnnotation {

    Class<? extends ExportTypeHandler> typeHandler();
}
