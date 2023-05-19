package com.jirepo.demo.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDemoBean {
    private String userId;
    private String userName;
    private int age; 
    private Date orderDate; 
    private String[] addrs;
    private List<String> favors;
}
