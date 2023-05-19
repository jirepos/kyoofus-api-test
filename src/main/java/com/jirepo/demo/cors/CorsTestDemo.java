package com.jirepo.demo.cors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.core.web.util.CookieUtils;


@Controller
@RequestMapping("/demo/cors")
public class CorsTestDemo {
    

    @GetMapping("/test")
    public ResponseEntity<CorsDemoBean> corsDemo(HttpServletRequest request, HttpServletResponse response) {

        String mycookie = CookieUtils.getCookieValue(request, "mycookie");
        System.out.println("mycookie: " + mycookie);

        response.setHeader("X-Message", "hahahah");

        // CookieUtils.addCookie(response, "testcookie", "testValue");
        CorsDemoBean bean = new CorsDemoBean();
        bean.setId("1");
        bean.setName("반갑습니다.");
        return ResponseEntity.ok(bean);

    }

    
    @GetMapping("/test2")
    public ResponseEntity<CorsDemoBean> corsDemo2(HttpServletResponse response) {

        CorsDemoBean bean = new CorsDemoBean();
        bean.setId("1");
        bean.setName("Test");
        return ResponseEntity.ok(bean);
    }
}//~
