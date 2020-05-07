package cn.cjx.component;

import java.lang.reflect.Method;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 0007 23:57
 * @创建人:陈俊旋
 */
public class CjxHandlerMethod {
    private Method method;
    private Class clazz;
    private String className;
    private Object instance;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
