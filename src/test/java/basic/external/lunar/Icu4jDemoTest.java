package basic.external.lunar;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ChineseCalendar;

public class Icu4jDemoTest {


    // ((year % 4) == 0 && (year % 100) != 0) || (year % 400) == 0;
    // 위 식은 윤년(2월 29일이 있는 해) 조건식인데,  4로 나누어 떨어지는 해 중 100으로 나누어 떨어지는 해는 제외하되 400으로 나누어 떨어지는 해는 포함
    // 2000년은 4로 나누어 떨어지고 100으로 나누어 떨어지지만 400으로 나누어 떨어지므로 윤년
    // 양력과 음력 변환에 이와 같은 간단한 규칙은 없다. 
    // 음력은 달의 삭망을 기준으로 만든 역법으로, 태양과 달의 운행을 정확하게 계산한 자료를 바탕으로 날짜를 정하게 됩니다. 즉, 지구-달-태양의 위치 
    // 변화에 따라 달라지는, 달의 모양이 기울었다(朔) 차고(望) 다시 기우는 주기인 삭망 주기(朔望週期)를 측정하여 정합니다.
    // 달의 삭망주기는 약 29.5일 입니다. 이에 따라 음력 한 달은 30일(큰 달) 또는 29일(작은 달)입니다. 따라서 1년 12달은 대략 354일 정도됩니다. 
    // 이는 양력 1년 약 365일과 다르므로, 대개 3년에 한번 같은 달이 두번 반복되는 윤달을 두어 조정하게 됩니다.
    // 따라서 양력을 음력으로 변환하기 위해서는, 음력의 어떤 달이 큰 달인지 작은 달인지 그리고 윤달인지 평달인지 알아야 합니다. 
    // 우리나라는 이 자료를 '한국천문연구원'에서 구할 수 있습니다.
    // ChineseCalendar는 중국의 음력이므로 한국과는 음력이 다를 수 있다. 천문연구원의 다음 링크를 참조한다. 
    // https://astro.kasi.re.kr/community/post/qna/2790?cPage=5&clsf_cd=qna04

    // 결국, 우리나라 음력 변환을 위해서는 위에서 설명한 큰 달, 작은 달, 윤달에 대한 정확한 자료가 있어야 합니다.
    // 아래 한국천문연구원 사이트의 윤년/윤달 정보에 보면 이에 대한 자료를 얻을 수 있습니다. 현재는 2050까지의 자료만 공개하고 있습니다. 문제는 원하는 범위의 연도, 월 자료를 한꺼번에 공개하고 있지는 않다는 점입니다.
    // https://astro.kasi.re.kr/life/pageView/8


    /**
     * 음력을 양력으로 변환한다. 
     * @param year  음력 년도 
     * @param month  음력 월 
     * @param dayOfMonth  음력 일 
     * @param timeZoneId  타임존 아이디 
     * @return  양력 날짜 
     */
    public LocalDate toSolar(int year, int month, int dayOfMonth, String timeZoneId) {
        // 여전히 내부에서는 java.util.Date 사용
        ChineseCalendar calendar = new ChineseCalendar();
        // ChinessCalendar.YEAR는 1~60까지의 값만 가지고,
        // ChinessCalendar.EXTENDED_YEAR는 Calendar.YEAR 값과 2637 만큼의 차이를 가집니다.        
        calendar.set(ChineseCalendar.EXTENDED_YEAR, year + 2637); // 년 
        calendar.set(ChineseCalendar.MONTH, month -1 );  // 월 
        calendar.set(ChineseCalendar.DAY_OF_MONTH, dayOfMonth);  // 일 
        return  Instant.ofEpochMilli(calendar.getTimeInMillis()).atZone(ZoneId.of(timeZoneId)).toLocalDate();
    }

    /** 음력을 양력으로 변환하는 테스트 */
    @Test 
    public void testToSolar(){ 
        // LocalDate solar = toSolar(1967, 9, 5, "Asia/Seoul");
        LocalDate solar = toSolar(1974, 7, 19, "Asia/Seoul");
        System.out.println(solar);
    }


 
    //https://unicode-org.github.io/icu/userguide/icu4j/
    /** 음력을 양력으로 변환하는 테스트 */
    @Test
    public void testSolar() {
 
        ChineseCalendar calendar = new ChineseCalendar();
        // https://rangerang.tistory.com/71
        // ChinessCalendar.YEAR는 1~60까지의 값만 가지고,
        // ChinessCalendar.EXTENDED_YEAR는 Calendar.YEAR 값과 2637 만큼의 차이를 가집니다.        
        calendar.set(ChineseCalendar.EXTENDED_YEAR, 1967 + 2637); // 년 
        calendar.set(ChineseCalendar.MONTH, 9 -1 );  // 월 
        calendar.set(ChineseCalendar.DAY_OF_MONTH, 5);  // 일 

        System.out.println(calendar.get(ChineseCalendar.EXTENDED_YEAR)); // 년
        System.out.println(calendar.get(ChineseCalendar.MONTH)); // 월
        System.out.println(calendar.get(ChineseCalendar.DAY_OF_MONTH)); // 일
        
        LocalDate solar = Instant.ofEpochMilli(calendar.getTimeInMillis()).atZone(ZoneId.of("Asia/Seoul")).toLocalDate();
        System.out.println(solar.getYear());
        System.out.println(solar.getMonth().getValue());
        System.out.println(solar.getDayOfMonth());
    }


    /** 양력을 음력으로 변환하는 테스트 */
    @Test
    public void testLunar() {

        //  icu4j의 Calendar와 java.util.Calendar의 시간 값은 동일하다 
        // com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance();
        // System.out.println(calendar.getTimeInMillis());
        // Calendar javaCalendar = Calendar.getInstance();
        // System.out.println(javaCalendar.getTimeInMillis());  // milliseconds를 제외하곤 같은 값 



        //양력 날짜 
        com.ibm.icu.util.Calendar calendar = com.ibm.icu.util.Calendar.getInstance();
        calendar.set(com.ibm.icu.util.Calendar.YEAR, 1967);       // 년
        calendar.set(com.ibm.icu.util.Calendar.MONTH, 10 -1);     // 월은 1을 빼고 입력해야 한다. 
        calendar.set(com.ibm.icu.util.Calendar.DAY_OF_MONTH, 8); // 일

        System.out.println(calendar.get(com.ibm.icu.util.Calendar.YEAR)); // 년
        System.out.println(calendar.get(com.ibm.icu.util.Calendar.MONTH) + 1); // 월
        System.out.println(calendar.get(com.ibm.icu.util.Calendar.DAY_OF_MONTH)); // 일


        // // // 평달 0 
        // // calendar.set(ChineseCalendar.IS_LEAP_MONTH, 0); // 윤달 여부
        ChineseCalendar chineseCalendar = new ChineseCalendar();
        // 음력 캘린더로 변환 
        chineseCalendar.setTimeInMillis(calendar.getTimeInMillis());
        // // 변환된 음력의 윤달 여부
        boolean leapMonth = (chineseCalendar.get(ChineseCalendar.IS_LEAP_MONTH) == 1) ? true : false;
        System.out.println(leapMonth); // 윤달 여부 출력
        
        // // LocalDate lunar = Instant.ofEpochMilli(chineseCalendar.getTimeInMillis()).atZone(ZoneId.of("Asia/Seoul")).toLocalDate();
        // // System.out.println(lunar.getYear());
        // // System.out.println(lunar.getMonth().getValue());
        // // System.out.println(lunar.getDayOfMonth());

        System.out.println(chineseCalendar.get(ChineseCalendar.EXTENDED_YEAR) - 2637 ); // 년
        System.out.println(chineseCalendar.get(ChineseCalendar.MONTH) + 1); // 월
        System.out.println(chineseCalendar.get(ChineseCalendar.DAY_OF_MONTH)); // 일

        // ZonedDateTime zdt = ZonedDateTime.of(1967, 10, 8, 0,0, 0, 0, ZoneId.of("Asia/Seoul"));
        // Long miliis = zdt.toEpochSecond();
        // ChineseCalendar chineseCalendar = new ChineseCalendar();
        // chineseCalendar.setTimeInMillis(miliis);
        // System.out.println(chineseCalendar.get(ChineseCalendar.EXTENDED_YEAR) - 2637 ); // 년
        // System.out.println(chineseCalendar.get(ChineseCalendar.MONTH) +1 ); // 월
        // System.out.println(chineseCalendar.get(ChineseCalendar.DAY_OF_MONTH)); // 일

    }
    @Test
    public void testMillis() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.toEpochSecond(ZoneOffset.of("+09:00")));
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTimeInMillis());  // milliseconds를 제외하곤 같은 값 
    }
}
