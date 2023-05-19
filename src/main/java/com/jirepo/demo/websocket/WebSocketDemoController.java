package com.jirepo.demo.websocket;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/websocket")
public class WebSocketDemoController {


    /** 도메인만 입력 시 랜딩 페이지 표시 */
    @GetMapping("/")
    public String index() {
        return "websocket/websocket";
    }    

    @GetMapping("/server-send")
    public ResponseEntity<String> serverSend(){ 
        WebSocketDemo.sendMesssageToAll("Server Message");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>("Success", headers, HttpStatus.OK);
    
    }
    
    
}
