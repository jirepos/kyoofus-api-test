package com.jirepo.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/indexeddb")
public class IndexedDbDemoController {

  /** index page */
  @GetMapping("/index")
  public String index() {
    return "web/indexeddb";
  }

  /** index page */
  @GetMapping("/index2")
  public String index2() {
    return "web/indexeddb2";
  }

}
