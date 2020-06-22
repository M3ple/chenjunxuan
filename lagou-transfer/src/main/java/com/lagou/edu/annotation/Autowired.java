package com.lagou.edu.annotation;

import java.lang.annotation.*;

/**
 * @功能描述: Autowired
 * @创建日期: 2020/4/23 10:24
 * @创建人:陈俊旋
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;
}
