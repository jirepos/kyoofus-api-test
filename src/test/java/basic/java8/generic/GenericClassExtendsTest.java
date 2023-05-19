package basic.java8.generic;

import org.junit.jupiter.api.Test;

/** 두 개의 타입을 갖는  클래스 테스트  */
public class GenericClassExtendsTest {

  /**
   * 두 개의 타입을 갖는 제네릭 클래스
   */
  static class MyClass<K, V> {	
    private K first;	// K 타입(제네릭)
    private V second;	// V 타입(제네릭) 
    
    void set(K first, V second) {
      this.first = first;
      this.second = second;
    }
    
    K getFirst() {
      return first;
    }
    
    V getSecond() {
      return second;
    }
    /** 제네릭 메소드의 K 타입은 클래스의 K 타입과는 다른 타입이다. */
    static <K> K genericMethod(K o) {
      return o; 
    }
  }


  /** 
   * MyClass<K,V> 테스트 케이스이다.
   */
  @Test
  public void testGenericClass() {

    // String type 두 개를 갖는 MyClass<String, String> 클래스를 생성한다.
    MyClass<String, String> myClass = new MyClass<String, String>();
    myClass.set("Hong", "Park");
    String first = myClass.getFirst();
    String second = myClass.getSecond();
    System.out.println(first);
    System.out.println(second);


    // static 변수, static 메소드 등 static이 붙은 것들은 프로그램 실행 시에 메모리에 이미 올라간다는 의미다.
    // Generic Method 의 K 타입은 클래스의 K 타입과는 다른 타입이다.
    // Generic Method 사용 
    String jeong = MyClass.genericMethod("Jeong");
    System.out.println(jeong);
  }
  
}///~
