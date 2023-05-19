package com.jirepo.demo.redis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.jirepo.core.json.JsonUtils;
import com.jirepo.core.web.exception.BaseBizErrorCode;
import com.jirepo.core.web.exception.BaseBizException;

/*
https://wildeveloperetrain.tistory.com/32
 * Redis는 여러 자료 구조를 가지고 있고, 사용하는 자료 구조에 따라 적절한 메서드를 사용할 수 있습니다.

메서드	설명
opsForValue	Strings를 쉽게 Serialize / Deserialize 해주는 interface
opsForList	List를 쉽게 Serialize / Deserialize 해주는 interface
opsForSet	Set을 쉽게 Serialize / Deserialize 해주는 interface
opsForZSet	ZSet을 쉽게 Serialize / Deserialize 해주는 interface
opsForHash	Hash를 쉽게 Serialize / Deserialize 해주는 interface
 */
/**
 * RedisTemplate 사용하기 위한 서비스 클래스이다. 
 */
@Service
public class RedisDemoService {
    /** RedisTemplate 주입 */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 키를 이용하여 Redis에 데이터를 저장한다. 
     * @return
     */
    public RedisDemoData setOperation() {
        RedisDemoData data = new RedisDemoData();
        data.setEmpId("empId");
        data.setName("name");
        // ValueOperations<String, Object> valueOperations =
        // redisTemplate.opsForValue();
        // valueOperations.set("key", data);
        try {
            // redisTemplate.opsForValue().set("redisDemoData", data); // java 직렬화
            // 아래는 String 직렬화
            redisTemplate.opsForValue().set("redisDemoData", JsonUtils.toJSON(data));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR);
        }

        return data;
    }

    /**
     * 키를 이용하여 레디스에 저장된 데이터를 조회한다.
     */
    public RedisDemoData getOperation() {
        // ValueOperations<String, Object> valueOperations =
        // redisTemplate.opsForValue();
        // RedisDemoData data = (RedisDemoData)valueOperations.get("key");
        try {
            // 다음과 같은 오류가 난다.
            // com.jirepo.demo.redis.RedisDemoData(com.jirepo.demo.redis.RedisDemoData is
            // in unnamed module of loader 'app'; com.jirepo.demo.redis.RedisDemoData
            // is in unnamed module of loader
            // org.springframework.boot.devtools.restart.classloader.RestartClassLoader
            // @df90b6a)\
            // 일단 pom.xml의 spring-devtools를 주석처리
            //
            // 역직렬화 조건
            // - 동일한 serialVersionUID를 가지고 있어야 한다.
            // - 직렬화 대상이 된 객체의 클래스가 클래스 패스에 존재해야 한다.

            // 아래는 java 직렬화
            // RedisDemoData data =
            // (RedisDemoData)redisTemplate.opsForValue().get("redisDemoData");
            // 아래는 String 역직렬화 방식
            String json = (String) redisTemplate.opsForValue().get("redisDemoData");
            if (json != null) {
                RedisDemoData data = (RedisDemoData) JsonUtils.toObject(json, RedisDemoData.class);
                return data;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 삭제
     */
    public void delete() {
        redisTemplate.delete("redisDemoData");
    }

    // redis에 삽입한 아이템에 대해서 5초의 expire time을 설정하고, 5초가 지난 후에 redis에서 해당 키가 조회되는지
    // 테스트하는 코드이다.
    /**
     * 자동 삭제 테스트이다. 
     */
    public void expireOperation() {
        // redisTemplate.expire("key", 10, TimeUnit.SECONDS);
        redisTemplate.expire("redisDemoData", 5, TimeUnit.SECONDS);

        // final ValueOperations<String, String> valueOperations =
        // redisTemplate.opsForValue();
        // valueOperations.set(key, data);
        // final Boolean expire = redisTemplate.expire(key, 5, TimeUnit.SECONDS);
        // Thread.sleep(6000);
        // final String s = valueOperations.get(key);
        // assertThat(expire).isTrue();
        // assertThat(s).isNull();
    }

    /**
     * 레디스에 리스트를 추가하고 가져오는 예제이다. 
     * @return
     */
    public List<String> listOperation() {
        try {
            String key = "listkey";
            ListOperations<String, String> opers = this.redisTemplate.opsForList();
            opers.rightPush(key, "1");
            opers.rightPush(key, "2");
            opers.rightPush(key, "3");
            opers.rightPush(key, "4");
            opers.rightPush(key, "5");
            opers.rightPush(key, "6");
            opers.rightPushAll(key, "7", "8", "9", "10");

            String char1 = opers.index(key, 1);
            System.out.println(char1);
            long size = opers.size(key);
            System.out.println(size);
            List<String> list = opers.range(key, 0, -1); // 전체
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Set 연산
     */
    public void forSetOperation() {
        // redis WRONGTYPE Operation against a key holding the wrong kind of value
        // 레디스는 기본적으로 5가지 타입을 제공하고 있으며 데이터베이스에 한번 타입이 결정된 상태에서 
        // 해당 타입과 상관없는 명령을 수행하려고 할때 위와 같은 에러 메시지가 출력된다. 
        // 꼭 수행해야되는 명령이라면 기존의 키를 지우면 된다.
        String key = "jirepos";
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            setOps.add(key, "H");
            setOps.add(key, "e");
            setOps.add(key, "l");
            setOps.add(key, "l");
            setOps.add(key, "o");
            Set<String> jirepos = setOps.members(key);
            System.out.println("members = " + Arrays.toString(jirepos.toArray()));
            long size = setOps.size(key);
            System.out.println(size);
            Cursor<String> cursor = setOps.scan(key, ScanOptions.scanOptions().match("*").count(3).build());
            while (cursor.hasNext()) {
                System.out.println("cursor = " + cursor.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR);
        }

    }// :

    /**
     * 키/밸류를 정렬된 형태로 저장하고 가져오기 
     */
    public void sortedSetOperation() {
        String key = "jirepos3";
        try {
            // ZSET 은 Redis Sorted Set 을 의미하며, 쉽게 말하면 어떤 값을 입력하면 정렬된 상태로 유지 하는 자료 저장 형태라고 생각하면 됩니다.
            ZSetOperations<String, String> setOps = redisTemplate.opsForZSet();
            // add(key, value, score)
            // key, value는 자료형을 선택할 수 있다. 
            // socre를 기준으로 내림차순으로 정렬 , score는 항상 숫자 
            setOps.add(key, "H", 1);
            setOps.add(key, "e", 5);
            setOps.add(key, "l", 10);
            setOps.add(key, "l", 15);
            setOps.add(key, "o", 20);

            // Get elements between start and end from sorted set.
            Set<String> range = setOps.range(key, 0, 5);

            System.out.println("range = " + Arrays.toString(range.toArray()));

            Long size = setOps.size(key);

            System.out.println("size = " + size);

            Set<String> scoreRange = setOps.rangeByScore(key, 0, 13);
            System.out.println("scoreRange = " + Arrays.toString(scoreRange.toArray()));

            // test1 에 등록된 DDD 의 랭킹을 조회한다(올림차순). Redis 명령 중 ZREVRANK 에 해당한다.
            // Long rankDDD = zSetOperations.reverseRank("test1", "DDD");
            //  key에 등록된 score 삭제. ZSET에서는 remove() 사용  
            // setOps.remove(key, "e");

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR);
        }

    }// :


    /**
     * Hash 값으로 넣고 가져오기 
     */
    public void hashSetOperation() {
        String key = "jirepos2";
        
        try {
            HashOperations<String, String, String> setOps = redisTemplate.opsForHash();

            setOps.put(key, "Hello", "a");
            setOps.put(key, "Hello2", "b");
            setOps.put(key, "Hello3", "c");

            Object hello = setOps.get(key, "Hello");
            System.out.println("hello = " + hello);
        
            Map<String, String> entries = setOps.entries(key);
            System.out.println("entries = " + entries.get("Hello2"));
            Long size = setOps.size(key);
            System.out.println("size = " + size);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR);
        }

    }// :

}/// ~
