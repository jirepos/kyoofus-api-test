package com.jirepo.demo.event;

import lombok.Getter;
import lombok.Setter;

/** Spring Event Handling을 테스트하기 위한 Event  */
@Getter
@Setter
public class DemoEvent {
    /** 이벤트 이름 */
    private String eventName;
    /** 사용자 이름 */
    private String userName; 
    
}
