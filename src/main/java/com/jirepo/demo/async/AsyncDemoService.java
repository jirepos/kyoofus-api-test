package com.jirepo.demo.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsyncDemoService {

    // AsyncConfig에서 생성한 빈의 이름을 설정한다. 
    @Async("asyncExecutor")
    public void doLongTask() {
        try {
            Thread.sleep(5000);
            log.debug(("this is a long task"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
