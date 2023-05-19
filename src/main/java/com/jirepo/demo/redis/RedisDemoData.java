package com.jirepo.demo.redis;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedisDemoData implements Serializable {
    private static final long serialVersionUID = -7353484588260422449L;
    private String empId;
    private String name;
}
