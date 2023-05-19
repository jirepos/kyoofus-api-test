package com.jirepo.core.mybatis;

import lombok.Getter;
import lombok.Setter;

/**
 * Inner static class를 생성할 수 있는지 테스트하기 위한 클래스이다. 
 */
@Getter
@Setter
public class HandlerTestBean {
    private String age; 

    public static class Handler implements HandlerTestIntf { 
        public Handler() {}
        public String getName() {
            return "hello"; 
        }
    }
}
