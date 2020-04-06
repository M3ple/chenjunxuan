package cn.cjx.mybatis;

import cn.cjx.mybatis.dao.IUserDao;
import cn.cjx.mybatis.entity.Resources;
import cn.cjx.mybatis.entity.User;
import cn.cjx.mybatis.factory.SqlSession;
import cn.cjx.mybatis.factory.SqlSessionFactory;
import cn.cjx.mybatis.utils.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;

@SpringBootTest(classes = MybatisApplication.class)
@RunWith(SpringRunner.class)
public class MybatisApplicationTest {

    @Test
    public void esSave() throws Exception {
//        String path = "";
//        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(path);
        InputStream in = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build(in);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User condition = new User();
        condition.setId(1);
        condition.setUsername("王五");
        List<User> conditionList = userDao.findByCondition(condition);
        System.out.println(conditionList);
        List<User> all = userDao.findAll();
        System.out.println(all);
    }

}
