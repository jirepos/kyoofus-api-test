package com.jirepo.demo.biz.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/login")
public class LoginDemoController {


    /** 공개키를 가진 화면을 반환한다. */
    @GetMapping("/index")
    public String index() throws Exception {
        return "biz/login/login";
    }//:
    
}
