package cn.cjx.component;

import cn.cjx.annotation.CjxController;
import cn.cjx.annotation.CjxRequestMapping;
import cn.cjx.utils.SpringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/7 17:50
 * @创建人:陈俊旋
 */
@Component
public class HandlerMethodMapping implements InitializingBean {
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

    @Override
    public void afterPropertiesSet() throws Exception {
        initHandlerMethods();
    }

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
        ApplicationContext context = SpringUtils.getContext();
        Class<?> clazz = context.getType(beanName);
        if (clazz!=null && isHandler(clazz)){
            doInitHandlerMethodMapping(clazz);
        }
    }

    private void doInitHandlerMethodMapping(Class<?> clazz) {
        ApplicationContext context = SpringUtils.getContext();
        CjxRequestMapping typeRequestMapping = AnnotationUtils.findAnnotation(clazz, CjxRequestMapping.class);
        String[] headPath = null;
        // 处理类上的requestMapping
        if (null!=typeRequestMapping){
            headPath = getRequestMappingPath(typeRequestMapping);
        }
        // 处理method上的requestMapping
        for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {
            Method method = clazz.getDeclaredMethods()[i];
            CjxRequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(method, CjxRequestMapping.class);
            String[] methodPath = getRequestMappingPath(methodRequestMapping);
            for (int j = 0; j < methodPath.length; j++) {
                String methodUri = methodPath[j];
                if (headPath!=null){
                    for (int k = 0; k < headPath.length; k++) {
                        String headUri = headPath[k];
                        mappingRegistry.regist(headUri+methodUri,clazz,method,context);
                    }
                }else {
                    mappingRegistry.regist(methodUri,clazz,method,context);
                }
            }
        }
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

    public CjxHandlerMethod getHandlerMethod(String uri) {
        return mappingRegistry.getHandlerMethod(uri);
    }
    /**
     * 获取所有工厂中的beanNames
     * @return String[]
     */
    private String[] getCandidateBeanNames() {
        ApplicationContext context = SpringUtils.getContext();
        return context.getBeanNamesForType(Object.class);
    }

    private class MappingRegistry {
        Map<String, CjxHandlerMethod> lookUps;
        public MappingRegistry() {
            lookUps = new HashMap<>();
        }

        public void regist(String uri, Class<?> clazz,Method method, ApplicationContext context) {
            if (lookUps==null){
                lookUps = new HashMap<>();
            }
            Assert.notNull(context,"ApplicationContext is can not be null!");
            CjxHandlerMethod handlerMethod = new CjxHandlerMethod();
            handlerMethod.setClassName(clazz.getName());
            handlerMethod.setInstance(context.getBean(clazz));
            handlerMethod.setClazz(clazz);
            handlerMethod.setMethod(method);
            lookUps.put(uri, handlerMethod);
        }

        public CjxHandlerMethod getHandlerMethod(String uri) {
            Assert.notNull(lookUps,String.format("no mapped handlerMothod found in %S",uri));
            return lookUps.get(uri);
        }
    }
}
