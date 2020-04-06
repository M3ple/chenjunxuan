package cn.cjx.mybatis.factory;

import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/25 0025 00:05
 * @创建人:陈俊旋
 */
public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementid, Object... params) throws Exception;

    //根据条件查询单个
    public <T> T selectOne(String statementid,Object... params) throws Exception;


    //为Dao接口生成代理实现类
    public <T> T getMapper(Class<T> mapperClass);
}
