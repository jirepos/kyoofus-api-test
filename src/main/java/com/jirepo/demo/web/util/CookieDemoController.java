package com.jirepo.demo.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.core.web.util.CookieUtils;

/** Cookie 사용 데모 컨트로러이다. */
@Controller
@RequestMapping("/demo/cookie")
public class CookieDemoController {

  /** index page */
  @GetMapping("/index")
  public String index() {
    return "web/cookie";
  }

  // Cookie Section
  /** 쿠키 추가 */
  @GetMapping("/add-cookie")
  public ResponseEntity<String> addCookie(HttpServletRequest request, HttpServletResponse response) {
    System.out.println("hello");
    // Cookie를 추가한다. 브라우저에서 확인할 수 있다.
    CookieUtils.addCookie(response, "testCookie", "hello");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN); // MediaType을 설정해야 한다.

    return new ResponseEntity<String>("success", headers, HttpStatus.OK);
  }// :

  /** 쿠키 값 읽기 */
  @GetMapping("/get-cookie-value")
  public ResponseEntity<String> getCookieValue(HttpServletRequest request, HttpServletResponse response) {
    // 쿠키 값을 가져온다.
    String cookieVal = CookieUtils.getCookieValue(request, "testCookie");
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN); // MediaType을 설정해야 한다.

    return new ResponseEntity<String>(cookieVal, headers, HttpStatus.OK);
  }// :

  /** 모든 쿠키 값 읽기 */
  @GetMapping("/read-all-cookie")
  public ResponseEntity<String> readAllCookie(HttpServletRequest request, HttpServletResponse response) {
    // 모든 쿠키 값을 읽는다
    Cookie[] cookies = CookieUtils.readAllCookie(request);
    if (cookies != null) {
      for (Cookie cki : cookies) {
        System.out.println(cki.getName());
      }
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN); // MediaType을 설정해야 한다.

    return new ResponseEntity<String>("success", headers, HttpStatus.OK);
  }// :

  /** 쿠키 삭제 */
  @GetMapping("/delete-cookie/{cookieName}")
  public ResponseEntity<String> deleteCookie(HttpServletRequest request, HttpServletResponse response,
      @PathVariable String cookieName) {
    // 모든 쿠키 값을 읽는다
    CookieUtils.deleteCookie(response, cookieName);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN); // MediaType을 설정해야 한다.
    return new ResponseEntity<String>("success", headers, HttpStatus.OK);
  }// :

}
