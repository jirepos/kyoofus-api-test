package com.jirepo.demo.codec;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/** JWT 데모 컨트롤러이다. */
@Controller
@RequestMapping("/demo/base64")
@Slf4j
public class Base64DemoController {
    
     /** 
     * Base64 테스트 페이지 
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        return "core/base64";
    }//:

}
