package com.jirepo.demo.core;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.core.jwt.JwtToken;
import com.jirepo.core.jwt.JwtUtils;
import com.jirepo.core.web.util.session.SessionUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;

/** JWT 데모 컨트롤러이다. */
@Controller
@RequestMapping("/demo/jwt")
@Slf4j
public class JwtDemoController {

    /** 
     * JWT 테스트 페이지 
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        
        // 클레임 생성 
        Map<String, String> claims = new HashMap<String, String>();
        claims.put("userId", "jirepos");  // 사용자 아이디 
        claims.put("pwd", "1234");  // 패스워드 
        String encodedKey = SessionUtils.JWT_KEY;  // 토큰 암호화 키 
        String subject = "user";   // 주제 
        String issuer = "jirepos";   // 발행자 
        String audience = "all";  // 대상 
        long expiration = 60 * 60 * 1000;  // 유효기간 하루
        String jws = JwtUtils.createJws(encodedKey, subject, issuer, audience, expiration, claims); // JWS 생성 
        request.setAttribute("jws", jws); 
        return "core/jwt";
    }//:


    /**
     * 사용자 요청에 대해 Authorization 헤더에서 token을 읽는다. 
     * @param request 사용자 요청
     * @return 응답 
     */
    @PostMapping("/send")
    public ResponseEntity<String> send(HttpServletRequest request) {
        // 인증 헤더를 읽는다. 
        String authorization = request.getHeader("Authorization");
        log.debug(authorization);
        // Bearer 제거한다. 
        String jws = authorization.substring(7);
        log.debug("#" + jws + "#");
        
        // JwtUtils를 사용하여 토큰에서 정보를 가져온다. 
        Jws<Claims> jwstoken = JwtUtils.readToken(SessionUtils.JWT_KEY, jws); // JWS로부터 Token을 읽는다.
        log.debug("issuer: {}",  JwtUtils.getIssuer(jwstoken));
        log.debug("subject: {}",  JwtUtils.getSubject(jwstoken)); 
        log.debug("audience: {}",  JwtUtils.getAudience(jwstoken));

        // JwtToken을 사용하여 간편하게 token에서 정보를 가져온다. 
        JwtToken token = new JwtToken(SessionUtils.JWT_KEY, jws);
        log.debug("issuer: {}", token.getIssuer());
        log.debug("subject: {}", token.getSubject());
        log.debug("userId: {}", token.getCliam("userId"));
        log.debug("pwd: {}", token.getCliam("pwd"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("OK", headers,  HttpStatus.OK);
    }//:
    
}///~
