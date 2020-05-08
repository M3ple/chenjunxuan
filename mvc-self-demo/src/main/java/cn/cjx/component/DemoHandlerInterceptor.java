package cn.cjx.component;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/8 18:05
 * @创建人:陈俊旋
 */
@Component
public class DemoHandlerInterceptor implements CjxHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
        if (handler instanceof CjxHandlerMethod){
            boolean b = ((CjxHandlerMethod) handler).checkSecurity(request);
            if (!b){
                response.getWriter().write("has no access to path "+request.getRequestURI());
            }
            return b;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {

    }
}
