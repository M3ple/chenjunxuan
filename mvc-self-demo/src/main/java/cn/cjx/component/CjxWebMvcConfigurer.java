package cn.cjx.component;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/5/8 0008 21:33
 * @创建人:陈俊旋
 */
@Component
public class CjxWebMvcConfigurer {
    private List<CjxHandlerInterceptorWrapper> lookUps;

    public CjxWebMvcConfigurer() {
        lookUps = new ArrayList<>();
    }

    public List<CjxHandlerInterceptorWrapper> getHandlerInterceptorWrappers(){
        return lookUps;
    }

    public CjxWebMvcConfigurer addInterceptors(CjxHandlerInterceptorWrapper wrapper){
        lookUps.add(wrapper);
        return this;
    }

    public void initInterceptors() {
        addInterceptors(new CjxHandlerInterceptorWrapper(new DemoHandlerInterceptor(),"/test/"));
    }
}

