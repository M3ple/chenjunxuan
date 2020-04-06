package cn.cjx.mybatis.entity;

import java.io.InputStream;

/**
 * @功能描述:
 * @使用对象:xx系统
 * @创建日期: 2020/3/29 0029 23:19
 * @创建人:陈俊旋
 */
public class Resources {
    public static InputStream getResourceAsSteam(String path){
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
