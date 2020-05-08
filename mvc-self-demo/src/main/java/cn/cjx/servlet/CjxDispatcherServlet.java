package cn.cjx.servlet;

import cn.cjx.component.CjxHandlerMethod;
import cn.cjx.component.HandlerMethodMapping;
import cn.cjx.utils.SpringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 17:35
 * @创建人:陈俊旋
 */
@WebServlet(urlPatterns = "/*")
public class CjxDispatcherServlet extends HttpServlet{

    private HandlerMethodMapping handlerMethodMapping;

    @Override
    public void init() throws ServletException {
        String simpleName = HandlerMethodMapping.class.getSimpleName();
        char[] chars = simpleName.toCharArray();
        if('A'<= chars[0] && chars[0]<='Z'){
            chars[0] = (char) (chars[0]+32);
            simpleName = new String(chars);
        }
        handlerMethodMapping = SpringUtils.getBean(simpleName);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = getServletContext().getContextPath();
        CjxHandlerMethod handlerMethod = handlerMethodMapping.getHandlerMethod(req,contextPath);
        if(handlerMethod == null) {
            resp.getWriter().write("404 not found");
            return;
        }
        handlerMethod.handle(req,resp);
    }
}
