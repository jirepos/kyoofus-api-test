package basic.external.lombok;


import org.junit.jupiter.api.Test;

/** Lombok Test */
public class LombokTest {
    /** Lombok Builder를 테스트하기 위한 TestCase */
    @Test
    public void builderTest() {
        LombokBean bean = LombokBean.builder()
                                  .name("홍길동")
                                  .age(20)
                                  .build();
        System.out.println(bean.getName());

    }
}///~
