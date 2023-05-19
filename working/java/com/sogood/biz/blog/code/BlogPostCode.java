package com.sogood.biz.blog.code;

import java.util.EnumSet;

import com.fasterxml.jackson.annotation.JsonValue;
import com.sogood.core.mybatis.MyBatisEnumStringTypeHandler;
import com.sogood.core.types.CodeEnumType;

import org.apache.ibatis.type.MappedTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlogPostCode implements CodeEnumType<String> {

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
  private static EnumSet<BlogPostCode> enumSet = null;
  static {
    enumSet = EnumSet.allOf(BlogPostCode.class);
  }

  public static BlogPostCode find(String code) {
    // 상수를 Stream을 사용해서 찾기
    return enumSet.stream().filter(constant -> constant.getCode().equals(code)).findFirst().orElseGet(() -> null);
  }
  
  public static String getJSON()  {
    // 클라이언트에 전체 상수 값을 내력 주기 위해
    try { 
      JSONArray arr = new JSONArray();
      for (BlogPostCode etype : BlogPostCode.values()) {
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

  @MappedTypes(BlogPostCode.class)
  public static class TypeHandler extends MyBatisEnumStringTypeHandler<BlogPostCode> {
    public TypeHandler() {
      super(BlogPostCode.class);
    }
  }
}/// ~
