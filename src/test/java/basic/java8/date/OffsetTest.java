package basic.java8.date;

import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

/**
 * ZoneOffset 테스트 케이스이다. 
 */
public class OffsetTest {

    /** ZoneOffset 사용한 시간 구하기 */
    @Test
    void testZoneOffset() {

        ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00"); // Seoul
        System.out.println(ZonedDateTime.now(seoulZoneOffset)); // 2021-11-25T16:23:59.080782400+09:00

        ZoneOffset laZonedOffset = ZoneOffset.of("-08:00"); // LA
        System.out.println(ZonedDateTime.now(laZonedOffset)); // 2021-11-24T23:26:35.777549900-08:00
    }

    /** 다른 타임존/시차 적용 */
    @Test
    void testSetTimeZone() {
        // 서울 타임존의 시간 설정
        ZonedDateTime seoul = Year.of(2021).atMonth(11).atDay(25).atTime(16, 43).atZone(ZoneId.of("Asia/Seoul"));
        // UTC로 변경
        ZonedDateTime utc = seoul.withZoneSameInstant(ZoneOffset.UTC);
        // 9시간 차이가 나니까, 07시가 된다.
        System.out.println(utc); // 2021-11-25T07:43Z
        // 14시간 차이
        ZonedDateTime toronto = seoul.withZoneSameInstant(ZoneOffset.of("-0500"));
        System.out.println(toronto); // 2021-11-25T02:43-05:00
    }// :

}
