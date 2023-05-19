package com.jirepo.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/** Demo Event Listener 클래스이다. */
@Component
@Slf4j
public class DemoEventListener {

    @EventListener
    @Async
    public void onDemoEvent(DemoEvent event) throws Exception {
        System.out.println("이벤트를 받았어요:" + event.getEventName());
        for(int i=1; i <= 100; i++) {
            log.debug("{} {} 처리중.....", event.getEventName(), i);
            Thread.sleep(200);
        }
    }
    
}
