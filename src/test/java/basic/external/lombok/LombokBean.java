package basic.external.lombok;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** Lombok Builder를 테스트하기 위한 Bean */
@Getter
@Setter
@Builder
public class LombokBean {
    private String name;
    private int age;
}
