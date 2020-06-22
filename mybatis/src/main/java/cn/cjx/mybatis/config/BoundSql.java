package cn.cjx.mybatis.config;

import cn.cjx.mybatis.utils.ParameterMapping;
import lombok.Data;

import java.util.List;

@Data
public class BoundSql {

    private String sqlText; //解析过后的sql

    private List<ParameterMapping> parameterMappingList;

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }
}
