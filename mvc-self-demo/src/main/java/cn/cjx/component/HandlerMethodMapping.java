package cn.cjx.component;

import cn.cjx.annotation.CjxController;
import cn.cjx.annotation.CjxRequestMapping;
import cn.cjx.annotation.Security;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 17:50
 * @创建人:陈俊旋
 */
@Component
public class HandlerMethodMapping implements ApplicationContextAware {
    /**
     * Bean name prefix for target beans behind scoped proxies. Used to exclude those
     * targets from handler method detection, in favor of the corresponding proxies.
     * <p>We're not checking the autowire-candidate status here, which is how the
     * proxy target filtering problem is being handled at the autowiring level,
     * since autowire-candidate may have been turned to {@code false} for other
     * reasons, while still expecting the bean to be eligible for handler methods.
     * <p>Originally defined in {@link org.springframework.aop.scope.ScopedProxyUtils}
     * but duplicated here to avoid a hard dependency on the spring-aop module.
     */
    private static final String SCOPED_TARGET_NAME_PREFIX = "scopedTarget.";
    private MappingRegistry mappingRegistry = new MappingRegistry();
    private ApplicationContext context;

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        initHandlerMethods();
//    }

    private void initHandlerMethods() {
        for (String beanName : getCandidateBeanNames()) {
            if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
                processCandidateBean(beanName);
            }
        }
//        handlerMethodsInitialized(getHandlerMethods());
    }

    /**
     * 处理所有的controller信息
     * @param beanName
     */
    private void processCandidateBean(String beanName) {
        Class<?> clazz = context.getType(beanName);
        if (clazz!=null && isHandler(clazz)){
            doInitHandlerMethodMapping(clazz);
        }
    }

    private void doInitHandlerMethodMapping(Class<?> clazz) {
        CjxRequestMapping typeRequestMapping = AnnotationUtils.findAnnotation(clazz, CjxRequestMapping.class);
        String[] headPath = null;
        // 处理类上的requestMapping
        if (null!=typeRequestMapping){
            headPath = getRequestMappingPath(typeRequestMapping);
        }
        Security typeSecurity = AnnotationUtils.findAnnotation(clazz, Security.class);
        String[] typeSecurityPath = null;
        if (typeSecurity!=null){
            typeSecurityPath = typeSecurity.value();
        }
        // 处理method上的requestMapping
        for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
            Method method = clazz.getDeclaredMethods()[i];
            CjxRequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(method, CjxRequestMapping.class);
            Security methodSecurity = AnnotationUtils.findAnnotation(method, Security.class);
            String[] methodPath = getRequestMappingPath(methodRequestMapping);
            String[] methodSecurityPath = null;
            if (methodSecurity!=null){
                methodSecurityPath = methodSecurity.value();
            }
            for (int j = 0; j < methodPath.length; j++) {
                String methodUri = trimHeadAndTailChar(methodPath[j],'/');
                if (headPath!=null){
                    for (int k = 0; k < headPath.length; k++) {
                        String headUri = trimHeadAndTailChar(headPath[k],'/');
                        mappingRegistry.regist("/"+headUri+"/"+methodUri,clazz,method, getSecurityPath(typeSecurityPath,methodSecurityPath),context);
                    }
                }else {
                    mappingRegistry.regist("/"+methodUri,clazz,method,getSecurityPath(typeSecurityPath,methodSecurityPath),context);
                }
            }
        }
    }

    private String[] getSecurityPath(String[] typeSecurityPath, String[] methodSecurityPath) {
        if (typeSecurityPath!=null && typeSecurityPath.length>0){
            if (methodSecurityPath!=null && methodSecurityPath.length>0){
                List<String> result = new ArrayList<>(Math.max(typeSecurityPath.length,methodSecurityPath.length));
                for (int i = 0; i < typeSecurityPath.length; i++) {
                    String typeSecurity = typeSecurityPath[i];
                    for (int j = 0; j < methodSecurityPath.length; j++) {
                        if (typeSecurity.equals(methodSecurityPath[j])){
                            result.add(typeSecurity);
                        }
                    }
                }
                return result.toArray(new String[result.size()]);
            }else {
                return typeSecurityPath;
            }
        }else {
            return methodSecurityPath;
        }
    }

    /**
     * 除去首位字符
     * @param str
     * @param c
     * @return
     */
    private String trimHeadAndTailChar(String str, char c) {
        int start = 0;
        int end = str.length();
        char[] chars = str.toCharArray();
        if (chars[0] == c){
            start = 1;
        }
        if (chars[chars.length-1] == c){
            end = end-1;
        }
        return str.substring(start,end);
    }

    private String[] getRequestMappingPath(CjxRequestMapping typeRequestMapping) {
        String[] uri = null;
        String[] values = typeRequestMapping.value();
        if (null==values){
            values = typeRequestMapping.path();
        }
        if (null!=values && values.length>0){
            uri = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                uri[i] = value;
            }
        }
        return uri;
    }

    /**
     * 判断是否为handler
     * @param beanType
     * @return boolean
     */
    private boolean isHandler(Class<?> beanType) {
        return (AnnotatedElementUtils.hasAnnotation(beanType, CjxController.class) ||
                AnnotatedElementUtils.hasAnnotation(beanType, CjxRequestMapping.class));
    }

    public CjxHandlerMethod getHandlerMethod(HttpServletRequest req, String contextPath) {
        String requestURI = req.getRequestURI();
        requestURI = requestURI.replaceFirst(contextPath,"");
        return mappingRegistry.getHandlerMethod(requestURI);
    }
    /**
     * 获取所有工厂中的beanNames
     * @return String[]
     */
    private String[] getCandidateBeanNames() {
        return context.getBeanNamesForType(Object.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        initHandlerMethods();
    }

    private class MappingRegistry {
        Map<String, CjxHandlerMethod> lookUps;
        public MappingRegistry() {
            lookUps = new HashMap<>();
        }

        public void regist(String uri, Class<?> clazz,Method method, String[] securitySheet,ApplicationContext context) {
            if (lookUps==null){
                lookUps = new HashMap<>();
            }
            Assert.notNull(context,"ApplicationContext is can not be null!");
            CjxHandlerMethod handlerMethod = new CjxHandlerMethod();
            handlerMethod.setClassName(clazz.getName());
            handlerMethod.setInstance(context.getBean(clazz));
            handlerMethod.setClazz(clazz);
            handlerMethod.setMethod(method);
            handlerMethod.setSecuritySheet(securitySheet);
            lookUps.put(uri, handlerMethod);
        }

        public CjxHandlerMethod getHandlerMethod(String uri) {
            Assert.notNull(lookUps,String.format("no mapped handlerMothod found in %S",uri));
            return lookUps.get(uri);
        }
    }
}
