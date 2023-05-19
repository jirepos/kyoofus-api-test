package basic.java8.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * TimeZone을 변경하는 테스트케이스이다. 
 */
public class ChangeTimeZoneTest {

  /**
   * ZonedDateTime을 java.util.Date로 변환. 
   * 
   * 테스트할 타임존은 다음과 같다.
   * 
   * Asia/Aden (+03:00)
   * America/Cuiaba (-03:00)
   * Etc/GMT+9 (-09:00)
   * Etc/GMT+8 (-08:00)
   * Africa/Nairobi (+03:00)
   * 
   * @param userZoneId 사용자의 ZoneId
   */
  @ParameterizedTest
  @ValueSource(strings = { "Asia/Aden", "America/Cuiaba", "Asia/Seoul" })
  void testZonedDateTimeToDate(String userZoneId) throws Exception {
    Date d = getDate(userZoneId);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    System.out.println("CONV:" + sdf.format(d)); // 출력만 시스템 타임존인 +0900일 뿐 이 값은 무시한다.
  }//

  /**
   * 주어진 시간대의 ZoneOffset을 구한다.
   * 
   * @param zoneId 시간대
   * @return ZoneOffset
   */
  public ZoneOffset getSystemZoneOffset(String zoneId) {
    ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(zoneId));
    return zdt.toOffsetDateTime().getOffset();
  }


  /**
   * UTC 시간대로 변경한다. 
   * @param zdt
   * @return
   */
  public ZonedDateTime toUTCZonedDateTime(ZonedDateTime zdt) {
    return zdt.withZoneSameInstant(ZoneId.of("UTC")); // UTC로 변경
  }


  /**
   * 시간 출력한다. 
   * @param prefix 출력전에 출력할 문자열
   * @param zdt 출력할 시간
   */
  public void printZonedDateTime(String prefix, ZonedDateTime zdt)  {
    System.out.format("%s %s %n", prefix, zdt.format(DateTimeFormatter.ISO_DATE_TIME));  //2021-11-25T19:40:52.1221402+09:00[Asia/Seoul]
  }


  /**
   * ZoneDateTime을 java.util.Date로 변환한다. 부득이하게 DB에
   * java.util.Date를 사용하는 경우에만 사용한다. LocalDatTime을 사용해야 하며
   * 나중에 이 메서드는 삭제한다.
   * 
   * @param zdt     변환할 시간
   * @param pattern 데이트 포맷터가 사용할 패턴
   * @return 변환된 시간
   * @throws ParseException
   */
  @Deprecated
  public Date toDateWithPattern(ZonedDateTime zdt, String pattern) throws ParseException {
    // DateFormatter를 사용하여 문자열로 변환한다.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String formatted = zdt.format(formatter);
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    return format.parse(formatted);
  }

  public Date getDate(String userZoneId) throws ParseException {
    // 사용자의 시간대 시간
    ZonedDateTime userZonedDateTime = ZonedDateTime.now(ZoneId.of(userZoneId));
    printZonedDateTime("사용자 시간:", userZonedDateTime);
    // 사용자의 시간대를 UTC로 변경
    ZonedDateTime utcZonedDateTime = toUTCZonedDateTime(userZonedDateTime);
    printZonedDateTime("UTC 시간:", utcZonedDateTime);
    // Date로 변경
    String pattern = "yyyy-MM-dd HH:mm:ss";
    return toDateWithPattern(utcZonedDateTime, pattern);
  }

}/// ~
