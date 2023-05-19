package com.jirepo.demo.cache;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.TransactionalMode;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.config.PersistenceConfiguration;


/**
 * 캐시 매니저에 캐시를 등록한다. 
 */
@Configuration
public class DemoCacheConfig {

    /** CacheConfigBase class에서 생성한 EhCacheCacheManager 주입한다.  */
    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    /**
     * Cache를 등록한다. 
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception { 
        // https://oingdaddy.tistory.com/385
        // https://oingdaddy.tistory.com/384

        // name : 캐시의 이름이다. @Cacheable("캐시의 이름") 와 일치시켜줘야한다.
		// diskExpiryThreadIntervalSeconds : 디스크에 저장된 캐시들에 대해 만료된 항목을 제거하기 위한 스레드를 실행할 주기 설정
		// diskSpoolBufferSizeMB : 디스크 캐시에 쓰기 모드로 들어갈 때 사용될 비동기 모드의 스폴 버퍼 크기 설정 (OutOfMemory 발생시 수치 낮추도록 함)
		// diskPersistent : VM이 재기동할 때 캐싱된 객체를 계속 유지할지 여부
		// eternal : 한번 캐시하면 영원히 유지할 것인지의 여부
		// maxElementsInMemory : 메모리에 캐싱될 최대 객체 수
		// maxEntriesLocalHeap : 힙메모리 최대량
		// overflowToDisk : 메모리저장공간이 부족할때 Disk 사용여부
		// memoryStoreEvictionPolicy : 최대 개수에 도달할 때, 제거 알고리즘
		// - FIFO : 먼저 저장된 데이터를 우선 삭제
		// - LFU : 데이터의 이용 빈도 수를 기준으로 이용 빈도가 가장 낮은 것부터 삭제
		// - LRU : 데이터의 접근 시점을 기준으로 최근 접근 시점이 오래된 데이터부터 삭제
		// overflowToDisk : maxElementsInMemory이 옴계량에 가까우면 오버플로우되는 객체들을 디스크에 저장할지 결정
		// timeToIdleSeconds : 다음 시간동안 유휴상태 후 갱신할지 결정 (데이터가 지정된 시간(초단위)동안 재호출되지 않으면 휘발됨)
		// timeToLiveSeconds : 한번 저장된 데이터의 최대 저장 유지 시간(초단위)
		// maxBytesLocalHeap : 최대 로컬 힙메모리 사용량 설정 (사용 시 maxEntriesLocalHeap 사용 불가)
		// maxBytesLocalDisk : maxBytesLocalHeap에 설정된 캐시 사용 이후 디스크 스토리지 한계 설정

        // CacheConfiguration cacheConfiguration = new CacheConfiguration();
        // cacheConfiguration.maxEntriesLocalHeap(1000);  // 메모리에 생성될 객체의 최대 수(0: 제한없음)
        // //cacheConfiguration.maxBytesLocalHeap(1000, MemoryUnit.MEGABYTES);  // 메모리에 생성될 객체의 최대 사용량(0: 제한없음)
        // // cacheConfiguration.overflowToOffHeap(false);    // 메모리에 생성될 객체의 최대 수를 초과하면 오버플로우되는 객체들을 디스크에 저장할지 결정
        // cacheConfiguration.eternal(false);  // 저장된 캐시를 제거할지 여부. 설정이 true 인 경우 저장된 캐시는 제거되지 않으며 timeToIdleSeconds, timeToLiveSeconds 설정은 무시
        // cacheConfiguration.timeToIdleSeconds(1800);  // 생성후 해당 시간 동안 캐쉬가 사용되지 않으면 삭제, 0은 삭제되지 않음
        // cacheConfiguration.timeToLiveSeconds(1800);  // 생성후 해당 시간이 지나면 캐쉬는 삭제. 0은 삭제되지 않음
        // cacheConfiguration.memoryStoreEvictionPolicy("LFU");  //maxEntriesLocalHeap 설정 값에 도달했을때 설정된 정책에 따라 객체가 제거되고 새로 추가(LRU : 사용이 가장 적었던 것부터 제거, FIFO : 먼저 입력된 것부터 제거, LFU : 사용량이 적은 것부터 제거)
        // cacheConfiguration.transactionalMode(TransactionalMode.OFF);               
        // //cacheConfiguration.persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP)); // diskStore 사용 설정("localTempSwap": Temp DiskStore 사용, "none": Only Memory 사용)
        // cacheConfiguration.persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE)); // diskStore 사용 설정("localTempSwap": Temp DiskStore 사용, "none": Only Memory 사용)
        // cacheConfiguration.name("getMenu");  // 캐시 이름
        // Cache getMenuCache = new Cache(cacheConfiguration);
        // // Cache가 등록되어 있는데 등록하려하면 오류가 발생 
        // if(!this.ehCacheCacheManager.getCacheManager().cacheExists("getMenu")) {
        //     //this.ehCacheCacheManager.getCacheManager().removeCache("getMenu");
        //     this.ehCacheCacheManager.getCacheManager().addCache(getMenuCache);
        // }
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        //cacheConfiguration.maxEntriesLocalHeap(1000);  // 메모리에 생성될 객체의 최대 수(0: 제한없음)
        //cacheConfiguration.maxEntriesLocalDisk(10000);  // 디스크(DiskStore)에 저장될 객체의 최대 수(0: 제한없음)
        cacheConfiguration.maxBytesLocalHeap(1000, MemoryUnit.MEGABYTES);  // 메모리에 생성될 객체의 최대 사용량(0: 제한없음)
        cacheConfiguration.overflowToOffHeap(false);    // 메모리에 생성될 객체의 최대 수를 초과하면 오버플로우되는 객체들을 디스크에 저장할지 결정
        cacheConfiguration.eternal(false);  // 저장된 캐시를 제거할지 여부. 설정이 true 인 경우 저장된 캐시는 제거되지 않으며 timeToIdleSeconds, timeToLiveSeconds 설정은 무시
        cacheConfiguration.timeToIdleSeconds(1800);  // 생성후 해당 시간 동안 캐쉬가 사용되지 않으면 삭제, 0은 삭제되지 않음
        cacheConfiguration.timeToLiveSeconds(1800);  // 생성후 해당 시간이 지나면 캐쉬는 삭제. 0은 삭제되지 않음
        cacheConfiguration.memoryStoreEvictionPolicy("LFU");  //maxEntriesLocalHeap 설정 값에 도달했을때 설정된 정책에 따라 객체가 제거되고 새로 추가(LRU : 사용이 가장 적었던 것부터 제거, FIFO : 먼저 입력된 것부터 제거, LFU : 사용량이 적은 것부터 제거)
        cacheConfiguration.transactionalMode(TransactionalMode.OFF);               
        //cacheConfiguration.persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP)); // diskStore 사용 설정("localTempSwap": Temp DiskStore 사용, "none": Only Memory 사용)
        cacheConfiguration.persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE)); // diskStore 사용 설정("localTempSwap": Temp DiskStore 사용, "none": Only Memory 사용)
        cacheConfiguration.name("getMenu");  // 캐시 이름
        Cache getMenuCache = new Cache(cacheConfiguration);
        // Cache가 등록되어 있는데 등록하려하면 오류가 발생 
        if(!this.ehCacheCacheManager.getCacheManager().cacheExists("getMenu")) {
            //this.ehCacheCacheManager.getCacheManager().removeCache("getMenu");
            this.ehCacheCacheManager.getCacheManager().addCache(getMenuCache);
        }
    }//:


}///~
