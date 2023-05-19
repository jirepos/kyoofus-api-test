package basic.java8.optional;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class OptionalTest {



  /**  empty() : 값이 null인 Optional */
  @Test 
  public void testOptional() { 
    //Optional은 Wrapper 클래스이기 때문에 값이 없을 수도 있는데, 이때는 Optional.empty()로 생성할 수 있다.
    Optional<String> opt1 = Optional.empty();
    System.out.println(opt1);  // Optioonal.empty
    if(opt1.isPresent()) {
      System.out.println("Present"); 
    }else {
      System.out.println("No Present"); // No Present가 출력된다 
    }
  }//:


  /**
   * ofNullable() : 값이 null일 수도 아닐 수도 있는 Optional
   */
  @Test 
  public void testOptional2() { 
    //Optional.ofNullbale() - 값이 Null일수도, 아닐수도 있는 경우
    Optional<List<String>> opt2 = Optional.ofNullable(null);
    if(opt2.isEmpty()) {
      System.out.println("empty");
    }
  }//:  
  /** of() : 값이 null을 허용하지 않는 Optional  */
  @Test 
  public void testOptional3() { 
    try {
      // Optional.of() - 값이 Null이 아닌 경우
      Optional<List<String>> opt2 = Optional.of(null);
      if(opt2.isEmpty()) {
        System.out.println("empty");
      }      
    } catch (Exception e) {
      // of(null)을 쓰는 경우에 오류 발생 
    }

  }//:  


  /**
   * get() 
   */
  @Test 
  public void testOptional4() { 
    //Optional은 내부 값을 null로 초기화한 싱글턴 객체를 Optional.empty() 메서드를 통해 제공하고 있습니다. 위에서 인용한 답변과 같이 “결과 없음”을 
    //표현해야 하는 경우라면 null을 반환하는 대신 Optional.empt()를 반환하면 값을 반환받은 쪽에서는 이후에 소개할 메서드들을 통해 적절하게 처리를 이어갈 수 있습니다.
    Optional<String> opt = Optional.of("Hong");
    // Optional.get() 호출 전에 Optional 객체가 값을 가지고 있음을 확실히 할 것
    // get() 메소드를 사용하면 Optional 객체에 저장된 값에 접근할 수 있습니다.
    String val = opt.get();
    System.out.println(val);
  }//:  


  /** orElse()  */
  @Test 
  public void testOptional5() { 
    Optional<String> opt = Optional.ofNullable(null);
    // 저장된 값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 인수로 전달된 값을 반환함.
    String val = opt.orElse("other");
    System.out.println(val);
  }//:  
  

  /** orElseGet() */
  @Test 
  public void testOptional6() { 
    Optional<String> opt = Optional.ofNullable(null);
    // 값이 없는 경우, Optional.orElseGet()을 통해 이를 나타내는 객체를 제공할 것
    // 저장된 값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 인수로 전달된 람다 표현식의 결괏값을 반환함.
    String val = opt.orElseGet(() -> "Hello");
    System.out.println(val);
  }//:  


  @Getter
  @Setter
  @AllArgsConstructor
  public static class Employee { 
    private String name; 
    private int age; 
    public Employee(){ }
  }

  /** 객체를 Optional로 감싸는 테스트 */
  @Test
  public void testOptionalWithObject(){
    
    Employee employee = new Employee();
    employee.setName("Lotte");
    // Null일 수도 있는 객체는 ofNullable 사용 
    Optional<Employee> opt = Optional.ofNullable(employee);
    // orElseGet은 null 아닌 경우에는 employee를 반환 
    Employee emp2 = opt.orElseGet(() -> new Employee());
    System.out.println(emp2.name);
    System.out.println(emp2.getAge());

    // 멤버가 null 인경우 
    Employee employee2 = new Employee();
    Optional<String> opt2 = Optional.ofNullable(employee2.getName());
    String userName = opt2.orElseGet(() -> "Hello"); // null이기 때문에 주입한 값
    System.out.println(userName);
  }
  
}///~
