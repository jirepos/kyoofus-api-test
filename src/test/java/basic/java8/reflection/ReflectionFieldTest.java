package basic.java8.reflection;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;


/** Relfection Field 테스트케이스이다. */
public class ReflectionFieldTest {

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
    }

    /** 필드를 구한다. */
    @Test
    public void testFidles() throws Exception {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        Field[] fields = aClass.getFields();
        // private는 출력이 되지 않음
        for (Field field : fields) {
            System.out.println(field.getName()); // name
        }

        // 필드를 구한다.
        Field field = aClass.getField("name");
        Object fieldType = field.getType(); // 필드 타입 
        System.out.println(fieldType); // class java.lang.String

        MyClass myObj  = new MyClass("jirepos"); // 인스턴스 생성 
        Object value = field.get(myObj);  // 필드 값을 구한다. 
        System.out.println(value.toString()); // 필드값 출력  , jirepos
        field.set(myObj, "jirepos2"); // 필드 값을 설정한다.
        System.out.println(field.get(myObj).toString()); // 필드 값 출력, jirepos2
    }

}
