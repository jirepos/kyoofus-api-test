package com.jirepo.core.yaml;

import java.util.List;

import org.junit.jupiter.api.Test;

public class YamlPropertiesTest {

     /**
     * 
     */
    @Test
    public void testYamlPropertiesImport() {

        String path = "test-spring-profile.yml";
        YamlProperties properties = YamlProperties.createInstance(path, "local", "sqllog");
        System.out.println(properties.getProperty("server-url"));
        @SuppressWarnings("unchecked")
        List<String> options  = (List<String>)properties.getProperty("login-option");
        if(options != null) {  // null 체크할 것 
            for(String option : options) {
                System.out.println(option);
            }
        }
        
        //@SuppressWarnings("unchecked")
        boolean corsEnable = (Boolean)properties.getProperty("cors-enable");
        System.out.println(corsEnable);
        //  null이면 캐스팅 오류 발생하므로 디폴트 값을 전달하는 것이 좋다 
        int serverPort = (Integer)properties.getProperty("server.port", 80);
        System.out.println(serverPort);
        double firstValue  = (double)properties.getProperty("first-value", 0);
        System.out.println(firstValue);
        String userName = (String)properties.getProperty("jdbc.username"); 
        System.out.println(userName);
        String serverUrl = (String)properties.getProperty("server-url"); 
        System.out.println(serverUrl);
        // @SuppressWarnings("unchecked")
        // List<String> importArr = (List<String>)properties.getProperty("spring.config.import"); 
        // System.out.println(importArr);
        String messengerDomain = (String)properties.getProperty("messenger-domain"); 
        System.out.println(messengerDomain);
        String jdbcUrl = (String)properties.getProperty("jdbc.url"); 
        System.out.println(jdbcUrl);
        String myMessasge = (String)properties.getProperty("my-message.hello"); 
        System.out.println(myMessasge);

    }
    
}
