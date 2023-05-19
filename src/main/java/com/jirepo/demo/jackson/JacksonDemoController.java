package com.jirepo.demo.jackson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo/jackson")
public class JacksonDemoController {


    /** applicatin/json 반환 */
    @GetMapping("/get-bean")
    public ResponseEntity<TimeDemoBean> getJson(HttpServletRequest request, HttpServletResponse response) {
        TimeDemoBean bean = new TimeDemoBean();
        return new ResponseEntity<TimeDemoBean>(bean, HttpStatus.OK);
    }// :

}
