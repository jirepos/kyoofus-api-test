package basic.java8.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

/**
 * LocalDateTime 테스크 케이스이다. 
 */
public class LocalDateTimeTest {


    // Local Zone
    /** 현재 날짜 구하기 */
    @Test
    void testLocalDateNow() {
        LocalDate today = LocalDate.now();
        // 시간 값 정보 없음
        System.out.println(today); // 2021-11-25
        LocalDate myDate = LocalDate.of(2001, 9, 3);
        System.out.println(myDate); // 2001-09-03
        LocalDate christMas = LocalDate.parse("2021-12-25");
        System.out.println(christMas); // 2021-12-25
    }// :

    /** 현재 시간 구하기 */
    @Test
    void testLocalTimeNow() {
        LocalTime currentTime = LocalTime.now();
        System.out.println(currentTime); // 15:10:55.358240200
        LocalTime curParis = LocalTime.now(ZoneId.of("Europe/Paris"));
        System.out.println(curParis); // 07:11:26.926995500
        LocalTime aTime = LocalTime.of(21, 20, 0);
        System.out.println(aTime); // 21:20
        LocalTime plusTime = aTime.plusHours(8);
        System.out.println(plusTime); // 05:20
    }

    /**
     * LocalDateTime을 ZoneDateTime으로 변경한다.
     */
    @Test
    public void testAtZone() {
        // LocalDateTime은 타임존 정보가 없다.
        LocalDateTime localDateTime = LocalDateTime.now();
        // LocalDateTime을 atZone()을 사용하여 시간대를 부여하여 ZonedDateTime으로 변경한다.
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println(zdt);
    }    

    /** LocalDateTime Test */
    @Test
    void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now); // 2021-11-25T15:26:51.886868600
        LocalDateTime now2 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println(now2);// 2021-11-25T15:26:51.886868600

        LocalDateTime dt1 = LocalDateTime.parse("2020-12-31T23:59:59.999");
        System.out.println(dt1); // 2020-12-31T23:59:59.999
        LocalDateTime dt2 = LocalDateTime.of(2022, 5, 6, 15, 30, 00);
        System.out.println(dt2); // 2022-05-06T15:30
        LocalDateTime dt3 = Year.of(2022).atMonth(5).atDay(6).atTime(15, 30);
        System.out.println(dt3); // 2022-05-06T15:30
    }

    @Test
    void testLocalDateUtility() {
        LocalDate now = Year.of(2021).atMonth(11).atDay(24);
        System.out.println(now); // 2021-11-24
        System.out.println(now.getYear()); // 2021
        System.out.println(now.getMonth()); // NOVEMBER
        System.out.println(now.getDayOfMonth()); // 24
        System.out.println(now.getDayOfWeek()); // WEDNESDAY
        System.out.println(now.isLeapYear()); // false , 윤년아님
        System.out.println(now.plusYears(1)); // 2022-11-24
        System.out.println(now.plusMonths(1)); // 2021-12-24
        System.out.println(now.plusDays(1)); // 2021-11-25

        LocalDate yesterday = now.minusDays(1);
        System.out.println(now.isAfter(yesterday)); // true
        System.out.println(now.isBefore(yesterday)); // false
    }

    @Test
    void testLocalDateTimeUtility() {
        LocalDate date = Year.of(2021).atMonth(11).atDay(24);
        LocalDateTime now = date.atTime(21, 50, 10, 888);
        System.out.println(now); // 2021-11-24T21:50:10.000000888
        System.out.println(now.getHour()); // 21
        System.out.println(now.getMinute()); // 50
        System.out.println(now.getSecond()); // 10
        System.out.println(now.getNano()); // 888
    }

    /** LocalDateTime에 타임존 정보 설정 */
    @Test
    void testTimeDiff() {
        LocalDate ld = LocalDate.of(2021, 11, 25);
        LocalTime lt = LocalTime.of(16, 30);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);

        // LocalDateTime에 타임존 설정
        ZonedDateTime zonedDateTime = ZonedDateTime.of(ldt, ZoneId.of("Asia/Seoul"));
        System.out.println(zonedDateTime); // 2021-11-25T16:30+09:00[Asia/Seoul]

        System.out.println(zonedDateTime.toLocalDateTime()); // 2021-11-25T16:30
        System.out.println(zonedDateTime.toLocalDate()); // 2021-11-25
        System.out.println(zonedDateTime.toLocalTime()); // 16:30
    }



}/// ~
