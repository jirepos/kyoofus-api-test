package com.jirepo.demo.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// @Component 어노테이션이 달린 클래스는 스프링 빈에 등록되고 그 인스턴스는 싱글톤으로 스프링에 의해 관리되지만 
// , @SeverEndPoint로 어노테이션이 달린 클래스는 WebSocket이 연결될 때마다 인스턴스가 생성되고 JWA구현에 의해 관리가 되어 
//  내부의 @Autowried가 설정된 멤버가 정상적으로 초기화 되지 않는다.
// @Autowried를 사용하기 위해서 SeverEndpointConfig.Configurator를 사용하여
// SeverEndPoint의 컨텍스트에 BeanFactory 또는 ApplicationContext를 연결해 주는 작업을 하는 클래스를 생성한다.
/**
 * WebSoekct 처리 클래스이다. 
 */
@Service
@ServerEndpoint(value="/websocket/chat", configurator = ServerEndpointConfiguratorDemo.class)
public class WebSocketDemo {

    /** 이거 주입이 안된다. 그래서 ServerEndpointConfiguratorDemo 를 구현한다. 
     *  그리고 @ServerEndPoint에 configurator를 지정해준다.
      */
    @Autowired
    private WebSocketDaoDemo webSocketDaoDemo; 


    /**
     * 웹소캣 세션. synchronizedSet은 Thread-Safe 합니다.
     */
    private static Set<Session> sessionSet = Collections.synchronizedSet(new HashSet<Session>());
    public static  Set<Session> getSessionSet() {
        return sessionSet;
    }

    
    /**
     * 웹소캣 연결 
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        if(session == null) return; 
        System.out.println("onOpen : " + session.getId());
        sessionSet.add(session);
    }


    /** 
     * 웹소켓 메시지 수신 
     */
    @OnMessage    
    public String onMessage(String message, Session session) {
        if(session == null) return null; 
        System.out.println("onMessage : " + message);
         this.webSocketDaoDemo.sendMessage(message);

        sendMesssageToAll(message);
        return null; 
    }

    /** 웹소켓 연결 해제 */
    @OnClose
    public void onClose(Session session) {
        if(session == null) return; 
        System.out.println("onClose : " + session.getId());
        sessionSet.remove(session);
    }


    @OnError
    public void onError(Throwable t) { 
        t.printStackTrace();
    }
    
    public static void sendMesssageToAll(String message) {
        if(sessionSet == null) return;
        if(sessionSet.size() < 1) return; 
        for(Session session : sessionSet) {
            try {
                //session.getBasicRemote().sendText(message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//:

}////~
