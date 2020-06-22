package cn.cjx.dao;

import cn.cjx.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author: xiaoxiang.zhang
 * @Description: Resume的基本操作
 * @Date: Create in 4:15 下午 2020/3/17
 */
public interface ResumeDao extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {

}
