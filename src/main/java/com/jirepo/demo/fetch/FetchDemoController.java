package com.jirepo.demo.fetch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.core.web.exception.BaseBizErrorCode;
import com.jirepo.core.web.exception.BaseBizException;

@Controller
@RequestMapping("/demo/fetch")
public class FetchDemoController {
    /** 
     *  Fetch Test Page 
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        return "request/fetch-basic";
    }//:
    
    @GetMapping("/ajax")
    public String ajax(HttpServletRequest request) {
        return "request/ajax-test";
    }//:


    @GetMapping("/error")
    public ResponseEntity<String> error(HttpServletRequest request) {
        boolean flag = true; 
        if(flag) {
            throw new BaseBizException(BaseBizErrorCode.OTHER_ERROR);
        }
        return ResponseEntity.ok().body("success");
    }//:
    
}
