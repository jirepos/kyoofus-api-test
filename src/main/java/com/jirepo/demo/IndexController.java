package com.jirepo.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /** 도메인만 입력 시 랜딩 페이지 표시 */
    @GetMapping("/")
    public String baseUrl() {
        return "index";
    }

}//
