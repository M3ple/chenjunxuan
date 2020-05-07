package cn.cjx.annotation;

import java.lang.annotation.*;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 09:43
 * @创建人:陈俊旋
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Animal {
    String value() default "";
}
