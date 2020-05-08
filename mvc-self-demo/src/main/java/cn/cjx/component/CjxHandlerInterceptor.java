package cn.cjx.component;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/8 18:03
 * @创建人:陈俊旋
 */
public interface CjxHandlerInterceptor{

    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception;

    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception;
}
