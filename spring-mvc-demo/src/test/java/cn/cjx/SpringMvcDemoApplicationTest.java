package cn.cjx;

import cn.cjx.annotation.AliasForTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringMvcDemoApplication.class})
//互为别名的属性只能存在一个
//@ContextConfiguration(value = "classpath:aa.xml", locations = "classpath:aa.xml")//失败打印
//@ContextConfiguration(value = "classpath:aa.xml", locations = "classpath:aa.xml")//成功打印
//@ContextConfiguration(value = "classpath:aa.xml")//成功打印
//@ContextConfiguration(locations = "classpath:aa.xml")//成功打印
@AliasForTest(name = "a",profile = "a")
public class SpringMvcDemoApplicationTest {

    @Test
    public void contextLoads() {

    }

    @Test
    public void testAliasfor() {
        ContextConfiguration cc = AnnotationUtils.findAnnotation(getClass(),
                ContextConfiguration.class);
        System.out.println(
                StringUtils.arrayToCommaDelimitedString(cc.locations()));
        System.out.println(StringUtils.arrayToCommaDelimitedString(cc.value()));
    }

    @Test
    public void arrayToCommaDelimitedString() {
        Integer[] ints = {1, 2, 3, 6};
        System.out.println(StringUtils.arrayToCommaDelimitedString(ints));
    }

    @Test
    public void testAliasfor2() {
        AliasForTest aft = AnnotationUtils.findAnnotation(getClass(),
                AliasForTest.class);
        System.out.println(aft.name());
        System.out.println(aft.profile());
    }
}
