package cn.cjx.mybatis;

import cn.cjx.mybatis.config.Configuration;
import cn.cjx.mybatis.utils.XMLConfigerBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

class MainMethodTest {

    public static void main(String[] args) throws Exception {
//        XMLParseTest();
//        stringTest();
        computeIfAbsent();
    }

    public static void XMLParseTest() throws Exception {
//        InputStream in = Resources.getResourceAsSteam("/resources/sqlMapConfig.xml");
        InputStream in = new FileInputStream("D:\\maple-projects\\mybatis-demo\\src\\main\\resources\\sqlMapConfig.xml");
        Configuration conf = new Configuration();
        XMLConfigerBuilder parser = new XMLConfigerBuilder(conf);
        Configuration configuration = parser.parse(in);
        System.out.println(configuration);
    }

    public static void stringTest() {
        String str = "\\#{}";
        int i = str.indexOf("\\", 1);
        System.out.println(i);

        char[] src = str.toCharArray();
        for (int j = 0; j < src.length; j++) {
            char c = src[j];
            System.out.println(c);
        }
    }

    public static void computeIfAbsent() {
        Map<String, String> map = new HashMap<>();
        map.put("a","1");
//        java8之前。从map中根据key获取value操作可能会有下面的操作
//        Object key = map.get("key");
//        if (key == null) {
//            key = new Object();
//            map.put("key", key);
//        }
        map.computeIfAbsent("b",k->"2");
        System.out.println(map.get("b"));
        System.out.println(map);
    }
}
