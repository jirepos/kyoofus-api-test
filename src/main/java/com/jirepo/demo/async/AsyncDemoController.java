package com.jirepo.demo.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * 비동기 메서드 실행 데모 컨트롤러이다. 
 */
@Controller
@RequestMapping("/demo/async")
@Slf4j
public class AsyncDemoController {

    @Autowired
    private AsyncDemoService asyncDemoService;

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Response { 
        private String message; 
    }

    /** 오래 걸리는 작업 요청 */
    @GetMapping("/do-long-task")
    public ResponseEntity<Response> doLongTask() {
        log.debug("before doLongTask");
        while(true) { // 방어코딩
            try {
                asyncDemoService.doLongTask();  // 오래 걸리는 작업을 비동기로 실행한다.
                log.debug("doLongTask() is called");
                break; // 정상 처리되면 루프를 빠져나간다.
            } catch (Exception e) {
                log.error("doLongTask error", e);
                try {
                    Thread.sleep(1000); // 대기 후 다시 시도한다.
                } catch (InterruptedException e1) {
                    // ignore
                }
            }
        }
        return ResponseEntity.ok(new Response("OK"));
    }

}
