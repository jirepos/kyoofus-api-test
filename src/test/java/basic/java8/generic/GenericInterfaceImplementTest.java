package basic.java8.generic;

import org.junit.jupiter.api.Test;

/** Generic Interface 상속 테스트 */
public class GenericInterfaceImplementTest {

  /**
   * 임의의 타입을 갖는 제네릭 인터페이스
   */
  public static interface Drawer<T> { 
    String draw(T arg);
  }


  /** 인터페이스를 구현한다.  */
  public static class Painter<T> implements Drawer<String> { 
    private T t; 
    
    public Painter(T t) {
      this.t = t; 
    }
    public String getPicture(){ 
      return "Good"; 
    }
    /** draw() 인터페이스 구현 */
    @Override
    public String draw(String arg) {
      String s = (String)arg; 
      String s2 = (String)t;
      return s + s2;
    }
  }//:

  
  /** Generic Interface 구현 테스트 */
  @Test 
  public void testExtendA() {
    Painter<String> painter = new Painter<String>("Kim");
    String result = painter.draw("Hello");
    System.out.println(result);
  }

}///~
