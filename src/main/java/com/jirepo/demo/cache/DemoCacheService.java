package com.jirepo.demo.cache;


import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/** 캐시를 테스트하기 위한 서비스 클래스이다. */
@Service
@Slf4j
public class DemoCacheService {

    // @Cacheable의 인자로는 cacheNames(혹은 value), key, condition을 지정할 수 있다. 2.x 버전에서는 value라고 표현했으나
    // 3.x버전에서는 cacheNames로 표현한다. 웬만하면 3버전의 이름을 사용하자.
    // cacheNames(혹은 value)는 ehcache.xml에서 등록했던 캐시 중 메서드에 적용할 캐시의 이름
    // key는 캐시를 구분하기 위한 값이다. 만약 메소드의 모든 파라미터를 key로 사용하는 경우 굳이 명시할 필요는 없다. 
    // 이 때 KeyGenerator가 key를 생성 전략은 공식 문서를 참고한다.
    // https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache-annotations-cacheable-default-key
    // @Cacheable(cacheNames="books", key="#isbn")
    // public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)
    // @Cacheable(cacheNames="books", key="#isbn.rawNumber")
    // public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)
    // @Cacheable(cacheNames="books", key="T(someType).hash(#isbn)")
    // public Book findBook(ISBN isbn, boolean checkWarehouse, boolean includeUsed)
    //
    // condition은 캐싱할 조건을 정할 수 있다. 예를 들어 어떤 파라미터의 값이 1 이상 혹은 길이가 10 이하인 것 들만 캐싱을 하도록 조건 설정이 가능하다. 자세한 내용은 공식 문서를 참고한다.
    // https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache-annotations-cacheable-condition
    // @Cacheable(cacheNames="book", condition="#name.length() < 32", unless="#result.hardback") 
    // public Book findBook(String name)
    //@Cacheable(cacheNames="getMenu", condition="#bean.getName().equals('hong')")
    // Key와 condition을 같이 정의해야 함. condition 만으로는 캐시 되지 않음 
    @Cacheable(cacheNames="getMenu", key="#bean.name", condition="#bean.name == 'hong'")
    public List<String> selectMenu(DemoCacheBean bean){ 
        
        // @Cacheable이 적용된 메서드 안에서 @Cacheable이 적용된 다른 메서드를 호출하면 캐시가 적용되지 않는다. 주의할 것 
        log.debug("selectMenu");  // 캐시가 적용되었는지 확인하기 위한 로그 

        List<String> menus = new ArrayList<>(); // 캐시를 적용하기 위한 메모리 생성
        menus.add("menu1");
        menus.add("menu2");
        menus.add("menu3");
        menus.add("menu4");
        menus.add("menu5");
        return menus; 
    }

    // allEntries 는 해당 캐시의 모든 속성을 의미한다. 
    @CacheEvict(value = "getMenu", allEntries = true)
    public void deleteCache(){ 
        //등록, 수정, 삭제 시에는 캐시 값이 달라지기 때문에 적절한 시점에 업데이트를 해주어야 한다. 따라서 해당 요청이 들어올 경우 @CacheEvict 
        // 어노테이션을 추가하고 allEntries를 true로 주어 해당 이름의 캐시가 전체 삭제되도록 한다.
        log.debug("캐시가 삭제됩니다.");
    }

    // 캐시를 생성할 때 key를 사용하면 삭제할 때도 key를 사용한다. 
    // @CacheEvict(value = "userDetail", key = "#userId")
    // public void deleteUser(Long userId) {
    //    로직 처리...
    // }



    // @CachePut : 캐시 내용 수정 담당  userDetail::<userId> : UserDetailsDto 값 수정
    // 메서드를 수행 후 Return 값을 캐쉬에 저장한다.  @Cacheable과의 차이는 캐싱값을 취하는 (메서드가 수행되지 않는) 
    // 방법이 아닌 메서드 수행 후 항상 캐쉬값을 저장 하도록 한다는 것이다.
    //
    // @CachePut(cacheNames = "useCacheNames", key="#pageNo")	
    // public List<ObjectA> getEventsByPageNo(int pageNo) {
    //    .......
    // }
    
}///~
