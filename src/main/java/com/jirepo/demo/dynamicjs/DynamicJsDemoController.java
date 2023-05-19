package com.jirepo.demo.dynamicjs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/demo/dynamic-js")
public class DynamicJsDemoController {

    /** index page */
    @GetMapping("/index")
    public String index() {
        return "dynamicjs/dynamicjs";
    }// :

    
    /**
     * 파라미터에 포워드할 페이지를 전달한다. 
     * @param page 포워드할 페이지
     * @throws Exception
     */
    @PostMapping("/page")
    public void page(HttpServletRequest request, HttpServletResponse reponse, @NotNull @RequestParam("page") String page) throws Exception{
        // forward 
        request.getRequestDispatcher(page).forward(request, reponse);
    }// :

  
}// :
    

