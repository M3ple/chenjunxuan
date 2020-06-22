package cn.cjx.service;

import cn.cjx.pojo.Resume;
import java.util.List;

/**
 * @Author: xiaoxiang.zhang
 * @Description:Rsume简历实例化对象
 * @Date: Create in 5:09 下午 2020/3/17
 */
public interface ResumeService {

  Resume selectOne(Long id);

  List<Resume> selectAll();

  void insert(Resume resume);

  void delete(Long id);

  void update(Resume resume);
}
