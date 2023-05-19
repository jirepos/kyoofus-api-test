package com.jirepo.demo.mybatis;


import java.util.EnumSet;

import org.apache.ibatis.type.MappedTypes;
// import org.json.JSONArray;
// import org.json.JSONObject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonValue;
import com.jirepo.core.mybatis.MyBatisEnumStringTypeHandler;
import com.jirepo.core.types.CodeEnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 
 * 코드값을 저장하는 테이블의 컬럼에 enum을 사용하기 위해 정의한 클래스이다. 
 * 각 코드값을 가지는 enum은 TypeHandler를 구현해야 한다. 
 */
@Getter
@AllArgsConstructor
public enum DemoCode implements CodeEnumType<String> {

  BLOG_POST_DELETE_YN_N("N", "삭제여부", "아니오"), BLOG_POST_DELETE_YN_Y("Y", "삭제여부", "예");

  private String code;
  private String korName;
  private String desc;

  // Serialize/Deseriaze하기 위해 어노테이션 사용하고 override한다.
  @JsonValue
  @Override
  public String getCode() {
    return this.code;
  }

  // EnumSet으로 상수 저장
  private static EnumSet<DemoCode> enumSet = null;
  static {
    enumSet = EnumSet.allOf(DemoCode.class);
  }

  public static DemoCode find(String code) {
    // 상수를 Stream을 사용해서 찾기
    return enumSet.stream().filter(constant -> constant.getCode().equals(code)).findFirst().orElseGet(() -> null);
  }
  
  public static String getJSON()  {
    // 클라이언트에 전체 상수 값을 내력 주기 위해
    try { 
      JSONArray arr = new JSONArray();
      for (DemoCode etype : DemoCode.values()) {
        JSONObject json = new JSONObject();
        json.put(etype.name(), etype.getCode());
        json.put("korName", etype.getKorName());
        json.put("desc", etype.getDesc());
        arr.put(json);
      }
      return arr.toString();
    }catch(Exception e) {
      throw new RuntimeException(e);
    }
  }// :

  @MappedTypes(DemoCode.class)
  public static class TypeHandler extends MyBatisEnumStringTypeHandler<DemoCode> {
    public TypeHandler() {
      super(DemoCode.class);
    }
  }
}/// ~