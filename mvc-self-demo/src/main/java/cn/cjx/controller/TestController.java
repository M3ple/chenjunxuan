package cn.cjx.controller;


import cn.cjx.annotation.CjxController;
import cn.cjx.annotation.CjxRequestMapping;

/**
 * <p>
 * 测试类
 * </p>
 *
 * @author cjx
 * @since 2020-01-09
 */
@CjxController
@CjxRequestMapping("/test")
public class TestController {

    @CjxRequestMapping("index")
    public String updateStatus(String name) {
        return name;
    }
}
