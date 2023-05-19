package basic.external.ical;

import java.util.ArrayList;
import java.util.List;

// import com.google.ical.compat.jodatime.DateTimeIterator;
// import com.google.ical.compat.jodatime.DateTimeIteratorFactory;
// import com.google.ical.values.RRule;

// import org.joda.time.DateTime;
// import org.junit.jupiter.api.Test;

/** 반복일정 테스트 */
public class RecurrenceTest {

  // DateTime createDateTime(String dateStr) {
  //   //  date = new DateTime("2021-10-29T06:30:00.000+08:00");
  //   return new DateTime(dateStr);
  // }


  // /**
  //  * 반복일정 만들기 기초 (1) 
  //  * @throws Exception
  //  */
  // @Test
  // void testCreateRrule() throws Exception  {
  //   // 매주 월요일 1주 간격으로 20번 반복일정을 만들어 낸다. 
  //   // 필요한 클래스
  //   //  - Rrule 
  //   //  - DateTime
  //   //  - DateTimeIteratorFactory
  //   // 
  //   // 반복일정 생성 
  //   // - DateTimeIteratorFactory.createDateTimeIterator() 
  //   // 
  //   // 매주, 1주간격, 20번 
  //   String rrulestr = "RRULE:FREQ=WEEKLY;BYDAY=MO;INTERVAL=1;COUNT=20";  // 매주 월요일 , 20번 
  //   RRule rrule = new RRule(rrulestr); // to create RRule instance 
  //   DateTime iterateFrom = new DateTime("2021-10-22T06:30:00.000+09:00");   // 10월 22일부터(22일 포함)
  //   System.out.println(iterateFrom.toString());
  //   System.out.println(iterateFrom.getZone().toString());  // TimeZone을 설정해도 Asia/Seoul임 
  //   System.out.println(">>>");
  //   DateTimeIterator it = DateTimeIteratorFactory.createDateTimeIterator(
  //      rrule.toIcal()  // rrule 
  //     ,iterateFrom     // 언제부터 반복일정을 생성할 것인지 
  //     ,iterateFrom.getZone() // 반복일정의 타임존 
  //     , true);
  //     List<DateTime> occurrences = new ArrayList<>();
  //     while (it.hasNext()) {
  //       DateTime n = it.next().withZone(iterateFrom.getZone());
  //       occurrences.add(n); 
  //     }
  //     occurrences.forEach( item -> {
  //       System.out.println(item.toString());
  //     });
  // }//:

}///~
