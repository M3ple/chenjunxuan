package cn.cjx.mybatis.factory;

import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.config.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}
