package cn.cjx.component;

import cn.cjx.utils.SpringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/8 0008 21:26
 * @创建人:陈俊旋
 */
@Component
public class HandlerInterceptorMapping implements ApplicationContextAware {

    private CjxHandlerInterceptorRegistry interceptorRegistry;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        CjxWebMvcConfigurer mvcConfigurer = context.getBean(CjxWebMvcConfigurer.class);
        interceptorRegistry = new CjxHandlerInterceptorRegistry();
        interceptorRegistry.regist(mvcConfigurer.getHandlerInterceptorWrappers());
    }

    public List<CjxHandlerInterceptorWrapper> getInterceptorWrappers() {
        return interceptorRegistry.getInterceptors();
    }

    private class CjxHandlerInterceptorRegistry {
        private List<CjxHandlerInterceptorWrapper> lookUps;
        public CjxHandlerInterceptorRegistry() {
            lookUps = new ArrayList<>();
        }

        public List<CjxHandlerInterceptorWrapper> getInterceptors() {
            return lookUps;
        }

        public void regist(List<CjxHandlerInterceptorWrapper> handlerInterceptors) {
            lookUps.addAll(handlerInterceptors);
        }
    }

    public void init() {
        CjxWebMvcConfigurer configurer = SpringUtils.getBean(CjxWebMvcConfigurer.class);
        configurer.initInterceptors();
        interceptorRegistry.regist(configurer.getHandlerInterceptorWrappers());
    }
}
