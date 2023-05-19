package com.jirepo.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



/** Demo Controller */
@Controller
@RequestMapping("/demo")
public class IndexDemoController {
    /** index page  */
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
}///~
