package basic.java8.enumclass;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 어플리케이션에서 Enum을 사용하여 개발할 때 표준을 정하기 위한 테스트 케이스이다.
 */
public class EnumStandardTest {


  // 원칙
  // 1. 화면에서 코드 값을 사용할 때 코드 값을 직접 하드코딩하지 않는다.
  // 예) if(selectedValue == "C") (X)
  // 2. 화면에서 사용할 코드 값은 서버에서 Enum으로 정의한다.
  // 2.1 모든 Enum은 동일한 방식으로 사용하도록 Inteface를 구현한다.
  // 3. 화면에서 사용할 코드 값은 Enum을 Serialize해서 JSON으로 응답한다.
  // 4. JaveBean의 코드 값을 다루는 필드는 Enum 타입을 필드로 선언한다.
  // 5. MyBatis에서는 Custom TypeHandler를 사용하여 컬럼의 코드 값을 Enum으로 변환한다.

  // 코드값을 구할 때 사용하는 메소드는 interface를 상속 받아 구현해야 하기 때문에
  // interface를 정의한다.
  // NamingRule 
  // 1. Back-end
  //    TypeHandler
  //      EnumTypeHandler 
  //         MyBatisStringEnumTypeHandler
  //         MyBatisIntegerEnumTypeHandler 
  //    interface
  //    CodeEnumType 
  //       getCode()
  //    Enum
  //       CommonEnum
  //          [모듈명][테이블명|엔티티명]Enum.java 
  //          * 공통은 Common 사용 
  //          ex) 
  //             BlogCommonEnum 
  //             BlogPostEnum
  //       
  /** Enum이 구현해야 할 인터페이스. */
  public static interface CodeEnum<T> {
    /** 코드 값을 반환 */
    T getCode();

    // CodeEnum<T> findEnum(T t);
  }

  /** Interface를 구현한 Enum */
  @Getter
  @AllArgsConstructor
  public static enum BoardTypeEnum implements CodeEnum<String> {

    USE_YN_Y("Y", "사용여부", "네" ), USE_YN_N("N", "사용여부", "아니오");

    private String code;
    private String korName; 
    private String desc;

    // EnumSet으로 상수 저장
    private static EnumSet<BoardTypeEnum> enumSet = null;
    static {
      enumSet = EnumSet.allOf(BoardTypeEnum.class);
    }

    public static BoardTypeEnum find(String code) {
      // 상수를 Stream을 사용해서 찾기
      return enumSet.stream().filter(constant -> constant.getCode().equals(code)).findFirst().orElseGet(() -> null);
    }

    // public CodeEnum<String> findEnum(String code) {
    // return find(code);
    // }

    @JsonValue
    @Override
    public String getCode() {
      return code;
    }
  }

  /** Enum을 필드로 사용한 클래스 */
  @Getter
  @Setter
  @AllArgsConstructor
  public static class Board {
    private String name;
    private BoardTypeEnum boardType;

    public Board() {
    }
  }

  /** 클라이언트에 코드 상수와 코드 값을 내려 준다. */
  @Test
  public void testSerializeAll() throws Exception {
    // 클라이언트에 전체 상수 값을 내력 주기 위해
    JSONArray arr = new JSONArray();
    for (BoardTypeEnum etype : BoardTypeEnum.values()) {
      JSONObject json = new JSONObject();
      json.put(etype.name(), etype.getCode());
      json.put("korName", etype.getKorName());
      json.put("desc", etype.getDesc());
      arr.put(json);
    }
    System.out.println(arr.toString()); // [{"USE_YN_Y":"Y"},{"USE_YN_N":"N"}]
  }// :

  /** Java Bean에 코드 값 설정 */
  @Test
  public void testSetEnum() {
    Board board = new Board();
    board.setName("모든게시판");
    board.setBoardType(BoardTypeEnum.USE_YN_Y);
  }

  /** Java Bean에 코드 값 비교 */
  @Test
  public void testIf() {
    Board board = new Board();
    board.setName("모든게시판");
    board.setBoardType(BoardTypeEnum.USE_YN_Y);

    if (board.boardType == BoardTypeEnum.USE_YN_Y) {
      System.out.println("사용중입니다.");
    } else {
      System.out.println("사용중이지 않습니다.");
    }

    // switch 구문을 사용
    switch (board.boardType) {
    case USE_YN_N:
      System.out.println("사용 중이다.");
      break;
    case USE_YN_Y:
      System.out.println("사용중이지 않다.");
      break;
    default:
      System.out.println("값이 없다.");
      break;
    }

    // null check
    Board board2 = new Board();
    board2.setName("All");
    System.out.println(board2.boardType); // null
    if (board.boardType == null) {
      System.out.println("BoardType이 null이다.");
    }
    // switch 구문을 사용
    // null이기 때문에 오류 발생함
    Optional<BoardTypeEnum> opt = Optional.ofNullable(board2.boardType);
    BoardTypeEnum chkType = opt.orElseGet(() -> BoardTypeEnum.USE_YN_N); // default = N

    switch (chkType) {
    case USE_YN_Y:
      System.out.println("사용 중이다.");
      break;
    case USE_YN_N:
      System.out.println("사용중이지 않다.");
      break;
    default:
      System.out.println("값이 없다.");
      break;
    }

    // 아니면 이런 코드
    if (opt.isEmpty()) { /// if(board2.boardType == null) 이게 더 낫지 않을까?
      switch (chkType) {
      case USE_YN_Y:
        System.out.println("사용 중이다.");
        break;
      case USE_YN_N:
        System.out.println("사용중이지 않다.");
        break;
      default:
        System.out.println("값이 없다.");
        break;
      }
    }
  }// :

  /** 코드 값으로 enum 찾기 */
  @Test
  public void testFindCode() {
    //
    String code = "N";
    BoardTypeEnum brdType = BoardTypeEnum.find(code);
    System.out.println(brdType);
    System.out.println(brdType.getCode());
  }

  /** Enum을 필드로 사용한 클래스 시리얼라이즈/디시리얼라이즈 */
  @Test
  public void testSerialize() throws Exception {
    Board board = new Board();
    board.setName("모든게시판");
    board.setBoardType(BoardTypeEnum.USE_YN_Y);

    ObjectMapper om = new ObjectMapper();
    String jsonString = om.writeValueAsString(board);
    // 클라이언트로 값을 보낼 때
    System.out.println(jsonString); // {"name":"모든게시판","boardType":"Y"}

    // 클라언트에서 값을 받을 때
    Board board2 = new ObjectMapper().readValue(jsonString, Board.class);
    System.out.println(board2.getBoardType().getCode()); // Y
  }// :

  // NULL인 경우의 처리
  /** Enum을 필드로 사용한 클래스 시리얼라이즈/디시리얼라이즈 */
  @Test
  public void testSerializeNull() throws Exception {
    Board board = new Board();
    board.setName("모든게시판");

    ObjectMapper om = new ObjectMapper();
    String jsonString = om.writeValueAsString(board);
    // 클라이언트로 값을 보낼 때
    System.out.println(jsonString); // {"name":"모든게시판","boardType":null}

    // 클라언트에서 값을 받을 때
    Board board2 = new ObjectMapper().readValue(jsonString, Board.class);
    System.out.println(board2.getName()); // 모든 게시판

  }// :

  /** 
   * 다중 바인딩 테스트를 위한 제네릭 클래스
   * Enum 클래스의 하위 클래스와 CodeEnum 인터페이스의 구현체를 타입으로 받는다. 
   */
  public static class MyHandler<E extends Enum<E> & CodeEnum<String>> {


    // Enum의 모든 상수를 담는다. 
    private EnumSet<E> enumSet;

    public MyHandler(Class<E> type) {
      // allOf()를 사용하여 모든 상수를 담는다. 
      this.enumSet = EnumSet.allOf(type);
    }

    public E getEnum(String code) {
      // return this.enumSet.stream().filter(constant ->
      // constant.name().equals(code)).findFirst().orElseGet(() -> null);
      return this.enumSet.stream().filter(constant -> {
        
        // CodeEnum을 제네릭 타입으로 지정하지 않으면 캐스팅해야 함. 
        // 이것은 권장하는 방법이 아니다. 
        //CodeEnum<String> codeEnum = (CodeEnum<String>) constant;
        //return codeEnum.getCode().equals(code) ? true : false;
        // CodeEnum을 타입으로 설정하면 CodeEnum의 메소드에 접근할 수 있음 
        return constant.getCode().equals(code) ? true : false;
        // constant.name().equals(code)
      }).findFirst().orElseGet(() -> null);
    }
  }


  /** 코드 값으로 Enum을 구하는 테스트  */
  @Test
  public void testMyHandler() {
    // String code = "USE_YN_N";
    String code = "Y";
    MyHandler<BoardTypeEnum> myhandler = new MyHandler<BoardTypeEnum>(BoardTypeEnum.class);
    BoardTypeEnum rtEnum = myhandler.getEnum(code);
    System.out.println(rtEnum);
  }

  // 기초 
  private static enum BasicEnum {
    JWTKey, B, C 
  }

  @Test 
  public void testBasicEnum() {
    BasicEnum basicEnum = BasicEnum.JWTKey;
    System.out.println(basicEnum.getClass().toString());
    System.out.println(basicEnum.name());
  }




}/// ~
