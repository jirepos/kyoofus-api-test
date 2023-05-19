package com.jirepo.demo.websocket;

import org.springframework.stereotype.Component;

@Component
public class WebSocketDaoDemo {

    public void sendMessage(String message) {
       System.out.println("WebSocketDaoDemo sendMessage : " + message);
    }
    
}///~
