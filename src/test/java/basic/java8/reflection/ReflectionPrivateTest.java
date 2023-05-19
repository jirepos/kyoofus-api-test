package basic.java8.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/** private 메서드 실행 테스트케이스이다. */
public class ReflectionPrivateTest {

    static class PrivateObject {

        private String privateString = null;

        public PrivateObject(String privateString) {
            this.privateString = privateString;
        }

        private String getPrivateString() {
            return this.privateString;
        }
    }

    /**
     * private 필드 값을 구한다.
     * @throws Exception
     */
    @Test
    public void testPrivateField() throws Exception {
        PrivateObject privateObject = new PrivateObject("The Private Value");
        Field privateStringField = PrivateObject.class.getDeclaredField("privateString"); // privateString field
        privateStringField.setAccessible(true);  // privateString field 접근 가능하게 한다.
        String fieldValue = (String) privateStringField.get(privateObject);// 필드 값을 구한다.
        System.out.println("fieldValue = " + fieldValue); // fieldValue = The Private Value
    }

    /**
     * private 메서드를 실행한다.
     * 
     * @throws Exception
     */
    @Test
    public void testPrivateMethod() throws Exception {
        PrivateObject privateObject = new PrivateObject("The Private Value");
        Method privateStringMethod = PrivateObject.class.getDeclaredMethod("getPrivateString"); // 메서드 구한다.
        privateStringMethod.setAccessible(true); // 메서드 접근 가능하게 한다.
        String returnValue = (String) privateStringMethod.invoke(privateObject); // 메서드 실행
        System.out.println("returnValue = " + returnValue); // returnValue = The Private Value
    }

}
