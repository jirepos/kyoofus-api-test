package basic.java8.enumclass;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Enum 클래스를 Serialize/Deserialize 하는 테스트 케이스이다. 
 */
public class EnumBasicSerialieTest {

  //아래 어노테이션을 사용하면 deserializer를 사용할 때 오류 발생 
  //@JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @Getter
  @AllArgsConstructor
  public static enum EmployeeType {
    FullTime("Full Time"), PartTime("Part Time");
    private String displayName;
  }//


  @Getter
  @Setter
  public static class Employee { 
    private String name;
    private EmployeeType employeeType; 
  }

  @Test
  public void testSerialize()  throws IOException {
    // Refer to https://www.logicbig.com/tutorials/misc/jackson/json-format-enum-as-object.html
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
     * @JsonFormat을 사용한 경우 
     * -- before serialization --
     * com.sogood.test.java.enumtest.EnumTest2$Employee@35fc6dc4
     * -- after serialization --
     * {"name":"Amy","employeeType":{"displayName":"Part Time"}}
     */

    /*
     * 아래와 같이 serialize하는 것이 default 이다. 
     * 
     * @JsonFormat을 사용하지 않은 경우 
     * -- before serialization --
     * com.sogood.test.java.enumtest.EnumTest2$Employee@35fc6dc4
     * -- after serialization --
     * {"name":"Amy","employeeType":"PartTime"}
     */
  }

  @Test 
  public void testDeserialize() throws IOException {
    Employee employee = new Employee();
    employee.setName("Amy");
    employee.setEmployeeType(EmployeeType.PartTime);
    System.out.println("-- before serialization --");
    System.out.println(employee);
    System.out.println("-- after serialization --");
    ObjectMapper om = new ObjectMapper();
    String jsonString = om.writeValueAsString(employee);
    System.out.println(jsonString);

    // Refer to https://www.baeldung.com/jackson-serialize-enums
    //  @JsonFormat이 어노테이션에 적용되어 있으면 오류 발생
    Employee employee2 = new ObjectMapper().readValue(jsonString, Employee.class);
    System.out.println(employee2.getEmployeeType());
  }//:

  @Test
  public void testSerializeAll() throws Exception {
    // 클라이언트에 전체 상수 값을 내력 주기 위해
    JSONArray arr = new JSONArray();
    for(EmployeeType etype : EmployeeType.values()) {
      JSONObject json = new JSONObject();
      json.put(etype.name(), etype.getDisplayName());
      arr.put(json);
    }
    System.out.println(arr.toString());
    // [{"FullTime":"Full Time"},{"PartTime":"Part Time"}]
    // 클라이언트에서 "FUllTime" 상수 이름을 사용할지 상수 값인 
    // displayName을 사용할지 결정해야 함 
    // 서버에 전송할 때
    //  { employeeType: "FullTime" }
  }//:
  
}////~
