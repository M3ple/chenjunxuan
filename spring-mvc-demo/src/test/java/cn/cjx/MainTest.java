package cn.cjx;

import cn.cjx.annotation.AliasForTest;

import java.lang.annotation.Annotation;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 10:14
 * @创建人:陈俊旋
 */
public class MainTest {
    public static void main(String[] args) {
        Class<SpringMvcDemoApplicationTest> clazz = SpringMvcDemoApplicationTest.class;
        clazz.getDeclaredAnnotation(AliasForTest.class);
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            System.out.println(annotation.annotationType());
        }
    }
}
