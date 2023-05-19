package basic.java8.reflection;

import java.lang.reflect.Array;

import org.junit.jupiter.api.Test;

/** 배열 Reflection 테스트케이스이다. */
public class ReflectionArraysTest {
    /** 
     * 배열 생성 및 배열 접근 
     */
    @Test
    public void testArray() {
        // 배열 생성
        int[] intArray = (int[]) Array.newInstance(int.class, 3);

        // 배열 초기화
        Array.set(intArray, 0, 123);
        Array.set(intArray, 1, 456);
        Array.set(intArray, 2, 789);

        System.out.println("intArray[0] = " + Array.get(intArray, 0));
        System.out.println("intArray[1] = " + Array.get(intArray, 1));
        System.out.println("intArray[2] = " + Array.get(intArray, 2));
    }

    /**
     * 표기법으로 배열 타입을 구한다. 
     * @throws Exception
     */
    @Test
    public void testArray2() throws Exception {

        Class<?> stringArrayClass = String[].class; // non reflection code to obtain Class object. See prev. section
        System.out.println("stringArrayClass = " + stringArrayClass);

        // I 는 Integer, [ 은 배열
        Class<?> intArray = Class.forName("[I");
        System.out.println("intArray = " + intArray);

        // L은 클래스 이름
        Class<?> stringArrayClass2 = Class.forName("[Ljava.lang.String;");
        System.out.println("stringArrayClass = " + stringArrayClass2);

    }// :



    /**
     * 표기법 오류를 테스트한다. 
     */
    @Test
    public void testClasSForName() throws Exception {
        Class<?> intClass1 = Class.forName("I"); // ClassNotFoundException 발생
        Class<?> intClass2 = Class.forName("int"); // ClassNotFoundException 발생
        System.out.println("intClass1 = " + intClass1);
        System.out.println("intClass2 = " + intClass2);
    }

    /**
     * ComponentType으로 배열요소의 타입을 구한다. 
     * @throws Exception
     */
    @Test
    public void testComponentType() throws Exception {
        String[] strings = new String[3];  // 문자열 배열 생성 
        Class<?> stringArrayClass = strings.getClass();  // 클래스 정보 얻기 
        Class<?> stringArrayComponentType = stringArrayClass.getComponentType(); // 컴포넌트 타입은 배열 아이템의 타입이다. 
        System.out.println(stringArrayComponentType);
    }

}///~
