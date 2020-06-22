package cn.cjx.mybatis.config;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/24 0024 23:48
 * @创建人:陈俊旋
 */
@Data
public class Configuration {
    private DataSource dataSource;
    private Map<String, MappedStatement> mappedStatementMap;

    public Configuration() {
        this.mappedStatementMap = new HashMap<>();
    }
}
