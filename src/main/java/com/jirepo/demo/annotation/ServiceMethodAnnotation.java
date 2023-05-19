package com.jirepo.demo.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드에 지정할 어노테이션이다.  클라이언트에서 서버에 요청 시
 * 메서드 이름을 알아내기 위해 사용된다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceMethodAnnotation {
    /**
     * Service class의 method의 이름을 지정한다.
     * @return
     */
    String name();
}


