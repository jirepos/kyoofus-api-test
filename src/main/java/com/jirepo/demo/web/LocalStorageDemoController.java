package com.jirepo.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo/storage")
public class LocalStorageDemoController {
      /** index page */
  @GetMapping("/index")
  public String index() {
    return "web/local-storage";
  }
}
