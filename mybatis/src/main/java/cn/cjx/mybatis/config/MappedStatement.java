package cn.cjx.mybatis.config;

import lombok.Data;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/29 0029 23:10
 * @创建人:陈俊旋
 */
@Data
public class MappedStatement {
    private String id;
    private String sql;
    private Class<?> resultType;
    private Class<?> paramterType;
}