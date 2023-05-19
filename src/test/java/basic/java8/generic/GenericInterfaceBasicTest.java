package basic.java8.generic;

import org.junit.jupiter.api.Test;

/**
 * Generic interface를 구현현 Generic class와 Non-Generic class를 테스트한다. 
 */
public class GenericInterfaceBasicTest {

  /** Generic Interface를 정의한다. */
  static interface MyInterface<T, K> {
    T doSomething(T t);
    K doSomething2(K k);
  }

  /** Generic Interface를 상속한 Generic class를 정의한다.   */
  static class MyClass<T, K> implements MyInterface<T, K> {
    public T doSomething(T t) {
      return t; 
    }
    public K  doSomething2(K k) {
      return k;
    }
  }
  
  /**
   * Generic Interface를 상속한 Non Generic class. Type을 구체적으로 직접 지정해야 한다. 
   */
  static class MyNonClass implements MyInterface<String, Integer> {
    // 구체적으로 타입을 명시해야 
    public String doSomething(String t) {
      return t; 
    }
    // 구체적으로 타입을 명시해야 
    public Integer  doSomething2(Integer k) {
      return k;
    }
  }//:




  /**
   * Generice Interface를 구현한 Generic class 테스트 케이스.
   */
  @Test 
  public void testGenericInterfaceA() {
    // Generic class 생성 
    MyClass<String, Integer> myclass = new MyClass<String, Integer>(); 
    String t = myclass.doSomething("Hello");
    Integer k = myclass.doSomething2(1);
    System.out.println(t);
    System.out.println(k);
  }


  /**
   * Generice Interface를 구현한 Non-Generic class 테스트 케이스.
   */
  @Test 
  public void testGenericInterfaceB() {
    // Generic class 생성 
    MyNonClass myclass = new MyNonClass(); 
    String t = myclass.doSomething("Hello2");
    Integer k = myclass.doSomething2(2);
    System.out.println(t);
    System.out.println(k);
  }

}///~
