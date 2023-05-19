package basic.java8.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

/**
 * Annotation Reflection 테스트케이스이다.
 */
public class ReflectionAnnotationTest {
    /** class에 Annotation 적용 */
    @ReflectionTestAnno(name = "ReflectionTestAnno", value = "ReflectionTestAnno Value")
    static class MyClass {
        /** 필드에 어노테이션 적용 */
        @ReflectionTestAnno(name = "ReflectionTestAnno", value = "ReflectionTestAnno Value")
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

        /** method에 annotation 적용 */
        @ReflectionTestAnno(name = "ReflectionTestAnno", value = "ReflectionTestAnno Value")
        public void doSomething() {
            System.out.println("doSomething");
        }

        /** parameter에 annotation 적용 */
        public static void doSomethingElse(
                @ReflectionTestAnno(name = "ReflectionTestAnno", value = "aValue") String parameter) {
            System.out.println("doSomethingElse");
        }
    }

    // https://jenkov.com/tutorials/java-reflection/annotations.html
    /**
     * Class Annotation 테스트 케이스이다.
     */
    @Test
    public void testClassAnnotation() {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
            if (annotation instanceof ReflectionTestAnno) { // 원하는 Annotation의 인스턴스인지 확인
                ReflectionTestAnno myAnnotation = (ReflectionTestAnno) annotation;
                System.out.println("name: " + myAnnotation.name()); // Annotation의 name의 값을 가져온다.
                System.out.println("value: " + myAnnotation.value()); // Annotation의 value의 값을 가져온다.
            }
        }
    }// :

    /**
     * Class Annotation 테스트 케이스이다.
     */
    @Test
    public void testClassAnnotation2() {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        // Annotation class 를 파라미터로 전달하여 어노테이션을 구한다. 
        Annotation annotation = aClass.getAnnotation(ReflectionTestAnno.class);
        if (annotation instanceof ReflectionTestAnno) { // 인스턴스이면 
            ReflectionTestAnno myAnnotation = (ReflectionTestAnno) annotation;
            System.out.println("name: " + myAnnotation.name());
            System.out.println("value: " + myAnnotation.value());
        }
    }


    /**
     * 메서드에 적용된 어노테이션을 가져온다. 
     * @throws Exception
     */
    @Test
    public void testMethodAnnotation() throws Exception {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        Method method = aClass.getMethod("doSomething");  // 메서드를 구한다.
        Annotation[] annotations = method.getDeclaredAnnotations(); // 적용된 어노테이션 배열을 구한다. 
        for (Annotation annotation : annotations) {
            if (annotation instanceof ReflectionTestAnno) { // 인스턴스이면 
                ReflectionTestAnno myAnnotation = (ReflectionTestAnno) annotation;
                System.out.println("name: " + myAnnotation.name());
                System.out.println("value: " + myAnnotation.value());
            }
        }
    }// :

    /**
     * 메서드에 적용된 어노테이션을 가져온다. 
     * @throws Exception
     */
    @Test
    public void testMethodAnnotation2() throws Exception {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        Method method = aClass.getMethod("doSomething");
        // 어노테이션의 타입정보를 파라미터로 전달하여 어노테이션을 구한다.
        Annotation annotation = method.getAnnotation(ReflectionTestAnno.class);
        if (annotation instanceof ReflectionTestAnno) {
            ReflectionTestAnno myAnnotation = (ReflectionTestAnno) annotation;
            System.out.println("name: " + myAnnotation.name());
            System.out.println("value: " + myAnnotation.value());
        }
    }// :


    /**
     * 메서드 파라미터에 정의된 어노테이션을 구한다. 
     * @throws Exception
     */
    @Test
    public void testParameterAnnotation() throws Exception {
        Class<?> aClass = MyClass.class; //obtain Class object. See prev. section
        Method method = aClass.getMethod("doSomethingElse", String.class);
        // 2차원 배열로 파라미터에 적용된 어노테이션을 구한다. 
        // 파라미터도 n개, 파라미터에 적용된 어노테이션도 n개이므로 2차원 배열로 구한다.
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();
        int i=0;
        for(Annotation[] annotations : parameterAnnotations){
            Class<?> parameterType = parameterTypes[i++];
            for(Annotation annotation : annotations){
                if(annotation instanceof ReflectionTestAnno){
                    ReflectionTestAnno myAnnotation = (ReflectionTestAnno) annotation;
                    System.out.println("param: " + parameterType.getName());
                    System.out.println("name : " + myAnnotation.name());
                    System.out.println("value: " + myAnnotation.value());
                }
            }// for
        } // for
    }// :

     /**
     * 필드에 적용된 어노테이션을 가져온다. 
     * @throws Exception
     */
    @Test
    public void testFieldAnnotation() throws Exception {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        // 필드를 구한다.
        Field field = aClass.getField("name");
        Annotation[] annotations = field.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof ReflectionTestAnno){
                ReflectionTestAnno myAnnotation = (ReflectionTestAnno) annotation;
                System.out.println("name: " + myAnnotation.name());
                System.out.println("value: " + myAnnotation.value());
            }
        }
    }// :

         /**
     * 필드에 적용된 어노테이션을 가져온다. 
     * @throws Exception
     */
    @Test
    public void testFieldAnnotation2() throws Exception {
        Class<?> aClass = MyClass.class; // obtain Class object. See prev. section
        // 필드를 구한다.
        Field field = aClass.getField("name");
        Annotation annotation = field.getAnnotation(ReflectionTestAnno.class);
        if(annotation instanceof ReflectionTestAnno){
            ReflectionTestAnno myAnnotation = (ReflectionTestAnno) annotation;
            System.out.println("name: " + myAnnotation.name());
            System.out.println("value: " + myAnnotation.value());
        }
    }// :
    

}///~
