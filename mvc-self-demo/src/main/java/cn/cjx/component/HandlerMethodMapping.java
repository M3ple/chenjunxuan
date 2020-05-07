package cn.cjx.component;

import cn.cjx.annotation.CjxController;
import cn.cjx.annotation.CjxRequestMapping;
import cn.cjx.utils.SpringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

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

    /**
     * 获取所有工厂中的beanNames
     * @return String[]
     */
    private String[] getCandidateBeanNames() {
        ApplicationContext context = SpringUtils.getContext();
        return context.getBeanNamesForType(Object.class);
    }
}
