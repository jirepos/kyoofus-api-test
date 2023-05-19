package com.jirepo.demo.cache;

import java.io.Serializable;


public class DemoCacheBean implements Serializable  {
    // serialVersionUID는 객체를 직렬화할 때 사용한다. Class의 경우 Class의 versioning 용도로 사용한다. 
    // 값을 명시적으로 지정하지 않으면 Compiler가 계산한 값을 부여하는데 Serializable Calss 
    // 또는 Outer Class에 변경이 있을 대 serialVersionUID 값이 변경 된다. Serialize할 때와 
    // Deserialize할 때 serialVersionUID 값이 다르면 InvalidClassExceptions 이 발생하여 
    // Deserialize할 수 없게 된다.
    static final long serialVersionUID = 1L;
    private String name; 
    private int age; 

    
    public String getName() {
        return name; 
    }
    public void setName(String name) {
        this.name = name; 
    }
    public int getAge() {
        return age; 
    }
    public void setAge(int age) {
        this.age = age; 
    }
}
