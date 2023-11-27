package com.example.config.dynamicDb.annotation;

import java.lang.annotation.*;

/**
 * PROJECT:chart
 * PACkAGE:com.example.config.dynamicDb.annotation
 * Date:2023/11/27 17:23
 *
 * @author BaiYiChen
 */

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBUSE {
    String value() default "mysql";
}
