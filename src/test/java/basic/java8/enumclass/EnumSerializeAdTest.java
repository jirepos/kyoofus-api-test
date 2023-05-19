package basic.java8.enumclass;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * JsonValue 어노테이션을 사용하여 JSON으로 serialize/deserialize하는 테스트 케이스이다. 
 * 
 */
public class EnumSerializeAdTest {
  
  //아래 어노테이션을 사용하면 deserializer를 사용할 때 오류 발생 
  //@JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @Getter
  @AllArgsConstructor
  public static enum EmployeeType {
    FullTime("F"), PartTime("P");
    private String displayName;
    /** JsonValue 어노테이션 적용 */
    @JsonValue
    private String getDisplayName() {
      return this.displayName; 
    }
  }//


  @Getter
  @Setter
  public static class Employee { 
    private String name;
    private EmployeeType employeeType; 
  }

  @Test 
  public void testDeserialize() throws IOException {
    // Refer  to https://www.baeldung.com/jackson-serialize-enums 
    Employee employee = new Employee();
    employee.setName("Amy");
    employee.setEmployeeType(EmployeeType.PartTime);
    System.out.println("-- before serialization --");
    System.out.println(employee);
    System.out.println("-- after serialization --");
    ObjectMapper om = new ObjectMapper();
    String jsonString = om.writeValueAsString(employee);
    System.out.println(jsonString);
    /*
     * -- before serialization --
     * com.sogood.test.java.enumtest.EnumSerializeAdTest$Employee@351d0846
     * -- after serialization --
     * {"name":"Amy","employeeType":"P"}
     */

    // Refer to https://www.baeldung.com/jackson-serialize-enums
    //  @JsonFormat이 어노테이션에 적용되어 있으면 오류 발생
    Employee employee2 = new ObjectMapper().readValue(jsonString, Employee.class);
    System.out.println(employee2.getEmployeeType());
  }//:
  
  
}///~
