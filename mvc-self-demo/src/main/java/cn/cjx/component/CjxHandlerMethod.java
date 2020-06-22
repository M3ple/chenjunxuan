package cn.cjx.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 0007 23:57
 * @创建人:陈俊旋
 */
public class CjxHandlerMethod {
    private Method method;
    private Class clazz;
    private String className;
    private Object instance;
    private String[] securitySheet;

    public String[] getSecuritySheet() {
        return securitySheet;
    }

    public void setSecuritySheet(String[] securitySheet) {
        this.securitySheet = securitySheet;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Object[] args = new Object[method.getParameterCount()];
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.getType().equals(HttpServletRequest.class)){
                    args[i] = req;
                }else if (parameter.getType().equals(HttpServletResponse.class)){
                    args[i] = resp;
                }else {
                    args[i] = req.getAttribute(parameter.getName());
                    if (args[i] ==null){
                        args[i] = req.getParameter(parameter.getName());
                    }
                }
            }
            Object result = method.invoke(instance, args);
            resp.getWriter().write("results: "+result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(CjxHandlerMethod.class.getMethods()[0].getParameterTypes()[0].getName());
        System.out.println(CjxHandlerMethod.class.getMethods()[0].getParameterTypes()[0].getSimpleName());
        System.out.println(CjxHandlerMethod.class.getMethods()[0].getParameters()[0].getName());
    }

    public boolean checkSecurity(HttpServletRequest request) {
        String username = request.getParameter("username");
        boolean hasAccess = false;
        if (username==null){
            username = (String) request.getAttribute("username");
        }
        if (securitySheet !=null && securitySheet.length>0){
            if (username!=null){
                for (String access : securitySheet) {
                    if (hasAccess = access.equals(username)){
                        break;
                    }
                }
            }
        }else {
            hasAccess = true;
        }
        return hasAccess;
    }

}
