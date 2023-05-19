package basic.external.lunar;

import org.junit.jupiter.api.Test;

import com.jirepo.core.util.LunarDateBean;
import com.jirepo.core.util.LunarDateUtils;

public class LunarDateUtilsTest {

    @Test 
    public void testPrintLunar(){
        // 양력으로 음력 찾기 
        LunarDateBean bean1 = LunarDateUtils.getSolarLunarDate(2022, 3, 1, true); 
        System.out.println(bean1);
        // 음력으로 양력찾기
        LunarDateBean bean2 = LunarDateUtils.getSolarLunarDate(1974, 7, 19, false); 
        System.out.println(bean2);
    }
}///~
