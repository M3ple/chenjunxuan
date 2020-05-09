package cn.cjx.dao;

import cn.cjx.pojo.Resume;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 4:53 下午 2020/3/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResumeDaoTest {

  @Autowired
  private ResumeDao resumeDao;

  @Test
  public void testSave() {
    Resume resume = new Resume();
    resume.setId(4L);
    resume.setName("波波AV");
    resume.setAddress("深圳");
    resume.setPhone("131633332");
    resumeDao.save(resume);
  }

  @Test
  public void testFindAll() {
    List<Resume> all = resumeDao.findAll();
    all.forEach(System.out::println);
  }
}
