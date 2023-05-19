package com.jirepo.demo.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.demo.request.ResponseDemoBean;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring Event Handling을 테스트하기 위한 컨트롤러
 */
@Controller
@RequestMapping("/demo/event")
@Slf4j
public class EventDemoController {

    /** 이벤트 퍼블리싱을 위한 객체 주입 */
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /** index page */
    @GetMapping("/")
    public String index() {
        return "event/event";
    }

    /** applicatin/json 반환 */
    @GetMapping("/get-json1")
    public ResponseEntity<ResponseDemoBean> getJson1(HttpServletRequest request, HttpServletResponse response) {

        // DB에서 읽어 왔다고 가정하고 
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName("홍길동(Gildong,Hong");
        bean.setAge(10);
        
        // 읽음 처리는 Event 처리 
        DemoEvent event = new DemoEvent();
        event.setEventName("DemoEvent1");
        this.eventPublisher.publishEvent(event); // 이벤트 발행 
        log.debug("event를 발행했어요.");

        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :

    
    /** applicatin/json 반환 */
    @GetMapping("/get-json2")
    public ResponseEntity<ResponseDemoBean> getJson2(HttpServletRequest request, HttpServletResponse response) {

        // DB에서 읽어 왔다고 가정하고 
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName("홍길동(Gildong,Hong");
        bean.setAge(10);
        
        // 읽음 처리는 Event 처리 
        DemoEvent event = new DemoEvent();
        event.setEventName("DemoEvent2");
        this.eventPublisher.publishEvent(event); // 이벤트 발행 
        log.debug("event를 발행했어요.");

        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :

}
