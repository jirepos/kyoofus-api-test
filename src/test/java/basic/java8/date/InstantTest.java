package basic.java8.date;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Instant 테스트 케이스이다. 
 */
public class InstantTest {

    @Test
    void testInstant() {

        Instant epoch = Instant.EPOCH; // Instant.ofEpochSecond(0); 와 동일
        System.out.println(epoch); // 1970-01-01T00:00:00Z

        Instant futureEpoch = Instant.ofEpochSecond(1000_000_000);
        System.out.println(futureEpoch); // 2001-09-09T01:46:40Z

        Instant pastEpoch = Instant.ofEpochSecond(-1_000_000_000);
        System.out.println(pastEpoch); // 1938-04-24T22:13:20Z
    }


    @Test
    void testInstantNow() {

        Instant now = Instant.now();
        System.out.println(now); // 2021-11-25T06:59:29.930470500Z
        
        long epochSecond = now.getEpochSecond(); // 초 단위 타임스탬프
        System.out.println(epochSecond); // 1637823569

        long epochMilli = now.toEpochMilli(); // 밀리세컨드 단위 타임 스탬프
        System.out.println(epochMilli); // 1637823569930
    }

}
