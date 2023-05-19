package com.jirepo.core.yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class SnakeYamlTest {

    @Test
    public void testSnakeYaml() throws Exception {
        // Yaml yaml = new Yaml();
        Yaml yaml = new Yaml(new Constructor(YamlTestBean.class));
        YamlTestBean test = yaml.load(new ClassPathResource("test.yml").getInputStream());
        System.out.println(test); // YamlTest(user=YamlTest.User(name=devson, age=20), test=This is test string
                                  // for snake yaml)
    }

    // https://www.baeldung.com/java-snake-yaml
    @Test
    public void testSnakeYaml2() throws Exception {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("test.yml");
        Map<String, Object> obj = yaml.load(inputStream);
        System.out.println(obj); // {user={name=devson, age=20}, test=This is test string for snake yaml}
    }


    // https://www.baeldung.com/java-snake-yaml
    /**
     * 하나의 yaml 파일에 여러개의 문서가 있을 경우
     * 구분은 '---'을 사용한다. 
     * @throws Exception
     */
    @Test
    public void testSnakeYaml3() throws Exception {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("spring-test.yml");
        //Iterable<Object> obj = yaml.loadAll(inputStream);
        for (Object object : yaml.loadAll(inputStream)) {
            System.out.println(object.getClass().toString());
            System.out.println(object);
        }
    }
    
    

    /**
     * 여러 문서로 구분된(---) yaml 문서를 읽어서 properties 파일 형식이 키로 변환하여 맵에 저장한다. 
     * @throws Exception
     */
    @Test
    public void testSnakeYaml4() throws Exception {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("spring-test.yml");
        //Iterable<Object> obj = yaml.loadAll(inputStream);
        List<Object> propList = new ArrayList<Object>();
        for (Object object : yaml.loadAll(inputStream)) {
            propList.add(object);
        }// for 
       
        // 여기에 프러퍼티 형식의 키를 사용하여 프러퍼티를 저장한다. 
        Map<String, Object> finalMap = new HashMap<String, Object>();

        @SuppressWarnings("unchecked") 
        Map<String, Object> firstMap = (Map<String, Object>)propList.get(0);   // ROOT 프러퍼티 
        Iterator<String> keys = firstMap.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            Object value = firstMap.get(key);
            if(value instanceof Map<?, ?>) {
                putChildren(finalMap, key, value);
            }else { 
                finalMap.put(key, value);
            }
        }


        // 프로파일별 속성을 담는다. 
        List<Map<String, Object>> profileMapList = new ArrayList<Map<String,Object>>(); 

        for(int i=1; i < propList.size(); i++) {
            Map<String, Object> trgtMap = new HashMap<String, Object>();
            @SuppressWarnings("unchecked") 
            Map<String, Object> srcMap = (Map<String, Object>)propList.get(i);   // profile 프러퍼티 
            Iterator<String> profileKeys = srcMap.keySet().iterator();
            while(profileKeys.hasNext()){
                String key = profileKeys.next();
                // System.out.println(key);
                Object value = srcMap.get(key);
                // System.out.println(value);
                if(value instanceof Map<?, ?>) {
                    //System.out.println(value.getClass().toString());
                    putChildren(trgtMap, key, value);
                }else { 
                    trgtMap.put(key, value);
                }
            }
            profileMapList.add(trgtMap);
        }
        
        String profileName = "local"; 
        Map<String, Object> profileMap = findProfile(profileName, profileMapList);
        // System.out.println(profileMap);
        if(profileMap != null) {
           finalMap.putAll(profileMap);
        }
        System.out.println(finalMap);
    }//:
    

    /**
     * 값이 Map인 경우에 하위 키를 찾아서 최종 맵에 저장한다.
     * @param propMap 프러퍼티를 저장할 맵 
     * @param key 키
     * @param mapTypeValue Map 형태의 값 
     */
    private void putChildren(Map<String, Object> propMap, String key, Object mapTypeValue) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>)mapTypeValue;
        Iterator<String> keys = map.keySet().iterator();
        while(keys.hasNext()){
            String subKey = keys.next();
            String newKey = key + "." + subKey;
            Object value = map.get(subKey);
            if(value instanceof Map<?, ?>) {
                putChildren(propMap, newKey , value);
            }else { 
                propMap.put(newKey, value);
            }
        }// while 
    }//:




    /**
     * 프로파일에 해당하는 맵 반환한다. 
     * @param profileName 프로파일명. 예) local
     * @param profileMapList 프로파일 맵 리스트
     * @return  프로파일에 해당하는 맵. 없으면 null 
     */
    private Map<String, Object> findProfile(String profileName, List<Map<String, Object>> profileMapList) {
        for(Map<String, Object> profileMap : profileMapList) {
            // System.out.println(profileMap);
            if(profileMap.containsKey("spring.profiles")) {
                String profiles = (String)profileMap.get("spring.profiles");
                if(profiles.contains(profileName)) {
                    return profileMap;
                }
            }
        }
        return null;
    }


    
}///~
