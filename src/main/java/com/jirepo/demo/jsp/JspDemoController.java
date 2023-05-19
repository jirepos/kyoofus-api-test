package com.jirepo.demo.jsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/jsp")
public class JspDemoController {

    @GetMapping("/index")
    public String index() {
        // webapp 아래의 전체 경로를 설정해야 동작함 
        // return "/WEB-INF/view/test.jsp";
        return "/test";
    }
    
}
