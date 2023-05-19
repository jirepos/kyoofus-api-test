package com.jirepo.demo.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


/** 다국어 메시지 처리 데모 컨트롤러이다. */
@RestController
@RequestMapping("/demo/i18n")
@Slf4j
public class I18nDemoController {

    /** AppConfig에서 생성한 MessageSource 객체가 주입된다.  */
    @Autowired
    private MessageSource messageSource;

    /**
     * 다국어 메시지를 조회한다. 
     * @return 다국어 메시지를 반환한다. 
     */
    @GetMapping("/message-source")
    public ResponseEntity<String> message(HttpServletRequest request, HttpServletResponse response) {

        log.debug(messageSource.getMessage("common.error.msg", null, Locale.KOREA));
        log.debug(messageSource.getMessage("greeting", null, Locale.KOREA));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);  // MediaType을 설정해야 한다.

        return new ResponseEntity<String>("success", headers,  HttpStatus.OK);
    }//:   
}
