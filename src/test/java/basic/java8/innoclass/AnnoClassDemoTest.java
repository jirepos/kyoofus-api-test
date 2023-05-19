package basic.java8.innoclass;

import org.junit.jupiter.api.Test;

/**
 * 익명 클래스 데모 테스트 케이스이다. 
 */
public class AnnoClassDemoTest {

    /** 보통의 클래스를 선언한다.  */
    private static class AnnoDemo { 
        public void print() {
            System.out.println("anno");
        }
    }

    // https://limkydev.tistory.com/226
    /** 익명 클래스 생성 테스트이다. */
    @Test
    public void testNewAnno() {
        // 익명 클래스 생성
        AnnoDemo anno = new AnnoDemo() {
            /** 새로운 메서드를 생성할 수 있다.  */
            public void print2() {
                System.out.println("anno2");
            }
            /* print()를 오버라이드 한다.  */
            @Override
            public void print() {
                System.out.println("anno child");
            }
        };
        anno.print();
        //anno.print2(); // 컴파일 오류 발생
    }



    /** 인터페이스 */
    private interface HelloInterface {
        void hello();
    }
    /** 인터페이스를 구현하지 않는 추상 클래스 */
    private static abstract class HelloAbstract implements HelloInterface {
    }
    @Test
    public void testAbstract(){ 
        // 익명 클래스로 추상 클래스를 생성한다
        HelloAbstract hello = new HelloAbstract() {
            @Override
            public void hello() {
                System.out.println("hello");
            }
        };
        hello.hello();
    }


    private static class DemoTestExcption  { 
        public static RuntimeException BASE_ERRROR = new RuntimeException("base error");
    }

    @Test
    public void testThrowException() {
        boolean flag = true; 
        if(flag) {
            try {
                throw DemoTestExcption.BASE_ERRROR;
            } catch (Exception e) {
                System.out.println("catch1");
            }
            
        }
        if(flag) {
            try {
                throw DemoTestExcption.BASE_ERRROR;
            } catch (Exception e) {
                System.out.println("catch2");
            }
            
        }


    }






}///~
