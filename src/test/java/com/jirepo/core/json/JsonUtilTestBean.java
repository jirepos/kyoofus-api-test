package com.jirepo.core.json;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;


/**
 * JSONUtils 클래스를 테스트하기 위한 Bean 클래스이다.
 */
@Getter
@Setter
public class JsonUtilTestBean {
    private String userId;
    private String userName;
    private int age; 
    private JsonTestSubBean subBean;
    
    // @JsonSerialize(using = JsonAlterLocalDateTimeSerializer.class)
    @JsonSerialize(as = LocalDateTime.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy MM")
    private LocalDateTime ldt; 

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date orderDate; 
    private String[] addrs;
    private List<String> favors;
}
