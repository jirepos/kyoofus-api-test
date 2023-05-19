package com.jirepo.core.json;

import lombok.Getter;
import lombok.Setter;

// @Getter
// @Setter
public class JsonTestSubBean {
    private String cmt; 
    private String userId; 

    // public String getUserId() {
    //     return userId; 
    // }
    // public void setUserId(String id) {
    //     this.userId = id; 
    // }
    public JsonTestSubBean() {
        this.cmt = "hello";
    }
}
