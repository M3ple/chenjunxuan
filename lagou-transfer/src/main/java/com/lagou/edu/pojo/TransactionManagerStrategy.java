package com.lagou.edu.pojo;

import com.lagou.edu.enums.ProxyTypeEnum;

import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/4/23 15:13
 * @创建人:陈俊旋
 */
public class TransactionManagerStrategy {

    /**
     * 作用在类上
     */
    public static final int TYPE_SCOPE = 1;

    /**
     * 作用在方法上
     */
    public static final int METHOD_SCOPE = 2;

    private int scope;

    private ProxyTypeEnum proxyTypeEnum;

    private List<String> methodList;

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public List<String> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<String> methodList) {
        this.methodList = methodList;
    }

    public ProxyTypeEnum getProxyTypeEnum() {
        return proxyTypeEnum;
    }

    public void setProxyTypeEnum(ProxyTypeEnum proxyTypeEnum) {
        this.proxyTypeEnum = proxyTypeEnum;
    }
}
