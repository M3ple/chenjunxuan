package cn.cjx.mybatis.factory.impl;

import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.factory.SqlSession;
import cn.cjx.mybatis.factory.SqlSessionFactory;
/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/25 0025 00:03
 * @创建人:陈俊旋
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() throws Exception {
        return new DefaultSqlSession(configuration);
    }
}
