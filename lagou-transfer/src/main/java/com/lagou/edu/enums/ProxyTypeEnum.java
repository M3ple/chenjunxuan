package com.lagou.edu.enums;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/4/23 15:27
 * @创建人:陈俊旋
 */
public enum ProxyTypeEnum {

    CJLIB(1),
    JDK(2);

    int value;

    ProxyTypeEnum(int value) {
        this.value = value;
    }
}
