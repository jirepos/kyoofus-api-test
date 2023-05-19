package com.jirepo.demo.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration 
public class WebSocketDemoConfig {

    /** 
     * {@literal @}ServerEndpoint 어노테이션이 달린 클래스들은 WebSocket이 생성될 때마다 인스턴스가 생성되고 JWA에 의해 관리되기 때문에 
     * 스프링의  @Autowired가 설정된 멤버들이 정상적으로 초기화 되지 않습니다. 이때 이를 연결해 주고 초기화해 주는 클래스가 필요합니다.
     */
    @Bean 
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    
}
