package basic.java8.of;

import java.util.Optional;

import org.junit.jupiter.api.Test;



/** Static Factory Method Test  */
public class OfDemoTest {
    // of는 정적 팩토리 메서드에 사용되는 일반적인 명명 규칙이다 
    // valueOf : 대략적으로 말하면 매개변수와 동일한 값을 가진 인스턴스를 반환합니다. 이러한 정적 팩토리는 효과적인 유형 변환 방법입니다.
    // of : EnumSet에 의해 대중화된 valueOf의 간결한 대안
    // "of"는 영어 단어 "of"를 의미하며 약어가 아닙니다


    /** Optional에서 정적팩터리 메서드 of 사용 */
    @Test 
    public void testOptional(){ 
        // 내부적으로 new를 통해서 객체를 생성 
        Optional<String> optional = Optional.of("Optional");
        System.out.println(optional.get());
    }

    // java static factory method naming convention 
    // from : 하나의 매개변수를 받아서 인스턴스를 생성합니다.
    // of : 여러개의 매개변수를 받아서 인스턴스를 생성
    // instance or getInstance:  인스턴스를 반환. 싱글톤 패턴에서 많이 사용하지만, 동일한 인스턴스인지는 보장하지 않음 
    // create or newInstance :  매번 새로운 인스턴스를 생성 
    // getXxxx : getInstance와 같으나 호출하는 클래스와 다른 타입의 인스턴스를 반환할 때 사용 


    /** 정적 팩터리 메서드 예제를 위한 클래스 */
    private static class  DemoOf { 
        private String name; 

        public String getName() {
            return name; 
        }
        public void setName(String name) {
            this.name = name;
        }
        /** Static Factory Method  */
        public static DemoOf from(String name) {
            DemoOf demoOf = new DemoOf();
            demoOf.setName(name);
            return demoOf;
        }
    }

    /** 정적 팩터리 메서드를 사용하여 인스턴스 생성하는 테스트 */
    @Test
    public void testDemoOf(){ 
        DemoOf demoOf = DemoOf.from("DemoOf");
        System.out.println(demoOf.getName());
    }



    
}///~
