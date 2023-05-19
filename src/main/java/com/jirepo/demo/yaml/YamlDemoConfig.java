package com.jirepo.demo.yaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Component
/** application.yml 파일의 속성을 담고 있는 클래스이다.  */
@ConfigurationProperties(prefix="app-info")
@Getter
@Setter
@ToString
//@ConfigurationProperties
public class YamlDemoConfig {
    private String name;
    private String version;
    private String description;
    private String author;
    private String email;
}
