package cn.cjx.mybatis.utils;

import lombok.Data;

@Data
public class ParameterMapping {

    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }
}
