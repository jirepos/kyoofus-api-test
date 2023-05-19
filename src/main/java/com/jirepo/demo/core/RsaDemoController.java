package com.jirepo.demo.core;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.core.crypt.RSAUtils;
import com.jirepo.core.web.util.session.SessionUtils;

/** RSA 암호화 테스트 데코 클래스이다. */
@Controller
@RequestMapping("/demo/rsa")
public class RsaDemoController {
    

    /** 공개키를 가진 화면을 반환한다. */
    @GetMapping("/index")
    public String index(HttpServletRequest request) throws Exception {
        KeyPair keyPair = RSAUtils.createKeyPair(1024);  // 키를 생성한다. 
        //String privateKey = RSAUtils.writePemToString(keyPair.getPrivate(), RSAUtils.RSA_PRIVATE_KEY_DESC);
        // PEM 형식으로 공개키 변환 
        String publicKey = RSAUtils.writePemToString(keyPair.getPublic(), RSAUtils.RSA_PUBLIC_KEY_DESC);
        SessionUtils.setObject(request, SessionUtils.SESSION_RSA_KEY, keyPair);// 세션에 키를 저장한다.
        // PEM 포맷의 개인키를 리퀘스트에 설정한다
        request.setAttribute("publicKey", publicKey);
        return "core/rsa";
    }//:

    /** 복호화한 값 반환 */
    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(HttpServletRequest request, HttpServletResponse response, @RequestBody RsaDemoBean bean) throws Exception {
        //String pwd = request.getParameter("pwd");
        String pwd = bean.getPwd();
        System.out.println("pwd: " + pwd);
        KeyPair keyPair = (KeyPair)SessionUtils.getObject(request, SessionUtils.SESSION_RSA_KEY);
        //String privateKey = RSAUtils.writePemToString(keyPair.getPrivate(), RSAUtils.RSA_PRIVATE_KEY_DESC);
        // 여기서 복호화 
        String decrypted = RSAUtils.decrypt(pwd, RSAUtils.privateKeyToBase64(keyPair));
        System.out.println("decrypted: " + decrypted);
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.TEXT_PLAIN);  // MediaType을 설정해야 한다.
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>(decrypted, headers,  HttpStatus.OK);
    }//


}///~
