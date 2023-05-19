package basic.java8.enumclass;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

/**
 * EnumSet을 사용하여 코드 값으로 Enum을 찾는 메소드 테스트
 */
public class EnumSetTest {
  
  public enum Lang {
    C("1", "C"), JAVA("2", "Java"), GO("3", "Other");
    private String code;
    private String name; 
    public String code() {
      return this.code;
    }
    public String langName() {
      return this.name; 
    }
    // EnumSet으로 상수 저장 
    private static EnumSet<Lang> enumSet = null;
    static { 
      enumSet = EnumSet.allOf(Lang.class);
    }
    public static Lang find(String code) {
      // 상수를 Stream을 사용해서 찾기 
      return enumSet.stream().filter(constant -> constant.code().equals(code)).findFirst().orElseGet(() -> null);
    }
    private Lang(String code, String name) {
      this.code = code; 
      this.name = name; 
    }
  }

  @Test 
  public void testEnumSet(){ 
    Lang lang = Lang.find("2");
    System.out.println(lang); // 상수명
    System.out.println(lang.code()); // 상수 코드 값 
  }
}///~
