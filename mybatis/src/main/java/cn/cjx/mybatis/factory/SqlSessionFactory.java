package cn.cjx.mybatis.factory;

import cn.cjx.mybatis.config.Configuration;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/24 0024 23:53
 * @创建人:陈俊旋
 */
public interface SqlSessionFactory {
    SqlSession openSession() throws Exception;
}
