package com.jirepo.demo.websocket;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerEndpointConfiguratorDemo extends javax.websocket.server.ServerEndpointConfig.Configurator implements ApplicationContextAware {

    // volatile 키워드는 java 변수를 Main Memory에 저장하겠다 라는 것을 명시한다.
    // 매번 변수의 값을 읽을 때마다 CPU cache에 저장된 값이 아닌 Main Memory에서 읽는 것이다.
    // 또한 변수의 값을 쓸 때마다 Main Memory까지 작성한다.



    private static volatile BeanFactory context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServerEndpointConfiguratorDemo.context = applicationContext;
    }
}