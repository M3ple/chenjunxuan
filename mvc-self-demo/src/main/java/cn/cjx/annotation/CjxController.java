package cn.cjx.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 18:26
 * @创建人:陈俊旋
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface CjxController{
    @AliasFor(value = "value",annotation = Component.class)
    String value() default "";
}
