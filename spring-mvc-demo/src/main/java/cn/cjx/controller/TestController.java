package cn.cjx.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 测试类
 * </p>
 *
 * @author cjx
 * @since 2020-01-09
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("index")
    @ResponseBody
    public String updateStatus(String name) {
        return name;
    }
}
