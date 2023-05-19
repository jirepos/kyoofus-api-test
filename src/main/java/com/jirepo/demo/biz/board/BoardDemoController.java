package com.jirepo.demo.biz.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/board")
public class BoardDemoController {

    @RequestMapping("/index")
    public String index() throws Exception {
        return "biz/board/board";
    }// :

}
