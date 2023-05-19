package com.jirepo.demo.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

/** RequestContextHolder Test Controller */
@RequestMapping("/demo/util")
@Controller
@Slf4j
public class RequestContextHolderController {
    /** RequestContextHolder로부터 HttpServletRequest와 HttpServletResponse를 구한다.  */
    @GetMapping(value="/request-context",  produces="text/plain;charset=utf-8")
    public @ResponseBody String requestContext() {
        // currentAttributes()   구하려는 RequestAttributes가 없으면 예외를 반환  
        // getRequestAttributes() 구하려는 RequestAttributes가 없으면 null을 반환 
        // RequestContextHolder는 Spring 컨텍스트에서 HttpServletRequest 에 직접 접근할 수 있다. 
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        log.debug(request.getRequestURI());
        HttpServletResponse response = attr.getResponse();
        response.setHeader("X-TEST-HEADER", "HELLO");
        return "ok";
    }//:
    
}//~
