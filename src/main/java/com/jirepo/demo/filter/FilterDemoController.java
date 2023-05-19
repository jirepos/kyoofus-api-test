package com.jirepo.demo.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.demo.request.ResponseDemoBean;

/**
 * Filter Test를 위한 데모 컨트롤러이다.
 */
@Controller
@RequestMapping("/demo/filter")
public class FilterDemoController {

    /** 도메인만 입력 시 랜딩 페이지 표시 */
    @GetMapping("/")
    public String baseUrl() {
        return "filter/filter";
    }

    /** applicatin/json 반환 */
    @GetMapping("/get-json")
    public ResponseEntity<ResponseDemoBean> getJson(HttpServletRequest request, HttpServletResponse response) {
        // 
        String age = request.getParameter("age");
        String[] job = request.getParameterValues("job");
        System.out.println(age);
        System.out.println(job);
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName("홍길동(Gildong,Hong");
        bean.setAge(10);
        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :
    
}
