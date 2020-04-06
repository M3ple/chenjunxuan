package cn.cjx.mybatis.dao;

import cn.cjx.mybatis.entity.User;

import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/31 0031 21:37
 * @创建人:陈俊旋
 */
public interface IUserDao {

    List<User> findAll();

    List<User> findByCondition(User user);
}
