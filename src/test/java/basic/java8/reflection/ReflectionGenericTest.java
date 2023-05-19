package basic.java8.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Generic Type reflection 테스트케이스이다.
 */
public class ReflectionGenericTest {

    static class MyClass {

        public List<String> stringList = new ArrayList<String>();

        public List<String> getStringList() {
            return this.stringList;
        }

        public void setStringList(List<String> list) {
            this.stringList = list;
        }
    }

    /**
     * Generic return type을 구한다.
     */
    @Test
    public void testGenericReturnType() throws Exception {
        Method method = MyClass.class.getMethod("getStringList"); // 메서드를 구한다.
        Type returnType = method.getGenericReturnType(); // 반환 값 타입 구한다.

        if (returnType instanceof ParameterizedType) {
            // genericType을 구하려면 java.lang.reflect.ParameterizedType을 사용한다. 
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments(); // 제네릭 타입의 인자들을 구한다.
            for (Type typeArgument : typeArguments) {
                Class<?> typeArgClass = (Class<?>) typeArgument;
                System.out.println("typeArgClass = " + typeArgClass);
            }
        }
    }// :

    /**
     * Generic 파라미터 타입을 구한다.
     */
    @Test
    public void testGenericMethodParameterType() throws Exception {
        Method method = MyClass.class.getMethod("setStringList", List.class);
        Type[] genericParameterTypes = method.getGenericParameterTypes(); // 제네릭 파라미터 타입을 구한다.

        for (Type genericParameterType : genericParameterTypes) {
            if (genericParameterType instanceof ParameterizedType) {
                ParameterizedType aType = (ParameterizedType) genericParameterType;
                Type[] parameterArgTypes = aType.getActualTypeArguments();
                for (Type parameterArgType : parameterArgTypes) {
                    Class<?> parameterArgClass = (Class<?>) parameterArgType;
                    System.out.println("parameterArgClass = " + parameterArgClass);
                }
            }
        }
    }// :

    /** Generic Field Type을 구한다. */
    @Test
    public void testGenericFieldType() throws Exception {
        Field field = MyClass.class.getField("stringList"); // 필드를 구한다.
        Type genericFieldType = field.getGenericType(); // generic type을 구한다. 

        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                Class<?> fieldArgClass = (Class<?>) fieldArgType;
                System.out.println("fieldArgClass = " + fieldArgClass);
            }
        }
    }


}/// ~
