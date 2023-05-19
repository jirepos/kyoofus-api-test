package com.jirepo.core.json;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jirepo.demo.jackson.TimeDemoBean;

/**
 * JSONUtils.java를 테스트하기 위한 테스트케이스이다.
 */
public class JsonUtilsTest {

    /** 객체를 JSON으로 변환한다. */
    @Test
    public void testToJson() throws Exception {

        JsonUtilTestBean bean = new JsonUtilTestBean();
        String[] addrs = new String[] { "11", "22", "33" };
        bean.setAddrs(addrs);
        bean.setFavors(Arrays.asList(addrs));
        bean.setAge(10);
        bean.setOrderDate(new Date());
        bean.setLdt(LocalDateTime.now());
        String json = JsonUtils.toJSON(bean);
        System.out.println(json);
    }// :

    /** 객체를 JSON으로 변환한다. */
    @Test
    public void testToJsonUsingBuilder() throws Exception {

        JsonUtilTestBean bean = new JsonUtilTestBean();
        String[] addrs = new String[] { "11", "22", "33" };
        bean.setAddrs(addrs);
        bean.setFavors(Arrays.asList(addrs));
        bean.setAge(10);
        bean.setOrderDate(new Date());
        bean.setLdt(LocalDateTime.now());
        ObjectMapper mapper = JsonObjectMapperBuilder.build();
        String json = mapper.writeValueAsString(bean);
        System.out.println(json);
    }// :

    
    /** 객체를 JSON으로 변환한다. */
    @Test
    public void testToJsonUsingBuilder2() throws Exception {
       JsonTestSubBean subBean = new JsonTestSubBean();
        ObjectMapper mapper = JsonObjectMapperBuilder.build();
        String json = mapper.writeValueAsString(subBean);
        System.out.println(json);
    }// :



    /** 객체를 JSON으로 변환한다. */
    @Test
    public void testTimeDemoBean() throws Exception {
        TimeDemoBean bean = new TimeDemoBean();
        String json = JsonUtils.toJSON(bean);
        System.out.println(json);
    }// :

    /** JSON을 객체로 변환한다. */
    @Test
    public void testToObject() throws Exception {
        String json = "{\"userId\":null,\"userName\":null,\"age\":10,\"orderDate\":1624246540691,\"addrs\":[\"11\",\"22\",\"33\"],\"favors\":[\"11\",\"22\",\"33\"]}";
        JsonUtilTestBean bean = (JsonUtilTestBean) JsonUtils.toObject(json, JsonUtilTestBean.class);
        System.out.println(bean.getAge());
    }// :

    /** 리스트를 JSON으로 변환한다. */
    @Test
    public void testToList() throws Exception {
        List<JsonUtilTestBean> list = new ArrayList<>();
        JsonUtilTestBean bean = new JsonUtilTestBean();
        bean.setUserId("latte");
        list.add(bean);

        String json = JsonUtils.toJSON(list);
        System.out.println(json);
        List<JsonUtilTestBean> list2 = JsonUtils.toList(json, JsonUtilTestBean.class);
        list2.forEach(s -> System.out.println(s.getUserId()));
    }// :

}