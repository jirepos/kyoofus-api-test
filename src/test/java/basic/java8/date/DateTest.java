package basic.java8.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * java.util.Date의 UTC 변환에 대한 테스트케이스이다. 
 */
public class DateTest {

    /**
     * java.util.Date를 ZonedDateTime으로 변환한다. java.util.Date는 서버 시간대 정보를 가지고 있기 때문에 
     * 사용자의 시간대와 동일한  시간대를 설정해야 한다. 다르면 서버시간대의 차이를 계산하여 시간이 바뀐다. 
     * @param date  변환할 시간 
     * @param zoneId  변환할 시간대 
     * @return 변환된 시간 
     */
    public ZonedDateTime toZonedDateTime(Date date, String zoneId) {
        return date.toInstant().atZone(ZoneId.of(zoneId));
    }
    
    /**
     * UTC 시간대의 ZoneDateTime으로 변환한다. 
     * @param zdt  변환할 시간
     * @return  변환된 시간
     */
    public ZonedDateTime toUTCZonedDateTime(ZonedDateTime zdt) {
        return zdt.withZoneSameInstant(ZoneId.of("UTC"));
    }

    /**
     * 다른 시간대의 ZoneDateTieme으로 변경한다. 
     * @param zdt 변환할 시간
     * @param zoneId 변경할 타임존 
     * @return  변환된 시간
     */
    public ZonedDateTime toAnotherZonedDateTime(ZonedDateTime zdt, String zoneId) {
        return zdt.withZoneSameInstant(ZoneId.of(zoneId));
    }


    /**
     * ZonedDateTime을 Date로 변경한다. UTC를 java.util.Date로 변환하면 
     * 다시 서버 시간대로 변환되기 때문에 이 값을 그대로 DB에 넣으면 UTC 시간값이 아니다. 
     * 사용하면 안된다. 
s    * @param zdt 변환할 시간
     * @return  변환된 시간 
     * @deprecated  사용하지 않는다. 
     */
    @Deprecated
    public Date toDate(ZonedDateTime zdt) {
        return Date.from(zdt.toInstant());
    }

    /**
     * ZoneDateTime을 java.util.Date로 변환한다. 부득이하게 DB에 
     * java.util.Date를 사용하는 경우에만 사용한다. LocalDatTime을 사용해야 하며
     * 나중에 이 메서드는 삭제한다.  
     * @param zdt 변환할 시간 
     * @param pattern  데이트 포맷터가 사용할 패턴 
     * @return 변환된 시간 
     * @throws ParseException
     */
    @Deprecated
    public Date toDateWithPattern(ZonedDateTime zdt, String pattern) throws ParseException {
        // DateFormatter를 사용하여 문자열로 변환한다. 
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern(pattern); 
        String formatted = zdt.format(formatter);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(formatted);
    }


    /**
     * 사용자의 시간대 시간을 UTC 시간대의 Date로 변경 
     */
    @Test
    public void testToUTC() throws Exception  {
        // 사용자가 입력한 시간은 사용자의 시간대 
        String pattern = "yyyy-MM-dd HH:mm:ss"; 
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // parse()를 이용하여 java.util.Date로 변환한다.
        Date date = format.parse("2022-08-25 08:35:00");
        System.out.println(date);


        // Date를 사용자의 시간대를 사용하여 ZonedDateTime으로 변환한다.
        // 반드시 사용자의 시간대와 동일해야 한다. 
        // java.util.Date는 시스템의 시간대를 기준으로 하기 때문에 
        // 다른 시간대를 지정하면 시간이 변경된다. 
        ZonedDateTime zonedDateTime = toZonedDateTime(date, "Asia/Seoul");
        System.out.println(zonedDateTime);

        // 사용자가 입력한 시간을 UTC로 변환한다.
        ZonedDateTime utcZonedDateTime = toUTCZonedDateTime(zonedDateTime);
        System.out.println(utcZonedDateTime);

        // DateFormatter를 사용하여 문자열로 변환한다. 
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern(pattern); 
        String formatted = utcZonedDateTime.format(formatter);
        System.out.println(formatted);
        // 다시 Date로 변환한다 

        // java.util.Date를 DB에 입력하기 위해 변환된 UTC 시간값 
        // 시간대는 무시해야 한다. 
        Date dateUtc = toDateWithPattern(utcZonedDateTime, pattern);
        System.out.println(dateUtc);
    }



    /**
     * 사용자의 시간대를 UTC로 변경하여 DB에 넣는다. java.util.Date를 사용한다. 
     * @throws Exception
     */
    @Test 
    public void testUserTimeToUTCWithDate() throws Exception {
        // 사용자가 입력한 시간은 사용자의 시간대 
        String pattern = "yyyy-MM-dd HH:mm:ss"; 
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        // parse()를 이용하여 java.util.Date로 변환한다.
        Date date = format.parse("2022-08-25 08:35:00");
        System.out.println(date);
        //Thu Aug 25 08:35:00 KST 2022
        // 출력 결과를 보면 서버시간인 Asia/Seoul로 변경되었다. 
        // 하지만 날짜와 시간값은 유지한다. 
        // 여기서 시간대는 무시한다. 
        // 이 시간은 사용자가 설정한 시간대의 날짜이기 때문에 DB에 넣으면 UTC 값이 아니게 된다. 
        // 우선 Date를 사용하지 말고 LocalDate를 사용하도록 한다. 
        String dateStr = "2022-08-25 08:35:00";
        LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(ldt); // 2022-08-25T08:35
        // 결과를 보면 시간 변경이 없다는 것을 확인할 수 있다. 
        // 이것을 사용자의 시간대로 변경한다. 
        ZonedDateTime zonedDateTime = ZonedDateTime.of(ldt, ZoneId.of("Asia/Seoul"));
        // 다시, 이것을 UTC로 변환한다. 
        ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(utcZonedDateTime); // 2022-08-24T23:35Z[UTC]
        // 이제 원하는 값을 얻었다. 이것을 다시 Date로 변환한다. 
        String utcStr = utcZonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Date utcDate = format.parse(utcStr);
        System.out.println(utcDate); // Wed Aug 24 23:35:00 KST 2022
        // 결과 값을 DB에 저장하면 끝이다. 

    }//


     /**
     * UTC를 사용자의 시간대로 변경한다. java.util.Date를 사용한다. 
     * @throws Exception
     */
    @Test 
    public void testUTCToUserWithDate() throws Exception {

        String pattern = "yyyy-MM-dd HH:mm:ss"; 
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        // 아래의 값이 DB에서 가져온 시간값이라고 가정한다. 
        Date date = format.parse("2022-08-24 23:35:00");
        System.out.println(date);
        // 시간대가 서버 시간대인 Asia/Seoul로 표시되지만 
        // DB에 있는 값은 UTC이다. 
        // 이것을 UTC로 변경해야 하는데 SimpleDateFormat을 사용한다. 
        String utcDateStr = format.format(date); 
        // 다음과 같이 변경되었다. 
        System.out.println(utcDateStr); // 2022-08-24 23:35:00
        // 이것을 LocalDateTime으로 변경한다. 
        LocalDateTime utcLdt = LocalDateTime.parse(utcDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(utcLdt); // 2022-08-24T23:35
        // LocalDateTime에 TimeZone을 설정한다. 
        ZonedDateTime utcZonedDateTime = utcLdt.atZone(ZoneId.of("UTC"));
        System.out.println(utcZonedDateTime); //2022-08-24T23:35Z[UTC]
        // 이것을 사용자의 시간대로 변경한다. 
        ZonedDateTime userZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
        System.out.println(userZonedDateTime); // 2022-08-25T08:35+09:00[Asia/Seoul]
        // 이 값을 시리얼라이즈 한다. 
        String returnStr = utcZonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(returnStr); // 2022-08-24 23:35:00

    }//




}///~
