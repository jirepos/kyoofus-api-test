package basic.java8.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

/**
 * 정규식 테스트
 */
public class RegexTest {

    /** Pattern을 이용하여 매치 되는지 */
    @Test
    public void patternTest() {
        String regex = "^[0-9]*$"; // 숫자만 등장하는지
        String input = "122222";
        boolean result = Pattern.matches(regex, input);
        System.out.println(result);
    }

    /**
     * Pattern과 Matcher를 이용한다.
     */
    @Test
    public void matcherTest() {
        // Matcher 클래스는 문자열의 패턴을 해석하고 주어진 패턴과 일치하는지 체크할 때 사용
        String regex = "^[0-9]*$"; // 숫자만 등장하는지
        String input = "122222";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        System.out.println(matcher.find()); // 대상 문자열에 패턴이 일치하는지 체크, 그 위치로 이동
    }


    /** 매치되는 문자 찾기 */
    @Test
    public void findTest() {
        String regex = "[0-9]"; // 숫자를 찾는다
        String input = "대한민국 0시 1분 1초";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        int matchCount = 0; 
        while(matcher.find()) {  // 문자열에서 매치된 것을 찾는다. 
            System.out.println(matcher.group()); // 매치된 것을 하나씩 꺼낸다 
            matchCount++;
        }
        System.out.println("\nmatchCount : " + matchCount);
    }

    /** 먜치되는 문자의 정보 출력하기 */
    @Test 
    public void findTest2() {
        String regex = "[0-9]"; // 숫자를 찾는다
        String input = "대한민국 0시 1분 2초";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        while(matcher.find()) {  // 문자열에서 매치된 것을 찾는다. 
            System.out.println("Match:" + matcher.group(0)); // 매치된 것을 하나씩 꺼낸다
            System.out.println("Start:" + matcher.start());   // 매치된 것의 시작 위치
            System.out.println("End:" + matcher.end()); // 매치된 것의 끝 위치
        }
    }


    /** 그룹을 이해하기 위한 테스트 */
    @Test 
    public void testGroup() {
        String input = "Windows10, Windows98, Windows2000, WindowsXP, WindowsVista, Windows7, Windows8, Windows8.1";
        Pattern pattern = Pattern.compile("(Windows)(98|XP|10|7)"); // 찾을 문자열을 정규식으로 정의, 그룹은 괄호 사용 
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {  // 문자열에서 매치된 것을 찾는다. 
            System.out.println("Match:" + matcher.group(0)); // 매치된 것을 하나씩 꺼낸다, matcher.group()과 matcher.group(0)은 같은 것이다.
            System.out.println("Match:" + matcher.group(1)); // 첫번째 그룹
            System.out.println("Match:" + matcher.group(2)); // 두번째 그룹
        }
    }



    /** 소문자 변환  */
    public String convert(String s) {
        return s.toLowerCase();
    }

    

    /**
     * 정규식을 이용하여 문자열을 찾아서 변경하기
    */
    @Test 
    public void testReplace() {
        String regex = "[a-zA-Z]"; // 문자를 찾는다 
        String input = "First 3 Capital Words! than 10 TLAs, I Found";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        int lastIndex = 0;
        StringBuilder sb = new StringBuilder();
        while(matcher.find()) {  // 문자열에서 매치된 것을 찾는다. 
            //System.out.println(matcher.group(0));  // 한 글자씩 반환 
             sb.append(input, lastIndex, matcher.start()) // 패턴 먜칭이 되지 않는 문자열 추가 
               .append(convert(matcher.group(0))); // 매치된 것을 하나씩 꺼낸다
            lastIndex = matcher.end();  // 패턴 매칭된 것 마지막 위치 기억    
        }
        System.out.println(sb.toString());
    }
    
    /** ReplaceAll 테스트 */
    @Test
    public void replaceTest() {
        String regex = "[0-9]"; // 숫자를 찾는다
        String input = "대한민국 0시 11분";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String result = matcher.replaceAll(" ## "); // 찾은 숫자를 ##로 변경
        System.out.println(result);
    }


    /** 특수 문자 치환 */
    @Test 
    public void testEscape() {
        String input = "\\c\\d hello"; 
        String result = input.replaceAll("\\\\", "@"); // 역 슬래시 치환은 이렇게 
        System.out.println(result);  //@c@d hello
        
        input = "abcdef \r\n";
        result = input.replaceAll("\r", "@"); // 공백 치환은 이렇게
        System.out.println(result);  //abcdef @

        result = input.replaceAll("[\r|\n]", "{newline}"); // 공백 치환은 이렇게
        System.out.println(result);  // abcdef {newline}{newline}

    }

}/// ~
