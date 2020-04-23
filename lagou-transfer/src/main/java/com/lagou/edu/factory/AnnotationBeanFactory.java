package com.lagou.edu.factory;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Component;
import com.lagou.edu.annotation.Transactional;
import com.lagou.edu.enums.ProxyTypeEnum;
import com.lagou.edu.pojo.TransactionManagerStrategy;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author cjx
 *
 * 工厂类，生产对象（使用反射技术）
 */
public class AnnotationBeanFactory {

    /**
     * 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */

    private static final String CLASS_SUFFIX = ".class";
    private static final String CLASS_FILE_PREFIX = File.separator + "classes"  + File.separator;
    private static final String PACKAGE_SEPARATOR = ".";
    private static final String packages = "com.lagou.edu";

    private static final Map<String,BeanDefinition> beanDefinitions = new HashMap<>();
    private static Map<String,Object> map = new HashMap<>();  // 存储对象
    private static Map<String,Object> objects = new HashMap<>();  // 存储实例化bean

    public AnnotationBeanFactory() {
        getAllResourcesAsBeanDefinition();
        processBeanDefinition();
    }

    private void processBeanDefinition(){
        if (null!=beanDefinitions && !beanDefinitions.isEmpty()){
            for (BeanDefinition beanDefinition : beanDefinitions.values()) {
                try {
                    Class<?> clazz = beanDefinition.getClazz();
                    boolean isOriginatedFromAnnotation = isOriginatedFromAnnotation(clazz,Component.class);
                    if (isOriginatedFromAnnotation && !clazz.isInterface() && !clazz.isAnnotation()){
                        Object o = clazz.newInstance();
                        map.put(beanDefinition.getClassSimpleName(), o);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            processAutoWiredBean();
            processTransactionalBean();
        }
    }

    private void processTransactionalBean() {
        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            String beanName = entry.getKey();
            Object bean = entry.getValue();
            BeanDefinition beanDefinition = beanDefinitions.get(bean.getClass().getName());
            if (beanDefinition.getProxyTypeEnum().equals(ProxyTypeEnum.CJLIB)) {
                bean = getProxyFactory().getCglibProxy(bean, beanDefinition.getTransactionManagerStrategy().getMethodList());
            } else {
                bean = getProxyFactory().getJdkProxy(bean, beanDefinition.getTransactionManagerStrategy().getMethodList());
            }
            objects.put(beanName,bean);
        }
    }

    private void processAutoWiredBean(Object bean){
        BeanDefinition beanDefinition = beanDefinitions.get(bean.getClass().getName());
        List<Field> autoWiredFields = beanDefinition.getAutoWiredFields();
        if (autoWiredFields!=null && !autoWiredFields.isEmpty()){
            for (int i = 0; i < autoWiredFields.size(); i++) {
                try {
                    Field field = autoWiredFields.get(i);
                    Class<?> fieldType = field.getType();
                    Object o = getBeanByType(fieldType);
                    processAutoWiredBean(o);
                    field.setAccessible(true);
                    field.set(bean, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void processAutoWiredBean(){
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String beanName = entry.getKey();
            Object bean = entry.getValue();
            processAutoWiredBean(bean);
            objects.put(beanName,bean);
        }
    }

    private Object getBeanByType(Class<?> fieldType) {
        for (Object o : map.values()) {
            if (fieldType.isInterface()){
                if (fieldType.isInstance(o)){
                    return o;
                }
            }else{
                if (fieldType.equals(o.getClass())){
                    return o;
                }
            }
        }
        return null;
    }

    private ProxyFactory getProxyFactory() {
        return (ProxyFactory)getBean("proxyFactory");
    }

    /**
     * 获取相关类型的字段
     * @param clazz
     * @return
     */
    private static List<Field> getAnnotationField(Class<?> clazz, Class<? extends Annotation> annoClazz) {
        List<Field> result = new ArrayList<>();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            boolean hasAnnotation = hasAnnotation(field, annoClazz);
            if (hasAnnotation){
                result.add(field);
            }
        }
        return result;
    }

    /**
     * 字段包含指定注解
     * @param field
     * @param annotationClass
     * @return
     */
    private static boolean hasAnnotation(Field field, Class<? extends Annotation> annotationClass) {
        Annotation[] annotations = field.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            if (annotation.annotationType().equals(annotationClass)
                    || annotation.annotationType().isAssignableFrom(annotationClass)){
                return true;
            }
        }
        return false;
    }

    /**
     * 包含指定annotation
     * @param clazz
     * @param componentClass
     * @return
     */
    private static boolean isOriginatedFromAnnotation(Class<?> clazz, Class<? extends Annotation> componentClass) {
        for (int i = 0; i < clazz.getAnnotations().length; i++) {
            Annotation annotation = clazz.getAnnotations()[i];
            if (annotation.annotationType().equals(componentClass)
                    || annotation.annotationType().isAnnotationPresent(componentClass)){
                return true;
            }
        }
        return false;
    }

    /**
     * 解析packages路径下的所有文件
     * @throws IOException
     */
    private void getAllResourcesAsBeanDefinition() {
        try {
            String path = "";
            if (null!=packages){
                path = packages.replaceAll("[.]","/")+"/";
            }
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String filePath = url.getFile();
                resolveAllResourcesAsBeanDefinition(filePath,beanDefinitions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装beanDefinition
     * @param path
     * @param beanDefinitions
     */
    public void resolveAllResourcesAsBeanDefinition(String path,Map<String,BeanDefinition> beanDefinitions) {
        File targetFile = new File(path);
        if (targetFile.isDirectory()){
            for (File file : listDirectory(targetFile)) {
                resolveAllResourcesAsBeanDefinition(file.getAbsolutePath(),beanDefinitions);
            }
        }else {
            try {
                String fullClassName = getFullClassName(targetFile);
                if (fullClassName != null) {
                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setClassName(fullClassName);
                    beanDefinition.setClassSimpleName(targetFile.getName().replace(CLASS_SUFFIX, "").toLowerCase());
                    // 获取自动状态属性
                    Class<?> clazz = Class.forName(beanDefinition.getClassName());
                    beanDefinition.setClazz(clazz);
                    beanDefinition.setAutoWiredFields(getAnnotationField(clazz, Autowired.class));
                    beanDefinition.setProxyTypeEnum(getProxyTypeEnum(clazz, Component.class));
                    beanDefinition.setTransactionManagerStrategy(getTransactionManagerStrategy(clazz));
                    beanDefinitions.put(beanDefinition.getClassName(),beanDefinition);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private ProxyTypeEnum getProxyTypeEnum(Class<?> clazz, Class<Component> componentClass) {
        Component component = clazz.getAnnotation(componentClass);
        if (component!=null){
            return component.proxyType();
        }
        return ProxyTypeEnum.CJLIB;
    }

    /**
     * 获取事务策略
     * @param clazz
     * @return
     */
    private TransactionManagerStrategy getTransactionManagerStrategy(Class<?> clazz) {
        TransactionManagerStrategy strategy = new TransactionManagerStrategy();
        Transactional annotation = clazz.getAnnotation(Transactional.class);
        if (annotation!=null){
            strategy.setScope(TransactionManagerStrategy.TYPE_SCOPE);
        }else {
            List<String> methodList = new ArrayList<>();
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                Method method = declaredMethods[i];
                annotation = method.getAnnotation(Transactional.class);
                if (annotation!=null){
                    strategy.setScope(TransactionManagerStrategy.METHOD_SCOPE);
                    methodList.add(method.getName());
                }
            }
            strategy.setMethodList(methodList);
        }
        return strategy;
    }

    public static String getFullClassName(File file) {
        String path = file.getPath();
        // 注意：这里替换文件分割符要用replace。因为replaceAll里面的参数是正则表达式,而windows环境中File.separator="\\"的,因此会有问题
        if(path.endsWith(CLASS_SUFFIX)) {
            path = path.replace(CLASS_SUFFIX, "");
            // 从"/classes/"后面开始截取
            String clazzName = path.substring(path.indexOf(CLASS_FILE_PREFIX) + CLASS_FILE_PREFIX.length())
                    .replace(File.separator, PACKAGE_SEPARATOR);
            if(-1 == clazzName.indexOf("$")) {
                return clazzName;
            }
        }
        return null;
    }
    /**
     * Determine a sorted list of files in the given directory.
     * @param dir the directory to introspect
     * @return the sorted list of files (by default in alphabetical order)
     * @since 5.1
     * @see File#listFiles()
     */
    protected static File[] listDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return new File[0];
        }
        Arrays.sort(files, Comparator.comparing(File::getName));
        return files;
    }

    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public Object getBean(String beanName) {
        if (null!=beanName){
            return objects.get(beanName.toLowerCase());
        }
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {
//        Class<?> clazz = Class.forName("com.lagou.edu.annotation.Component");
//        Class<?> clazz2 = Class.forName("com.lagou.edu.annotation.Service");
//        System.out.println(clazz.isAnnotationPresent(Component.class));
//        System.out.println(clazz2.isAnnotationPresent(Component.class));
//        System.out.println(clazz.isAssignableFrom(clazz2));
//        System.out.println(clazz2.isAssignableFrom(clazz));
        new AnnotationBeanFactory();
    }

    public Object getBean(Class<?> clazz) {
        if (null!=clazz){
            for (Object o : objects.values()) {
                if (clazz.isInterface()){
                    if (clazz.isInstance(o)){
                        return o;
                    }
                }else{
                    if (clazz.equals(o.getClass())){
                        return o;
                    }
                }
            }
        }
        return null;
    }
}
