package basic.java8.date;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Date Format 테스트 케이스이다. 
 */
public class DateFormatTest {

    @Test
    public void test() {
        // Locale.ENGLISH일 때 2022-08-24 PM 15:02:10.088+0900
        String pattern = "yyyy-MM-dd aaa HH:mm:ss.SSSZ"; // 2022-08-24 오후 14:59:11.573+0900
        Date nowDate = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        // 원하는 데이터 포맷 지정
        String strNowDate = simpleDateFormat.format(nowDate);
        // 지정한 포맷으로 변환
        System.out.println("포맷 지정 후 : " + strNowDate);

    }

    /**
     * SimpleDateFormat을 사용하여 날짜 문자열을 java.util.Date로 변환한다.
     * @throws Exception
     */
    @Test 
    public void testSimpleDateFormat() throws Exception  {
        String pattern = "yyyy-MM-dd HH:mm:ss"; 
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // parse()를 이용하여 java.util.Date로 변환한다.
        Date date = format.parse("2019-01-01 12:00:00");
        System.out.println(date);
    }    

    /** 문자열 변환 Test */
    @Test
    void testTimeZoneToString() {
        // Format 형식은 아래 URL 참조
        // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/format/DateTimeFormatter.html
        ZonedDateTime seoul = Year.of(2021).atMonth(11).atDay(25).atTime(16, 43).atZone(ZoneId.of("Asia/Seoul"));

        System.out.println(seoul.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 2021-11-25T16:43:00+09:00
        System.out.println(seoul.format(DateTimeFormatter.ISO_DATE_TIME)); // 2021-11-25T16:43:00+09:00[Asia/Seoul]
        System.out.println(seoul.format(
                DateTimeFormatter.ofPattern("yyyy/MM/dd/ HH:mm:ss z"))); // 2021/11/25/ 16:43:00 KST
        System.out.println(seoul.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL))); // 2021년 11월 25일 목요일 오후 4시 43분 0초 대한민국 표준시
        System.out.println(seoul.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL).withLocale(Locale.KOREA))); // 2021년 11월 25일 목요일
                                                                                                    // 오후 4시 43분 0초 대한민국
                                                                                                    // 표준시
        // 2020-05-11T02:00:00.000Z
        // DateTimeFormatter.iso

        ZonedDateTime toronto = seoul.withZoneSameInstant(ZoneOffset.of("-0500"));
        // ZoneId가 없으면 Zone을 표시하지 못한다.
        System.out.println(toronto.format(DateTimeFormatter.ISO_DATE_TIME)); // 2021-11-25T02:43:00-05:00
        System.out.println(toronto.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 2021-11-25T16:43:00+09:00
        ZonedDateTime toronto2 = seoul.withZoneSameInstant(ZoneId.of("America/Toronto"));
        System.out.println(toronto2.format(DateTimeFormatter.ISO_DATE_TIME)); // 2021-11-25T02:43:00-05:00[America/Toronto]
        System.out.println(toronto2.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 2021-11-25T02:43:00-05:00

    }// :

    /** 문자열을 ZonedDateTime으로 변환 */
    @Test
    void testStringToZoneDateTime() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2021-11-25T16:43:00+09:00[Asia/Seoul]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2021/11/25/ 16:43:00 KST",
                DateTimeFormatter.ofPattern("yyyy/MM/dd/ HH:mm:ss z"));
        System.out.println(zdt1);
        System.out.println(zdt2);
    }// :

}
