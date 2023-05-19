package basic.java8.string;

import org.junit.jupiter.api.Test;

public class StringTest {
    
    @Test
    public void testSplit() {
        String str = "server.name:8080"; 
        String[] arr = str.split(":");
        System.out.println(arr.length);

    }
}
