package com.lagou.edu.factory;

import com.lagou.edu.pojo.TransactionManagerStrategy;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @功能描述: BeanDefinition
 * @创建日期: 2020/4/23 11:24
 * @创建人:陈俊旋
 */
public class BeanDefinition {
    private String className;
    private String classSimpleName;
    private Class clazz;
    private List<Field> autoWiredFields;
    private TransactionManagerStrategy transactionManagerStrategy;
    private String alias;
    private com.lagou.edu.enums.ProxyTypeEnum ProxyTypeEnum;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Field> getAutoWiredFields() {
        return autoWiredFields;
    }

    public void setAutoWiredFields(List<Field> autoWiredFields) {
        this.autoWiredFields = autoWiredFields;
    }

    public TransactionManagerStrategy getTransactionManagerStrategy() {
        return transactionManagerStrategy;
    }

    public void setTransactionManagerStrategy(TransactionManagerStrategy transactionManagerStrategy) {
        this.transactionManagerStrategy = transactionManagerStrategy;
    }

    public com.lagou.edu.enums.ProxyTypeEnum getProxyTypeEnum() {
        return ProxyTypeEnum;
    }

    public void setProxyTypeEnum(com.lagou.edu.enums.ProxyTypeEnum proxyTypeEnum) {
        ProxyTypeEnum = proxyTypeEnum;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "className='" + className + '\'' +
                ", classSimpleName='" + classSimpleName + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
