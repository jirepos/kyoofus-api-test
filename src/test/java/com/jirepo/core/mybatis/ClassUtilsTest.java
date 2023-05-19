package com.jirepo.core.mybatis;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.TypeHandler;
import org.junit.jupiter.api.Test;

import com.jirepo.core.json.JsonUtils;
import com.jirepo.core.util.ClassUtils;
import com.jirepo.core.util.IoUtils;


/**
 * {@link ClassLoader#loadClass(String)}를 테스트하기 위한 클래스이다.
 */
public class ClassUtilsTest {

    /**
     * 클래스 생성 테스트 케이스이다. 
     */
    @Test 
    public void testCreate2() throws Exception {
        String className = "com.jirepo.core.mybatis.HandlerTestBean";
        ClassLoader classLoader = ClassUtilsTest.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass(className.trim());
        Constructor<?> constructor = aClass.getConstructor();
        HandlerTestBean bean  = (HandlerTestBean)constructor.newInstance();
        bean.setAge("10");
        System.out.println(bean.getAge());
    }
    
    /**
     * inner class 생성 테스트 케이스이다. 
     * @throws Exception
     */
    @Test 
    public void testCreate() throws Exception {
        String className = "com.jirepo.core.mybatis.HandlerTestBean$Handler";
        ClassLoader classLoader = ClassUtilsTest.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass(className.trim());
        Constructor<?> constructor = aClass.getConstructor();
        Object obj = constructor.newInstance();
        HandlerTestIntf handler = (HandlerTestIntf)obj; 
        System.out.println(handler.getName());
    }

    
    /**
     * ClassUtils의 createInstance()를 테스트하기 위한 테스트 케이스이다.
     * @throws Exception
     */
    @Test
    public void testCreate3() throws Exception {
        String className = "com.jirepo.core.mybatis.HandlerTestBean$Handler";
        HandlerTestIntf bean  = (HandlerTestIntf)ClassUtils.createInstance(className);
        System.out.println(bean.getName());
    }


    /** src/main/resources/enum-handlers.json 파일을 읽어 클래스 인스턴스를 생성하는 테스트이다. */
    @Test 
    public void testCreate4() {
        String jsonString = IoUtils.readFileClasspathToString("enum-handlers.json", "utf-8");
        System.out.println(jsonString);
        List<String> list = JsonUtils.toList(jsonString, String.class);
        list.forEach(className -> {
            System.out.println(className);
            HandlerTestIntf bean  = (HandlerTestIntf)ClassUtils.createInstance(className);
            System.out.println(bean.getName());
        });
    }

    @Test 
    public void testCreate5() {
        String jsonString = IoUtils.readFileClasspathToString("enum-handlers.json", "utf-8");
        //System.out.println(jsonString);
        List<String> list = JsonUtils.toList(jsonString, String.class);
        List<Object> handlers = new ArrayList<Object>();
        list.forEach(className -> {
            //System.out.println(className);
            Object bean  = ClassUtils.createInstance(className);
            //System.out.println(bean.getClass().getName());
            handlers.add(bean);
        });

        TypeHandler<?>[] typeHandlers = handlers.toArray(new TypeHandler[handlers.size()]);
        for(int i=0; i < typeHandlers.length; i++) {
            System.out.println(typeHandlers[i].getClass().getName());
        }
    }
}
