package basic.external.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

public class AntPathMatcheTest {

    
    @Test
    public void matchTest() {
        String pattern = "/demo/websocket/**";
        AntPathMatcher matcher = new AntPathMatcher();
        if(matcher.isPattern(pattern)) { // pattern에 맞는지 검사 
            System.out.println("pattern is pattern");
        }else {
            System.out.println("pattern is not pattern");
        }
        
        String url = "/demo/websocket/server-send/aaa";
        if(matcher.match(pattern, url)) { // url에 맞는지 검사 
            System.out.println("url is match");
        }else {
            System.out.println("url is not match");
        }
    }


    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html

    @Test 
    public void matchTest2() {
            String pattern = "/demo/websocket/**/*.jsp";
        AntPathMatcher matcher = new AntPathMatcher();
        if(matcher.isPattern(pattern)) { // pattern에 맞는지 검사 
            System.out.println("pattern is pattern");
        }else {
            System.out.println("pattern is not pattern");
        }
        
        String url = "/demo/websocket/server-send/bbb.text";
        if(matcher.match(pattern, url)) { // url에 맞는지 검사 
            System.out.println("url is match");
        }else {
            System.out.println("url is not match");
        }
    }
}
