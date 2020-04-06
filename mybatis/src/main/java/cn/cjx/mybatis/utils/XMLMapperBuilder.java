package cn.cjx.mybatis.utils;

import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.config.MappedStatement;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/29 0029 23:24
 * @创建人:陈俊旋
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parse(InputStream in) throws Exception {
        SAXReader sr = new SAXReader();
        Document doc = sr.read(in);
        Element rootElement = doc.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> list = rootElement.selectNodes("select");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType!=null?Class.forName(resultType):null);
            mappedStatement.setParamterType(paramterType!=null?Class.forName(paramterType):null);
            mappedStatement.setSql(sqlText);
            String key = namespace+"."+id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
        return configuration;
    }
}
