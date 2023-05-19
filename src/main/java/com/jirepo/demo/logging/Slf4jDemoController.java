package com.jirepo.demo.logging;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/** SLF4J logging test controller */
@Controller
@RequestMapping("/demo/slf4j")
@Slf4j
public class Slf4jDemoController {

      /** slf4j의 log.debug() 테스트  */
      @GetMapping(value="/test",  produces="text/plain;charset=utf-8")
      public @ResponseBody String test() {
        log.debug("logging debug test");
        return "logging test"; 
      }//:
}//~
