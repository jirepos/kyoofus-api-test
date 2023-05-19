package com.jirepo.core.yaml;

import lombok.Data;

@Data
public class YamlTestBean {

    private User user;
    private String test;
    
    @Data
    public static class User {
        private String name;
        private int age;
    }
}
