package basic.java8.charset;


import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * StandardCharsets 클래스에는 모든 자바 가상 머신에서 반드시 지원해야 하는 문자 인코딩을 나타내는 Charset 타입 정적 변수가 있다.
 * <pre>
 * StandardCharsets.UTF_8
 * StandardCharsets.UTF_16
 * StandardCharsets.UTF_16BE
 * StandardCharsets.UTF_16LE
 * StandardCharsets.ISO_8859_1
 * StandardCharsets.US_ASCII
 * </pre>
 */
public class CharsetTest {


  @Test 
  public void testCharset() {
    String rawString = "Entwickeln Sie mit Vergnügen";
    // Java 1.7 이상에서 Charset 상수를 사용한다. 
    byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
    String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
    System.out.println(utf8EncodedString);
  }//~
  
}///~
