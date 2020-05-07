package cn.cjx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @功能描述:Freemarker配置
 * @使用对象:demo系统
 * @创建日期: 2019/10/8 10:32
 * @创建人:陈俊旋
 */
@Configuration
public class FreemarkerConfiguration {

    @Autowired
    protected freemarker.template.Configuration configuration;

    /**
     * 添加自定义标签
     */
    @PostConstruct
    public void setSharedVariable() {
        try {
            configuration.setSharedVariable("rootPath", "/mvc");
            configuration.setSharedVariable("staticVersion", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义属性
     */
    @PostConstruct
    public void setSettings() {
        try {
            configuration.setSetting("number_format","##.##");
            configuration.setSetting("whitespace_stripping","true");
            configuration.setSetting("classic_compatible","true");
            configuration.setSetting("template_exception_handler","ignore");
            configuration.setSetting("time_format","HH:mm:ss");
            configuration.setSetting("date_format","yyyy-MM-dd");
            configuration.setSetting("locale","zh_CN");
            configuration.setSetting("output_encoding","UTF-8");
            configuration.setSetting("default_encoding","UTF-8");
            configuration.setSetting("url_escaping_charset","UTF-8");
            configuration.setSetting("datetime_format","yyyy-MM-dd HH:mm:ss");
            configuration.setSetting("template_update_delay","5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
