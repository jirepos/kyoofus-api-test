package com.jirepo.core.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateTimeUtilsTest {


    /**
     * DB에 insert, update하기 위해서 UTC 기준 현재 시간을 사용한다. 
     */
    @Test 
    public void testCurrentDateTime(){ 
        LocalDateTime now = DateTimeUtils.getUTCDateTime();
        System.out.println(now);
        // 2022-09-02T02:39:57.447645800
    }


    /**
     * UTC를 사용자 시간으로 변경하기 
     */
    @Test 
    public void testUTCToUserTime() {
        // MyBatis에서 LocalDateTime에 바인딩 된 값
        // DB는 UTC로 보관되어 있다. 
        // JavaBean에는 LocalDateTime으로 선언되어 있다.
        LocalDateTime utcDateVal =  /* DB에서 가져왔다고 가정한다.*/ DateTimeUtils.getUTCDateTime();
        System.out.println(utcDateVal);
        // 2022-09-02T02:39:57.447645800

        // 여기서부터는 Jackson ObjectMapper의 LocalDateTimeSerializer를 사용하여 처리한다. 
        //
        // LocalDateTime은 시간대 정보가 없으므로 시간대를 설정한다. 
        ZonedDateTime javaBeanFieldVal = ZonedDateTime.of(utcDateVal, ZoneId.of("UTC"));
        // 사용자의 타임존 아이디 정보를 세션에서 읽어 TimeZone을 변경한다. 
        ZonedDateTime usreZoneDateTime  = javaBeanFieldVal.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        // 시간대가 변경되고 시간이 바뀐다. 
        System.out.println(usreZoneDateTime);
        // 이것을 LocalDateTime으로 변경한다. 
        LocalDateTime rerturnVal = usreZoneDateTime.toLocalDateTime();
        // 이것을 표준 포맷으로 시리얼 라이즈 한다. 
        String dateStr = rerturnVal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        System.out.println(dateStr);

    }//:

    /**
     * UTC를 사용자 시간으로 변경하기 
     */
    @Test 
    public void testUTCToUserTime2() {
        LocalDateTime utcDateVal =  /* DB에서 가져왔다고 가정한다.*/ DateTimeUtils.getUTCDateTime();
        System.out.println(utcDateVal);  // DB 시간 출력
        ZonedDateTime javaBeanFieldVal = DateTimeUtils.toZonedDateTime(utcDateVal, "UTC");  // 타임존 설정
        ZonedDateTime usreZoneDateTime = DateTimeUtils.changeZone(javaBeanFieldVal, "Asia/Seoul");  // 타임존 변경 
        LocalDateTime returnVal = usreZoneDateTime.toLocalDateTime(); // LocalDateTime으로 변경
        String format = "yyyy-MM-dd'T'HH:mm:ss";  // 데이트 포맷
        String dateStr = returnVal.format(DateTimeFormatter.ofPattern(format)); // 포맷팅 
        System.out.println(dateStr);  // 시리얼라이즈할 값 
    }


    /**
     * 시간값 문자열을 Date로 변환하는 테스트 
     * @throws Exception
     */
    @Test
    public void testParseToDate() throws Exception { 
        String dateStr = "2021-09-02 02:39:57";
        Date date = DateTimeUtils.parseToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
        System.out.println(date);
    }
    /** 시간값 문자열을 LocalDateTime으로 변환하는 테스트 */
    @Test
    public void testParseToLocalDateTime() throws Exception { 
        String dateStr = "2021-09-02 02:39:57";
        LocalDateTime ldt = DateTimeUtils.parseToLocalDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
        System.out.println(ldt);
    }


    @Test 
    public void testFormat() {
        String pattern = "yyyy-MM-dd aaa hh:mm:ss";
        Date d = Date.from(Instant.now());
        String s = DateTimeUtils.format(d, pattern);
        System.out.println(s);
    }






    // /** 주어진 날짜가 속한 주의 월요일과 일요일을 구한다.  */
    // @Test
    // public void testWeekDays() {
    //     LocalDate today = LocalDate.now();
    //     LocalDate[] dates = DateTimeUtils.getStartAndEndDate(today);
    //     System.out.println(dates[0]);
    //     System.out.println(dates[1]);
    // }//:



}///~

