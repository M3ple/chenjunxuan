package cn.cjx.controller;


import cn.cjx.annotation.CjxController;
import cn.cjx.annotation.CjxRequestMapping;
import cn.cjx.annotation.Security;

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
@Security({"lucy","emma"})
public class TestController {

    @CjxRequestMapping("index")
    @Security("emma")
    public String index(String username) {
        return username;
    }
}
