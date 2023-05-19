package basic.java8.reflection;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/** Method Reflection 테스트케이스이다. */
public class ReflectionMethodTest {

    
    /** Reflection Test를 위한 inner class */
    @Deprecated
    static class MyClass {
        public String name;

        public MyClass() {
        }

        public MyClass(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String doSomething() {
            return "jirepos";
        }
        public String doAction(String action) {
            return action;
        }
    }
    
    /** Method를 구하고, 파라미터 타입, 리턴타입을 구한다. */
    @Test 
    public void testMethods() throws Exception {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        Method method = aClass.getMethod("doSomething");
        System.out.println(method.getName()); //doSomething
        method = aClass.getMethod("doAction", new Class[]{String.class});
        System.out.println(method.getName()); //doAction
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            System.out.println(parameterType.getName());  //java.lang.String
        }
        Class<?> returnType = method.getReturnType();
        System.out.println(returnType.getName()); //java.lang.String

        MyClass myObj = new MyClass();
        // 객체로부터 메서드를 구한다.
        method = myObj.getClass().getMethod("doAction", new Class[]{String.class});
        Object returnValue = method.invoke(myObj, "parameter-value1"); // 메서드 실행 
        System.out.println(returnValue); //parameter-value1  // 반환값 
    }
    
}
