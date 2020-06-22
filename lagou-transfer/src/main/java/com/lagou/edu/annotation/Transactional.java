package com.lagou.edu.annotation;

import java.lang.annotation.*;

/**
 * @功能描述: transactional
 * @创建日期: 2020/4/23 10:24
 * @创建人:陈俊旋
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {
}
