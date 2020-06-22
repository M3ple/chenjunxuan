package cn.cjx.mybatis.utils;

import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.entity.Resources;
import cn.cjx.mybatis.factory.SqlSessionFactory;
import cn.cjx.mybatis.factory.impl.DefaultSqlSessionFactory;

import java.io.InputStream;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/24 0024 23:55
 * @创建人:陈俊旋
 */
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(InputStream in) throws Exception {
        //-------------------------------读取配置文件-------------------------------
        Configuration configuration = new Configuration();
        XMLConfigerBuilder xmlConfigerBuilder = new XMLConfigerBuilder(configuration);
        xmlConfigerBuilder.parse(in);
        return new DefaultSqlSessionFactory(configuration);
    }

    public static SqlSessionFactory build(String path) throws Exception {
        InputStream in = Resources.getResourceAsSteam(path);
        return build(in);
    }
}
