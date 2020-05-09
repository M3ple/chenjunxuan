package cn.cjx.controller;

import cn.cjx.service.ResumeService;
import cn.cjx.ResponseMsg;
import cn.cjx.pojo.Resume;
import cn.cjx.pojo.User;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xiaoxiang.zhang
 * @Description:简历类前段控制器
 * @Date: Create in 6:44 下午 2020/3/17
 */
@Controller
public class ResumeController {

  @Autowired
  private ResumeService resumeService;

  @RequestMapping("/selectAll")
  @ResponseBody
  public List<Resume> selectAll() {
    return resumeService.selectAll();
  }

  @RequestMapping("/insert")
  @ResponseBody
  public ResponseMsg insertResume(@RequestBody Resume resume) {
    try {
      resumeService.insert(resume);
    } catch (Exception e) {
      return new ResponseMsg("500", "FAIL", e.getMessage());
    }
    return new ResponseMsg("200", "SUCCESS", null);
  }

  @RequestMapping("/delete")
  @ResponseBody
  public ResponseMsg deleteResume(Long id) {
    try {
      resumeService.delete(id);
    } catch (Exception e) {
      return new ResponseMsg("500", "FAIL", e.getMessage());
    }
    return new ResponseMsg("200", "SUCCESS", null);
  }

  @RequestMapping("/update")
  @ResponseBody
  public ResponseMsg updateResume(@RequestBody Resume resume) {
    try {
      resumeService.update(resume);
    } catch (Exception e) {
      return new ResponseMsg("500", "FAIL", e.getMessage());
    }
    return new ResponseMsg("200", "SUCCESS", null);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public void login(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response) {
    try {
      if ("admin".equals(user.getUsername()) && "admin".equals(user.getPassword())) {
        request.getSession().setAttribute("user", user);

        response.sendRedirect("/list.jsp");
        return;
      }
      response.sendRedirect("/login.jsp");
      return;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
