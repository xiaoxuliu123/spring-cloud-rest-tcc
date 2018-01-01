package com.github.prontera.config;

import com.github.prontera.config.dbsource.CustomizeDS;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    CustomizeDS value() default CustomizeDS.ORDER;
}