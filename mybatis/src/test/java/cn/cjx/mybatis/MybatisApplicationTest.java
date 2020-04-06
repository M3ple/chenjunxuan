package cn.cjx.mybatis;

import cn.cjx.mybatis.dao.IUserDao;
import cn.cjx.mybatis.entity.Resources;
import cn.cjx.mybatis.entity.User;
import cn.cjx.mybatis.factory.SqlSession;
import cn.cjx.mybatis.factory.SqlSessionFactory;
import cn.cjx.mybatis.utils.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;

@SpringBootTest(classes = MybatisApplication.class)
@RunWith(SpringRunner.class)
public class MybatisApplicationTest {

    IUserDao userDao = null;

    @Before
    public void init() throws Exception {
        InputStream in = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @Test
    public void select(){
        User condition = new User();
        condition.setId(1);
        condition.setUserName("王五");
        List<User> conditionList = userDao.findByCondition(condition);
        System.out.println(conditionList);
        List<User> all = userDao.findAll();
        System.out.println(all);
    }

    @Test
    public void insert(){
        User user = new User();
        user.setUserName("cjx_test_insert");
        userDao.insert(user);
    }

    @Test
    public void update(){
        User user = new User();
        user.setId(1);
        user.setUserName("cjx_test_update");
        userDao.updateById(user);
    }

    @Test
    public void delete(){
        User user = new User();
        user.setId(27);
        userDao.delete(user);
    }

}
