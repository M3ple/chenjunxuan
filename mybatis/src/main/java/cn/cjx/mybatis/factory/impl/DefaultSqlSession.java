package cn.cjx.mybatis.factory.impl;

import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.config.MappedStatement;
import cn.cjx.mybatis.factory.Executor;
import cn.cjx.mybatis.factory.SqlSession;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/25 0025 00:04
 * @创建人:陈俊旋
 */
@Data
public class DefaultSqlSession implements SqlSession {
    /**
     * 1、Class.forName() 方法要求JVM查找并加载指定的类到内存中；
     * 2、将"com.mysql.jdbc.Driver" 当做参数传入，就是告诉JVM，去"com.mysql.jdbc"这个路径下找Driver类，将其加载到内存中。
     * 3、由于JVM加载类文件时会执行其中的静态代码块，从Driver类的源码中可以看到该静态代码块执行的操作是：将mysql的driver注册到系统的DriverManager中。
     */
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<E> list = simpleExecutor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<T> list = selectList(statementid, params);
        if (list!=null && list.size()>0){
            return list.get(0);
        }
        throw new RuntimeException("结果不存在或者存在多个");
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Class<?> declaringClass = method.getDeclaringClass();
                String statementId = declaringClass.getName()+"."+method.getName();
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                Executor executor = new SimpleExecutor();
                List<Object> objectList = executor.query(configuration, mappedStatement, args);
                Class<?> returnType = method.getReturnType();
                // 是collection子类
                if (Collection.class.isAssignableFrom(returnType)){
                    return objectList;
                }
                return objectList.get(0);
            }
        });
        return (T) o;
    }
    //-------------------------------connection-------------------------------
}
