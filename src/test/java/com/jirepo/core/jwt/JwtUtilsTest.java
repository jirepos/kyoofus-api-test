package com.jirepo.core.jwt;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.jirepo.core.util.IoUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;



/**
 * JWT(JSON Web Token) 테스트 케이스이다. 
 */
public class JwtUtilsTest {
  
  @Test 
  public void readKeyTest() throws Exception { 
    String key = IoUtils.readFileClasspathToString("jwt.key", "UTF-8");
    System.out.println("key:" + key);
  }

  @Test
  public void createKeyTest() throws Exception {
    String encodedKey = JwtUtils.createKey();   // Token Key를 생성한다. 
    System.out.println(encodedKey); // 키 출력 
    String path = ""; 
    File f = IoUtils.createFileInClasspath(path, "jwt.key");
    IoUtils.writeStringToFile(f, encodedKey, "UTF-8"); // 키를 파일로 저장한다.

  }//:

  @Test
  public void testJWT() throws Exception  {

    String encodedKey = JwtUtils.createKey();   // Token Key를 생성한다. 
    System.out.println(encodedKey); // 키 출력 

    String subject = "test";   // 주제 
    String issuer = "jirepos@gmail.com";  // 발행자 
    String audience = "all";  // 대상 

    long expiration = 60 * 60 *  1000; // 유효기간 하루 
    Map<String, String> claims = new HashMap<String, String>(); // 데이터를 담을 맵 생성 
    String userJson = "{ \"userId\": \"jirepos@gmail.com\" }";  // 사용자 정보, 나중에 객체를 사용하여 JSON으로 변환 
    claims.put("userInfo", userJson);  // 사용자 정보를 맵에 담는다. 

    // JWS(JSON Web Signature) 생성 
    // JWS는 JSWON Web Signatrue의 약자로 Private Key로 서명한 Token이라고 보면 된다.
    String jws = JwtUtils.createJws(encodedKey, subject, issuer, audience, expiration, claims);
    System.out.println(jws);  // JWS 출력 


    // Token 읽기
    Jws<Claims> jwstoken = JwtUtils.readToken(encodedKey, jws); // JWS로부터 Token을 읽는다.
    String returnedSubject = JwtUtils.getSubject(jwstoken); // 주제 반환 
    System.out.println(returnedSubject); // 주제 출력
    String returnedAudience = JwtUtils.getAudience(jwstoken); // 대상 반환
    System.out.println(returnedAudience); // 대상 출력
    String returnedId = JwtUtils.getId(jwstoken); // Token 아이디 반환
    System.out.println(returnedId); //  Token 아이디 출력
    String returnedIssuer = JwtUtils.getIssuer(jwstoken); //  발행자 반환
    System.out.println(returnedIssuer); // 발행자 출력
    
    Date date = JwtUtils.getExpiration(jwstoken);  // 유효기간 반환
    System.out.println(date.toString()); // 유효기간 출력

    String returnedClaim = JwtUtils.getClaim(jwstoken, "userInfo"); // 사용자 정보 반환
    System.out.println(returnedClaim);  // 사용자 정보 출력

    // 만료 여부 체크 
    if(!JwtUtils.isExpired(date)) {
      System.out.println("유효한 토큰.");
    }else { 
      System.out.println("만료된 토큰");
    }
  }//:
  
}///~