package com.jirepo.demo.core;

import lombok.Getter;
import lombok.Setter;


/** RSA decryption 테스트를 위한 클래스이다. */
@Setter
@Getter
public class RsaDemoBean {
    /** 공개키로 암호화된 암호 */
    private String pwd; 
}
