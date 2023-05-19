package com.jirepo.core.codec;


import java.util.Base64;

import org.junit.jupiter.api.Test;


public class Base64Test {



  /** 기본 encode */
  @Test
  public void testEncodeBasic() {
    String str = "이것은 원본 original 문자열입니다.ㅋㅋa"; 
    byte[] encodedByte = Base64.getEncoder().encode(str.getBytes()); // byte로 변환 
    String encodedStr = new String(encodedByte); // 다시 문자열로 
    System.out.println(encodedStr); // 문자열 출력 
  }//:

  /** 기본 decode */
  @Test
  public void testDecodeBasic() {
    String str = "이것은 원본 original 문자열입니다.ㅋㅋa"; 
    byte[] encodedByte = Base64.getEncoder().encode(str.getBytes()); // byte로 변환 
    byte[] decodedByte = Base64.getDecoder().decode(encodedByte);
    String decodedStr = new String(decodedByte);
    System.out.println(decodedStr);
  }

  /** 문자열로 encode */
  @Test 
  public void testEncodeString() {
    String str = "이것은 원본 original 문자열입니다.ㅋㅋa"; 
    String encodedStr = Base64.getEncoder().encodeToString(str.getBytes());
    System.out.println(encodedStr);
  }
  /** 문자열로 dncode */
  @Test 
  public void testDecodeString() {
    String encodedStr = "7J206rKD7J2AIOybkOuzuCBvcmlnaW5hbCDrrLjsnpDsl7TsnoXri4jri6Qu44WL44WLYQ==";
    byte[] decodedBytes = Base64.getDecoder().decode(encodedStr);
    System.out.println(new String(decodedBytes));
  }
  
  /** 패딩없는 encode */
  @Test 
  public void testEncodeStringWithoutPadding() {
    String str = "이것은 원본 original 문자열입니다.ㅋㅋa"; 
    String encodedStr = Base64.getEncoder().withoutPadding().encodeToString(str.getBytes());
    System.out.println(encodedStr);
  }

  /** Mime Encode */
  @Test
  public void testMimeEncode() {
    // 출력의 각 행의 76자를 넘어서는 안된다. 
    // 캐리지 리턴과 라인피드(\r\n)로 끝난다. 
    String str = "Base64 인코딩에서 출력 인코딩 된 문자열의 길이는 3의 배수여야 한다."
       + "그렇지 않은 경우 출력은 추가 패드 문자 ( = ) 로 채워진다."
       + "5년 만의 미국 출장을 마친 이재용 삼성전자 부회장이"
       + "우리 현장의 목소리와 시장의 냉혹한 현실을 직접 보고 오게 되니 마음이 무겁다고 밝혔다";
       String encodedStr = Base64.getMimeEncoder().encodeToString(str.getBytes());
       System.out.println(encodedStr);

       // decode 
       byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedStr);
       System.out.println(new String(decodedBytes));

  }//:


  @Test 
  public void testUrlEncode() {
    String url = "https://www.demo.com?abc_dd=absF&gw_ql#q=aa1";
    String encodedUrl = Base64.getUrlEncoder().encodeToString(url.getBytes());
    System.out.println(encodedUrl);
    byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedUrl);
    String decodedUrl = new String(decodedBytes);
    System.out.println(decodedUrl);
  }

}///~






