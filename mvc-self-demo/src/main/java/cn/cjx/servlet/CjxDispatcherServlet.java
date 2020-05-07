package cn.cjx.servlet;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
public class CjxDispatcherServlet extends HttpServlet implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
//        initMultipartResolver(context);
//        initLocaleResolver(context);
//        initThemeResolver(context);
//        initHandlerMappings(context);
//        initHandlerAdapters(context);
//        initHandlerExceptionResolvers(context);
//        initRequestToViewNameTranslator(context);
//        initViewResolvers(context);
//        initFlashMapManager(context);
    }

    private void initHandlerMappings(ApplicationContext context) {
    }
}
