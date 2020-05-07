package cn.cjx.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 09:43
 * @创建人:陈俊旋
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Animal
public @interface AliasForTest {

    @AliasFor(annotation = Animal.class,attribute = "value")
    String name() default "";

    @AliasFor(annotation = Animal.class,attribute = "value")
    String profile() default "";
}
