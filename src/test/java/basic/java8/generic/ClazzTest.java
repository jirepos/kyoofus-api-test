package basic.java8.generic;

import org.junit.jupiter.api.Test;

/**
 * 클래스 정보를 얻는 방법에 대한 테서트 케이스이다. 
 */
public class ClazzTest {

    /**
     * Class{@literal <?>} Test
     * ?는 모든 종류의 클래스든 다 받는다.
     * 
     */
    @Test
    public void clazzTest() {
        String name = new String("Latte");
        // Class<?>는 어떤 종류의 클래스든 다 받아 들인다는 뜻이다.
        Class<?> clazz = name.getClass();
        System.out.println(clazz.getName());
    }// :

    /**
     * 클래스 리터럴은 String.class, Integer.class 등을 말한다.
     * String.class의 타입은 Class, Intger.class의 타입은 Class이다.
     * 클래스 리터럴은 타입 토큰으로서 사용된다.
     */
    @Test
    public void clazzTest2() {
        // 모든 클래스에서 내장된 class 스태틱 변수를 통해서 타입정보를 구한다.
        // 클래스 리터럴은 String.class, Integer.class 등을 말한다.
        Class<?> c2 = String.class;

        // Type Token은 간단히 말해 타입을 나타내는 토큰이며 클래스 리터럴이 사용된다.
        // methodA(Class<?> clazz)와 같은 메서드는 타입토큰을 인자로 받는 메서드이다.
        System.out.println(c2.getTypeName());
    }// :



    /**
     * 클래스 정보를 얻는 세가지 방법에 대한 테스트 케이스이다. 
     * @throws Exception
     */
    @Test
    public void classInfoTest() throws Exception  {
        // 인스턴스를 통해서 얻기
        // 인스턴스만 있으면 그 인스턴스가 어떤 클래스의 객체인지 쉽게 알아낼 수 있다.
        String obj = new String("jirepos");
        Class<?> c1 = obj.getClass(); // <?> : 어떤 종류의 클래스든 다 얻어올 수 있다
        System.out.println(c1.getName());

        // class.forName() 메서드를 통해서 얻기
        // 인자가 가리키는 문자열이 없을 수 있기 때문에 예외처리가 필요
        // 클래스 이름을 문자열로 받을 수 있기 때문에 유지보수가 쉽다.
        Class<?> c2 = Class.forName("java.lang.String");
        System.out.println(c2.getName());

        // 모든 클래스에 내장된 "class" 스태틱 변수를 통해서 얻기
        // 클래스 이름을 코드로 명시하기 때문에 유지보수가 어렵다.
        Class<?> c3 = String.class;
        System.out.println(c3.getName());
    }//:



    /**
     * 1.타입을 입력 받는 인터페이스를 정의한다. 
     */
    static interface HandlerInterface<T> {
        String getValue();
    }
    /**
     * 2.Sring type을 입력 받는 인터페이스를 구현하는 클래스를 정의한다. 
     */
    static class MyHandler  implements HandlerInterface<String> {
        private String val;
        public MyHandler(String val) {
            this.val = val;
        }
        @Override
        public String  getValue() {
            return this.val;
        }
    }
    
    /**
     * 3.String Type을 입력받는 MYhandler를 반환하는 메서드를 정의한다. 
     */
    static class MyConverter {
        public static HandlerInterface<String> getHandler() {
            return new MyHandler("jirepos");
        }
    }

    /**
     * 4.HandlerInterface{@literal <?>} 변수를 정의한다. 어떤 종류의 타입도 허용된다. 
     * getHandler()로 인스턴스를 반환받고 interface의 getVale()를 호출한다. 
     */
    @Test
    public void testIntfWild(){ 
        // ?를 사용하여 인스턴스를 참조하도록 한다. 
        HandlerInterface<?> handler = MyConverter.getHandler();
        // 객체는 HandlerInterface를 구현했기 때문에  getValue()를 호출할 수 있다. 
        System.out.println(handler.getValue());
    }//:


}///~
