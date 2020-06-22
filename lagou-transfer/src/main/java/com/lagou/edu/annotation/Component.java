package com.lagou.edu.annotation;

import com.lagou.edu.enums.ProxyTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
    ProxyTypeEnum proxyType() default ProxyTypeEnum.CJLIB;
}