package cn.cjx.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/8 0008 22:12
 * @创建人:陈俊旋
 */
public class CjxHandlerInterceptorWrapper {
    private CjxHandlerInterceptor cjxHandlerInterceptor;
    private String path;

    public CjxHandlerInterceptorWrapper(DemoHandlerInterceptor demoHandlerInterceptor, String path) {
        this.cjxHandlerInterceptor = demoHandlerInterceptor;
        this.path = path;
    }

    public CjxHandlerInterceptor getCjxHandlerInterceptor() {
        return cjxHandlerInterceptor;
    }

    public void setCjxHandlerInterceptor(CjxHandlerInterceptor cjxHandlerInterceptor) {
        this.cjxHandlerInterceptor = cjxHandlerInterceptor;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean needIntercept(HttpServletRequest req, String contextPath) {
        String requestURI = req.getRequestURI();
        requestURI = requestURI.replaceFirst(contextPath,"");
        path = path.replaceFirst("[/**]","");
        return path.equals("") || requestURI.contains(path);
    }

    public boolean doPreIntercept(HttpServletRequest req, HttpServletResponse resp, CjxHandlerMethod handlerMethod) throws Exception {
        return cjxHandlerInterceptor.preHandle(req,resp,handlerMethod);
    }
}
