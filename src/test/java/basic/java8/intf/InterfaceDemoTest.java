package basic.java8.intf;

import org.junit.jupiter.api.Test;

public class InterfaceDemoTest {



    /** 인터페이스에 상수슬 사용할 수 있다.  */
    private interface ConsInterface {
        // 인터페이스에서는 public static final 을 생략할 수 있다
        public int MAX = 100; 
        void print();
    }

    /** 상수 테스트 케이스 */
    @Test
    public void testConstInterface() {
        // 익명 구현 객체 
        // 구현 클래스를 만들어 사용하는 것이 일반적이나 
        // 일회성의 구현 책체를 만들기 위해 익명 구현 객체를 사용한다.
        // 형식
        // 인터페이스 변수 = new 인터페이스() {
        //     인터페이스에서 선언된 추상 메서드 구현 
        // };    
        ConsInterface ci = new ConsInterface() {
            /* 인터페이스에서 정의된 메서드를 구현한다.  */
            @Override
            public void print() {
                System.out.println(MAX);
            }
        };
        // 인터페이스 사용 
        ci.print();

        // 다음과 같이 "인터페이스명.상수"로 직접 사용할 수 있다. 
        System.out.println(ConsInterface.MAX);
    }//:


    /** 디폴트 메서드를 사용한다. */
    private interface DefaultInterface {
        String getName();
        // 인터페이스에서는 default 메서드를 정의할 수 있다
        default void print() {
            System.out.println(getName());
        }
    }


    /** default method 테스트 케이스 */
    @Test 
    public void testDefaultMethod() {
        // 익명 구현 객체 
        DefaultInterface di = new DefaultInterface() {
            @Override
            public String getName() {
                return "default";
            }
        };
        // 인터페이스 사용 
        di.print();
    }//:

    /** 정적 메서드를 사용할 수 있다. */
    private interface StaticInterface {
        // 인터페이스에서는 static 메서드를 정의할 수 있다
        static void print() {
            System.out.println("static");
        }
    }

    /** static method 테스트 케이스 */
    @Test
    public void testStaticMethod() {
        // 인터페이스 사용 
        StaticInterface.print();
    }//:

    // https://hyuntaekhong.github.io/blog/java-basic20/

}
