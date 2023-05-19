package basic.java8.func;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class FunctionalTest {
  /* 함수형 인터페이스. 1개의 추상 메소드를 갖는다. */
  static interface FunctionalInterface {
    public abstract void doSomething(String text);
  }

  /** 함수형 인터페이스 사용 */
  @Test
  public void testFunctional() {
    // 함수형 인터페이스를 사용하는 이유는 자바의 람다식은 함수형 인터페이스로만 접근이 되기 때문.
    FunctionalInterface func = text -> System.out.println(text);
    func.doSomething("hello");
  }

  /**
   * 인터페이스를 이렇게 간단히 생성하여 반환 가능하다.
   * @return
   */
  public FunctionalInterface getFunctionalInterface() {
    return text -> System.out.println(text);
  } 

  /** 함수형 인터페이스를 생성한 함수를 이용한 테스트 */
  @Test
  public void testFunctional2() {
    FunctionalInterface func = getFunctionalInterface();
    func.doSomething("hello");
  }



  @Test
  public void testMyFunctional() {
    MyFuncInterface func = text -> System.out.println(text);
    func.print("hello");
  }

  @Test
  public void testConsumer() {
    Consumer<String> c = str -> System.out.println(str);
    c.accept("hello1");
  }

  @Test
  public void testFunction() {
    Function<String, Integer> f = str -> Integer.parseInt(str);
    Integer result = f.apply("1");
    System.out.println(result);
  }

  @Test
  public void testSupplier() {
    Supplier<String> s = () -> "hello supplier";
    String result = s.get();
    System.out.println(result);
  }




}/// ~
