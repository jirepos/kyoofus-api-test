package basic.java8.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

/**
 * ZoneID 테스트 케이스이다.
 */
public class ZoneIdTest {

    /** ZoneId 사용한 시간 구하기 */
    @Test
    void testZoneIdTset() {
        ZoneId vancouverZoneId = ZoneId.of("America/Vancouver"); // 여기는 DST사용
        System.out.println(ZonedDateTime.now(vancouverZoneId)); // 2021-11-24T23:29:54.191862300-08:00[America/Vancouver]
    }



    /** ZoneId 출력 */
    @Test
    void testListZoneIds() {
        LocalDateTime localDateTime = LocalDateTime.now();
        // 사용 가능한 시간대를 구한다. 
        for (String zoneId : ZoneId.getAvailableZoneIds()) {
            ZoneId id = ZoneId.of(zoneId);
            // LocalDateTime을 ZonedDateTime으로 변경 한다. 
            ZonedDateTime zonedDateTime = localDateTime.atZone(id);
            // 해당 시간대의 offset을 구한다. 
            ZoneOffset zoneOffset = zonedDateTime.getOffset();
            System.out.println(zoneOffset.getId());
            // replace Z to +00:00
            String offset = zoneOffset.getId().replaceAll("Z", "+00:00");
            System.out.format("%s (%s)\n", id.getId(), offset);
        }
        /*
         * Asia/Aden (+03:00)
         * America/Cuiaba (-03:00)
         * Etc/GMT+9 (-09:00)
         * Etc/GMT+8 (-08:00)
         * Africa/Nairobi (+03:00)
         * 
         * ... 생략 ....
         */
    }

    /** 
     * OffsetId를 사용하여 ZoneOffset을 구한다. 
     */
    @Test 
    void testZoneOffset() {
      ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00"); // Seoul
      System.out.println(ZonedDateTime.now(seoulZoneOffset));  //2021-11-25T16:23:59.080782400+09:00
  
      ZoneOffset laZonedOffset = ZoneOffset.of("-08:00"); // LA
      System.out.println(ZonedDateTime.now(laZonedOffset));  // 2021-11-24T23:26:35.777549900-08:00
    }
}
