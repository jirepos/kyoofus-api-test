package com.jirepo.demo.yaml;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;


/** application.yaml 설정값 읽기 데모 */
@Controller
@RequestMapping("/demo/yaml")
@Slf4j
public class YamlDemoController {
    
    @Autowired
    private ApplicationContext ctx; 
    @Autowired
    private YamlDemoConfig yamlDemoConfig;
    
    /** application.yml 파일에서 server.port 속성을 읽어서 text로 반환한다.  */
    @GetMapping(value="/read-yml",  produces="text/plain;charset=utf-8")
    public @ResponseBody String readYaml() {
        return ctx.getEnvironment().getProperty("server.port");
    }//:
    
    /** ConfigurationProperties Demo */
    @GetMapping(value="/config")
    public ResponseEntity<YamlDemoConfig> getAppInfo(){ 
        log.debug("YamlDemoConfig: {}", yamlDemoConfig);
        return new ResponseEntity<YamlDemoConfig>(yamlDemoConfig, HttpStatus.OK);
    }//:
    
}///~
