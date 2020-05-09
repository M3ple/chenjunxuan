package cn.cjx.interceptor;

import cn.cjx.pojo.User;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author: xiaoxiang.zhang
 * @Description:
 * @Date: Create in 7:47 下午 2020/3/18
 */
public class LoginInterceptor implements HandlerInterceptor {

  //不拦截的请求
  private static final String[] IGNORE_URI = {"/login.jsp", "/login"};

  /*该方法进行处理器拦截，该方法将在Controller处理之前调用。该方法返回true拦截器才会继续往下执行，返回false整个请求结束*/
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println("AuthorizationInterceptor preHandle -->");
    //flag判断用户是否登录
    boolean flag = false;
    //获取请求的路径进行判断
    String servletPath = request.getServletPath();
    //判断是否需要拦截请求
    for (String s : IGNORE_URI) {
      if (servletPath.contains(s)) {
        flag = true;
        break;
      }
    }
    //拦截请求
    if (!flag) {
      //获取session总的用户
      User user = (User) request.getSession().getAttribute("user");
      //判断是否登录
      if (user == null) {
        //用户没登录
        System.out.println("AuthorizationInterceptor拦截请求");
        if (request.getHeader("x-requested-with") != null && request
            .getHeader("x-requested-with")
            .equalsIgnoreCase("XMLHttpRequest")) {
          //如果是ajax请求响应头会有x-requested-with
          PrintWriter out = response.getWriter();
          out.print("loseSession");//session失效
          out.flush();
          flag = false;
        }
      } else {
        //已登录
        System.out.println("AuthorizationInterceptor放行请求");
        flag = true;
      }
    }
    return flag;
  }
}

