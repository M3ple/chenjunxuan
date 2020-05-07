package cn.cjx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("cn.cjx.servlet")
public class MvcSelfDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcSelfDemoApplication.class, args);
    }

}
