package basic.external.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Junit 사용방법 테스트 케이스")
public class JunitExpTest {

  // @BeforeAll
  // static void initAll() {
  //   System.out.println("This should be executed once before all tests");
  // }

  // @BeforeEach 
  // void init() {
  //   System.out.println("This should be executed before some tests.");
  // }


  @Test
  @DisplayName("첫번재 테스트 케이스")
  public void testFirst(){ 
    System.out.println("This is the first test case");
  }

  @Test
  public void testSecond(){ 
    System.out.println("This is the second test case");
  }
  
  // @AfterEach 
  // void tearDown() {
  //   System.out.println("This is executed after some tests");
  // }
    

  @ParameterizedTest
  @ValueSource(strings = { "hello", "I", "am"})
  void testParam(String text) {
    System.out.println(text);
  }

  @ParameterizedTest
  @NullSource 
  @ValueSource(strings = { "hello", "I", "am"})
  void testparamNull(String text) {
    System.out.println(text);
  }

  
  // enum HttpProtocol { 
  //   HTTP1, HTTP2;
  // }

  @ParameterizedTest
  @EnumSource(HttpProtocol.class) 
  void testEnumSource(HttpProtocol protocol) {
    System.out.println(protocol.name());
  }

  @ParameterizedTest
  @CsvSource( {
    "홍길동, 10, 2020-12-20",
    "허난설, 20, 2021-01-20",
    "박한별, 25, 2019-12-01",
  })
  void testCSV(String name, int age, LocalDate date) {
    System.out.printf("%s, %d, %s", name, age, date);
    System.out.println("");
  }


  // Assertions
  /** assertEquals, assertNotEquals Test */
  @Test 
  void testAssertEquals() {
    // assertEquals(기대값, 실제값)
    assertEquals(2,2);
    assertNotEquals(2, 3);
  }
  @Test 
  void testAssertTrue() {
    //assertTrue( 10 < 5);
    assertTrue( 10 > 5);
    assertFalse(10 < 5);
  }

  /** assertNotNull , assertNull Test */
  @Test 
  void testAssertNull() {
    Object dog = new Object();
    assertNotNull(dog, () -> "The dog should not be null");
    
    Object cat = null;
    assertNull(cat, () -> "The cat should be null");
  }


  /** assertArrayEquals Test */
  @Test 
  void testAssertArray() {
    char[] expected = { 'J', 'u', 'p', 'i', 't', 'e', 'r' };
    char[] actual = "Jupiter".toCharArray();
    assertArrayEquals(expected, actual, "Arrays should be equal");
  }
  /** fail Test  */
  @Test
  void testFail() {
    // 무조건 실패
    fail("Failed");
  }

  /** assertAll Test */
  @Test
  void testAssertAll() {
    // 여러개의 assertion을 묶어서 실행 
    assertAll(
      "heading",
      () -> assertEquals(4, 2 * 2, "4 is 2 times 2"),
      () -> assertEquals("java", "JAVA".toLowerCase())
    );
  }

  // @AfterAll
  // static void tearDownAll() {
  //   System.out.println("This is executed once after all tests");
  // }

}//~
