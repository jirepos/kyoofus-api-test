package com.jirepo.demo.mustache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/mustache")
public class MustacheDemoController {

    /** index page */
    @GetMapping("/index")
    public String index() {
        return "mustache/index";
    }// :


    
}
