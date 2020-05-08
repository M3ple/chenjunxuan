package cn.cjx.servlet;

import cn.cjx.component.CjxHandlerInterceptorWrapper;
import cn.cjx.component.CjxHandlerMethod;
import cn.cjx.component.HandlerInterceptorMapping;
import cn.cjx.component.HandlerMethodMapping;
import cn.cjx.utils.SpringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 17:35
 * @创建人:陈俊旋
 */
@WebServlet(urlPatterns = "/*")
public class CjxDispatcherServlet extends HttpServlet{

    private HandlerMethodMapping handlerMethodMapping;
    private HandlerInterceptorMapping handlerInterceptorMapping;

    @Override
    public void init() throws ServletException {
        initHandlerMapping();
        initHandlerInterceptorMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String contextPath = getServletContext().getContextPath();
            CjxHandlerMethod handlerMethod = handlerMethodMapping.getHandlerMethod(req,contextPath);
            if(handlerMethod == null) {
                resp.getWriter().write("404 not found");
                return;
            }
            if (initInterceptorPreHandle(req,resp,handlerMethod,contextPath)){
                handlerMethod.handle(req,resp);
                initInterceptorPostHandle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHandlerInterceptorMapping() {
        handlerInterceptorMapping = SpringUtils.getBean(getBeanName(HandlerInterceptorMapping.class));
        handlerInterceptorMapping.init();
    }

    private void initHandlerMapping() {
        handlerMethodMapping = SpringUtils.getBean(getBeanName(HandlerMethodMapping.class));
    }

    private String getBeanName(Class<?> clazz) {
        String beanName = clazz.getSimpleName();
        char[] chars = beanName.toCharArray();
        if('A'<= chars[0] && chars[0]<='Z'){
            chars[0] = (char) (chars[0]+32);
            beanName = new String(chars);
        }
        return beanName;
    }

    private void initInterceptorPostHandle() {
        // TODO: 2020/5/8 0008
    }

    private boolean initInterceptorPreHandle(HttpServletRequest req, HttpServletResponse resp, CjxHandlerMethod handlerMethod, String contextPath) throws Exception {
        List<CjxHandlerInterceptorWrapper> interceptorWrappers = handlerInterceptorMapping.getInterceptorWrappers();
        if (interceptorWrappers!=null && !interceptorWrappers.isEmpty()){
            for (int i = 0; i < interceptorWrappers.size(); i++) {
                CjxHandlerInterceptorWrapper interceptorWrapper = interceptorWrappers.get(i);
                if (interceptorWrapper.needIntercept(req,contextPath)){
                    if (!interceptorWrapper.doPreIntercept(req,resp,handlerMethod)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
