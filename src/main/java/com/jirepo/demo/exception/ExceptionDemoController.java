package com.jirepo.demo.exception;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.core.web.exception.BaseBizErrorCode;
import com.jirepo.core.web.exception.BaseBizException;


/** 예외처리 설명 Demo Controller이다. */
@Controller
@RequestMapping("/demo/error")
public class ExceptionDemoController {

    @GetMapping("/invoke-biz-error")
    public ResponseEntity<String> invokeBizError(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = true;
        if(flag) {
            throw new BaseBizException(BaseBizErrorCode.PASSWORD_NOT_MATCH); 
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);  // MediaType을 설정해야 한다.
        return new ResponseEntity<String>("success", headers,  HttpStatus.OK);
    }//:


    @GetMapping("/invoke-not-login")
    public ResponseEntity<String> invokeNotLogin(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = true;
        if(flag) {
            // 여기서 인가 받지 않은 사용자 예외를 발생 시킨다. 
            throw new BaseBizException(BaseBizErrorCode.LOGIN_REQUIRED); 
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);  // MediaType을 설정해야 한다.
        return new ResponseEntity<String>("success", headers,  HttpStatus.OK);
    }//:


    @GetMapping("/invoke-custom-error")
    public ResponseEntity<String> invokeCustomError(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = true;
        if(flag) {
            // 여기서 기타 오류로 예외를 발생시킨다. 
            throw new BaseBizException(BaseBizErrorCode.LOGIN_REQUIRED); 
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);  // MediaType을 설정해야 한다.
        return new ResponseEntity<String>("success", headers,  HttpStatus.OK);
    }//:


    
    @GetMapping("/system-error")
    public ResponseEntity<String> invokeSystemError(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = true;
        if(flag) {
            // 여기서 기타 오류로 예외를 발생시킨다. 
            throw new RuntimeException("시스템 오류");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);  // MediaType을 설정해야 한다.
        return new ResponseEntity<String>("success", headers,  HttpStatus.OK);
    }//:


}///~
