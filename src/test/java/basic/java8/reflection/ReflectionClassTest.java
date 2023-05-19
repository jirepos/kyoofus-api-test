package basic.java8.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

/**
 * Class Reflection  테스트케이스이다. 
 */
public class ReflectionClassTest {
    /** Reflection Test를 위한 inner class */
    @Deprecated
    static class MyClass {
        public String name; 
        public MyClass() {

        }
        public MyClass(String name) {
            this.name = name;
        }
        public String getName(){ 
            return name; 
        }
        public void setName(String name) {
            this.name = name; 
        }
    }
    
    /** 클래스 이름을 구한다. */
    @Test
    public void testClassName() {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        String className = aClass.getName();
        System.out.println(className);  //basic.java8.reflection.RefTestClass$MyClass
    }
    /**
     * Modifiers를 구한다.
     */
    @Test
    public void testModifiers() {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        int modifiers = aClass.getModifiers();
        // Modifier.isAbstract(int modifiers);
        // Modifier.isFinal(int modifiers);
        // Modifier.isInterface(int modifiers);
        // Modifier.isNative(int modifiers);
        // Modifier.isPrivate(int modifiers);
        // Modifier.isProtected(int modifiers);
        // Modifier.isPublic(int modifiers);
        // Modifier.isStatic(int modifiers);
        // Modifier.isStrict(int modifiers);
        // Modifier.isSynchronized(int modifiers);
        // Modifier.isTransient(int modifiers);
        // Modifier.isVolatile(int modifiers);
        System.out.println(Modifier.isPublic(modifiers));
    }
    /** 패키지 정보를 구한다. */
    @Test
    public void testPackage() {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        java.lang.Package pkg = aClass.getPackage();
        System.out.println(pkg.getName());  //basic.java8.reflection
    }
    /** SuperClass를 구한다. */
    @Test 
    public void testSuperClass(){ 
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        Class<?> superClass = aClass.getSuperclass();
        System.out.println(superClass.getName());  //java.lang.Object
    }


    /** 생성자를 구한다. */
    @Test
    public void testConstructor() {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        Constructor<?>[] constructors = aClass.getConstructors();
        for(Constructor<?> constructor : constructors) {
            System.out.println(constructor.getName());  // basic.java8.reflection.RefTestClass$MyClass
        }
    }

    /** Relfection 테스트를 위한 Interface */
    static interface Handle  {
        void deal();
    }
    /** Reflection Test를 위한 class */
    static class HandleImpl implements Handle { 
        public void deal() {
            System.out.println("HandleImpl");
        }
    }

    /** 어떤 인터페이스를 구현했는지를 테스트한다.  */
    @Test 
    public void testInterfaces() {
        Class<?> aClass = HandleImpl.class; //obtain Class object. See prev. section
        Class<?>[] interfaces = aClass.getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            System.out.println(interfaceClass.getName()); // basic.java8.reflection.RefTestClass$Handle
        }
    }

    /** Method를 구한다. */
    @Test 
    public void testMethods(){ 
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        Method[] method = aClass.getMethods();
        for (Method mod : method) {
            System.out.println(mod.getName()); // deal
            // getName
            // setName
            // ...
        }
    }


    /** 필드를 구한다. */
    @Test 
    public void testFidles() {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        Field[] fields = aClass.getFields();

        // private는 출력이 되지 않음
        for (Field field : fields) {
            System.out.println(field.getName()); // $handle
        }
    }

    /** Annotation을 구한다. */
    @Test 
    public void testAnnotation() {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        Annotation[] annotations = aClass.getAnnotations();
        for(Annotation annotation : annotations) {
            System.out.println(annotation.toString()); // @Deprecated
        }
    }

}///~
