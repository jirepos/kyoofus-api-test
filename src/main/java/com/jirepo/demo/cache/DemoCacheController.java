package com.jirepo.demo.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/** 
 * 캐시를 테스트하기 위한 컨트롤러 클래스입니다.
 */
@Controller
@RequestMapping("/demo/cache")
@Slf4j
public class DemoCacheController {

    /** 캐시를 적용한 메서드를 가진 서비스 클래스를 주입한다.  */
    @Autowired
    private DemoCacheService demoCacheService;
    

    /** 여기서 캐시가 적용된 메서드를 호출한다.  */
    @GetMapping("/list")
    public ResponseEntity<List<String>> getMenu() {

        log.debug("before selectMenu");  // 이 엔드포인트가 호출되었는지 확인하기 위해 로그 출력 
        DemoCacheBean bean = new DemoCacheBean();
        bean.setName("hong");
        List<String> menus =  this.demoCacheService.selectMenu(bean); // 캐시가 적용된 메서드 호출 
        return ResponseEntity.ok(menus);
    }

        /** 여기서 캐시가 적용된 메서드를 호출한다.  */
        @GetMapping("/list2")
        public ResponseEntity<List<String>> getMenu2() {
    
            log.debug("before selectMenu");  // 이 엔드포인트가 호출되었는지 확인하기 위해 로그 출력 
            DemoCacheBean bean = new DemoCacheBean();
            bean.setName("park");
            List<String> menus =  this.demoCacheService.selectMenu(bean); // 캐시가 적용된 메서드 호출 
            return ResponseEntity.ok(menus);
        }

}///~
