package basic.java8.date;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

/**
 * ZonedDateTime 테스트 케이스이다.
 */
public class ZonedDateTimeTest {

    /**
     * 현재 시간 구하기
     * 
     * @param zoneId 타임존 아이디
     * @return 현재 시간
     */
    public ZonedDateTime now(String zoneId) {
        return ZonedDateTime.now(ZoneId.of(zoneId));
    }

    /** UTC 현재 시간 구하기 */
    public ZonedDateTime nowUTC() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }

    /**
     * 다른 시간대의 ZoneDateTieme으로 변경한다.
     * 
     * @param zdt    변환할 시간
     * @param zoneId 변경할 타임존
     * @return 변환된 시간
     */
    public ZonedDateTime toAnotherZonedDateTime(ZonedDateTime zdt, String zoneId) {
        return zdt.withZoneSameInstant(ZoneId.of(zoneId));
    }

    /** 현재 시간 구하기 */
    @Test
    public void testCurrentTime() {
        // 특정 시간대 현재 시간
        String zoneId = "Asia/Seoul";
        ZonedDateTime zdt = now(zoneId);
        System.out.println(zdt);

        // UTC 현재 시간
        ZonedDateTime utc = nowUTC();
        System.out.println(utc);
    }

    /** 시간대 변경하기 */
    @Test
    public void testToAnotherTimeZone() {
        // 특정 시간대 현재 시간
        String zoneId = "Asia/Seoul";
        ZonedDateTime zdt = now(zoneId);
        System.out.println(zdt);

        // UTC 현재 시간
        ZonedDateTime utc = toAnotherZonedDateTime(zdt, "UTC");
        System.out.println(utc);
    }

    @Test
    void testZonedDateTime() {
        // 시스템 시간대 현재 시간
        ZonedDateTime nowLocal = ZonedDateTime.now();
        System.out.println(nowLocal); // 2021-11-25T16:04:19.396320300+09:00[Asia/Seoul]

        ZonedDateTime curSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println(curSeoul); // 2021-11-25T16:04:53.223393100+09:00[Asia/Seoul]

        ZonedDateTime specDt = ZonedDateTime.of(2010, 7, 5, 10, 10, 10, 25, ZoneId.of("Asia/Seoul"));
        System.out.println(specDt); // 2010-07-05T10:10:10.000000025+09:00[Asia/Seoul]
    }

}/// ~
