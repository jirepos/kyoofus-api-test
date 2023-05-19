package basic.java8.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/**
 * Getter/Setter를 구한다.
 */
public class ReflectionGetterSettersTest {

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

    /** Getter 확인 */
    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get"))
            return false;
        if (method.getParameterTypes().length != 0)
            return false;
        if (void.class.equals(method.getReturnType()))
            return false;
        return true;
    }

    /** Setter 확인 */
    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set"))
            return false;
        if (method.getParameterTypes().length != 1)
            return false;
        return true;
    }

    /** Getter와 Setters를 구한다. */
    @Test
    public void testGettersSetters() {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            if (isGetter(method))
                System.out.println("getter: " + method);
            if (isSetter(method))
                System.out.println("setter: " + method);
        }

        // getter: public java.lang.String
        // basic.java8.reflection.ReflectionGetterSettersTest$MyClass.getName()
        // setter: public void
        // basic.java8.reflection.ReflectionGetterSettersTest$MyClass.setName(java.lang.String)
        // getter: public final native java.lang.Class java.lang.Object.getClass()

    }

}
