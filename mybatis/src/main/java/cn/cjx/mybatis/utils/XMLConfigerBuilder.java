package cn.cjx.mybatis.utils;

import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.entity.Resources;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/29 0029 23:24
 * @创建人:陈俊旋
 */
public class XMLConfigerBuilder {

    private Configuration configuration;

    public XMLConfigerBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parse(InputStream in) throws Exception {
        SAXReader sr = new SAXReader();
        Document doc = sr.read(in);
        Element rootElement = doc.getRootElement();
        //-------------------------------dataSource-------------------------------
        Element dataSourceElement = rootElement.element("dataSource");
        List<Element> propertyList = dataSourceElement.selectNodes("property");
        Properties properties = new Properties();
        for (Element element : propertyList) {
            properties.setProperty(element.attributeValue("name"),element.attributeValue("value"));
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);
        //-------------------------------dataSource-------------------------------
        List<Element> list = rootElement.selectNodes("mapper");
        for (int i = 0; i < list.size(); i++) {
            Element element = list.get(i);
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsSteam);
        }
        return configuration;
    }
}
