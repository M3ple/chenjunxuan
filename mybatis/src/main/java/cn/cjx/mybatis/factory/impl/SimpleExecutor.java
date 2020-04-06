package cn.cjx.mybatis.factory.impl;


import cn.cjx.mybatis.config.BoundSql;
import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.config.MappedStatement;
import cn.cjx.mybatis.factory.Executor;
import cn.cjx.mybatis.utils.GenericTokenParser;
import cn.cjx.mybatis.utils.ParameterMapping;
import cn.cjx.mybatis.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleExecutor implements Executor {


    @Override                                                                                //user
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 前处理
        PreparedStatement preparedStatement = preHandle(configuration, mappedStatement, params);

        // 5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> resultTypeClass = mappedStatement.getResultType();
        List<E> results = new ArrayList<>();
        List<Field> fieldList = Arrays.asList(resultTypeClass.getDeclaredFields());
        List<String> fieldNameList = fieldList.stream().map(Field::getName).collect(Collectors.toList());
        // 6. 封装返回结果集
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段的值
                Object value = resultSet.getObject(columnName);
                // 字段存在则映射
                if (fieldNameList.contains(columnName)){
                    //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(o, value);
                }
//                PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(o);
//                propertyEditor.setValue(value);
            }
            results.add((E) o);
        }
        return results;
    }

    private PreparedStatement preHandle(Configuration configuration, MappedStatement mappedStatement, Object[] params) throws SQLException, NoSuchFieldException, IllegalAccessException {
        // 1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 获取sql语句 : select * from user where id = #{id} and userName = #{userName}
        //转换sql语句： select * from user where id = ? and userName = ? ，转换的过程中，还需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = this.getBoundSql(sql);

        // 3.获取预处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4. 设置参数
        //获取到了参数的全路径
        Class<?> paramtertypeClass = mappedStatement.getParamterType();

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        if (parameterMappingList!=null && parameterMappingList.size()>0){
            for (int i = 0; i < parameterMappingList.size(); i++) {
                ParameterMapping parameterMapping = parameterMappingList.get(i);
                String content = parameterMapping.getContent();

                //反射
                Field declaredField = paramtertypeClass.getDeclaredField(content);
                //暴力访问
                declaredField.setAccessible(true);
                Object o = declaredField.get(params[0]);

                preparedStatement.setObject(i + 1, o);
            }
        }
        return preparedStatement;
    }

    @Override
    public Integer insert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 前处理
        PreparedStatement preparedStatement = preHandle(configuration, mappedStatement, params);
        preparedStatement.executeUpdate();
        return preparedStatement.getUpdateCount();
    }

    @Override
    public Integer update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 前处理
        PreparedStatement preparedStatement = preHandle(configuration, mappedStatement, params);
        preparedStatement.executeUpdate();
        return preparedStatement.getUpdateCount();
    }

    @Override
    public Integer delete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        // 前处理
        PreparedStatement preparedStatement = preHandle(configuration, mappedStatement, params);
        preparedStatement.executeUpdate();
        return preparedStatement.getUpdateCount();
    }

    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
     * @param sql
     * @return BoundSql
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        //解析出来的sql
        String parseSql = tokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }


}
