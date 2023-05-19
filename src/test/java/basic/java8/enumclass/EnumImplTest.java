package basic.java8.enumclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Interface를 구현한 테스트 케이스 */
public class EnumImplTest {

  /**
   * Enum Class를 만드는 경우 이 인터페이스를 구현해야 한다.
   */
  public static interface TestEnumType {
    /** ENUM의 코드 값 */
    String getCode();
    /**
     * 코드 한글명 화면에 표시가 필요한 경우 표시할 값. 그러나 다국어 문제가 있음
     */
    String getText();

    /** 설명 */
    String getDescription();
  }

  // 아래 어노테이션을 사용하면 deserializer를 사용할 때 오류 발생
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @Getter
  @AllArgsConstructor
  public static enum EmployeeType implements TestEnumType {

    FullTime("F", "Full Time", "Full Time Job"), PartTime("P", "Part Time", "Part Time Job");

    private String code;
    private String text;
    private String description;

    /** ENUM의 코드 값 */
    // JsonValue 어노테이션을 사용하면 이 값이 serialize된다.
    @JsonValue
    @Override
    public String getCode() {
      return this.code;
    }

    /**
     * 코드 한글명 화면에 표시가 필요한 경우 표시할 값. 그러나 다국어 문제가 있음
     */
    @Override
    public String getText() {
      return this.text;
    }

    /** 설명 */
    @Override
    public String getDescription() {
      return this.description;
    }
  }//

  @Getter
  @Setter
  public static class Employee {
    private String name;
    private EmployeeType employeeType;
  }


  /** Serialize 테스트  */
  @Test
  public void testSerialize() throws Exception {

    Employee employee = new Employee();
    employee.setName("Amy");
    employee.setEmployeeType(EmployeeType.PartTime);
    System.out.println("-- before serialization --");
    System.out.println(employee);
    System.out.println("-- after serialization --");
    ObjectMapper om = new ObjectMapper();
    String jsonString = om.writeValueAsString(employee);
    System.out.println(jsonString);
    /**
     * -- before serialization --
     * com.sogood.test.java.enumclass.EnumImplTest$Employee@77e4c80f
     * -- after serialization --
     * {"name":"Amy","employeeType":"P"}
     */

    Employee employee2 = new ObjectMapper().readValue(jsonString, Employee.class);
    System.out.println(employee2.getEmployeeType());
    /**
     * PartTime
     */

  }// :

}/// ~
