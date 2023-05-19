package com.jirepo.demo.request;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/demo/res-entity")
public class ResponseEntityDemoController {

    /** index page */
    @GetMapping("/index")
    public String index() {
        return "request/response-entity";
    }

    /** plain/text 반환 */
    @GetMapping("/get-text")
    public ResponseEntity<String> getText(HttpServletRequest request, HttpServletResponse response) {
        String greeting = "반가워요";
        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.TEXT_PLAIN); // MediaType을 설정해야 한다.
        headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>(greeting, headers, HttpStatus.OK);
    }// :

    /** plain/text 반환 */
    @GetMapping("/get-html")
    public ResponseEntity<String> getHtml(HttpServletRequest request, HttpServletResponse response) {
        String html = "<strong>나는 강하다</strong>";
        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.TEXT_HTML); // MediaType을 설정해야 한다.
        headers.setContentType(new MediaType("text", "html", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>(html, headers, HttpStatus.OK);
    }// :

    /** applicatin/json 반환 */
    @GetMapping("/get-json")
    public ResponseEntity<ResponseDemoBean> getJson(HttpServletRequest request, HttpServletResponse response) {
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName("홍길동");
        bean.setAge(10);
        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :

       /** applicatin/json 반환 */
       @PostMapping("/get-json-urlencoded")
       public ResponseEntity<ResponseDemoBean> getJsonUrlencoded(HttpServletRequest request, HttpServletResponse response) {
           ResponseDemoBean bean = new ResponseDemoBean();
           bean.setUserName("홍길동");
           bean.setAge(10);
           return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
       }// :


    /** @PathVariable 사용 */
    @GetMapping("/path-variable/{age}/{name}")
    public ResponseEntity<ResponseDemoBean> getJson(HttpServletRequest request, HttpServletResponse response,
            @PathVariable int age, @PathVariable String name) {
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName(name);
        bean.setAge(age);
        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :

    /** @RequestParam 사용 */
    @GetMapping("/request-param")
    public ResponseEntity<ResponseDemoBean> requestParam(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("age") int age, @RequestParam("name") String name) {
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName(name);
        bean.setAge(age);
        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :

    /** applicatin/json 반환 */
    @PostMapping("/request-body")
    public ResponseEntity<ResponseDemoBean> requestBody(HttpServletRequest request, HttpServletResponse response,
            @RequestBody ResponseDemoBean bean) {
        System.out.println("test");
        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :

    /** @ModelAttribue 사용. JSON 반환 */
    @PostMapping("/model-attribute")
    public ResponseEntity<ResponseDemoBean> modelAttribute(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute ResponseDemoBean bean) {
        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :

    /** 이미지 다운로드 */
    @GetMapping(value = "/get-image")
    public ResponseEntity<byte[]> getImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // Mime Type을 image/png로 설정
        headers.set("Content-Disposition", "attachment;filename=" + "gominsi.png");
        // response.setHeader("Content-Disposition", "attachment;filename=" +
        // "gominsi.png");
        InputStream in = getClass().getResourceAsStream("/public/images/gominsi.png");
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
    }// :

    /** octet-stream으로 이미지 다운로드 */
    @GetMapping(value = "/get-image-octet")
    public ResponseEntity<byte[]> getImageOctet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Mime Type을 image/png로 설정
        headers.set("Content-Disposition", "attachment;filename=" + "gominsi.png");
        // response.setHeader("Content-Disposition", "attachment;filename=" +
        // "gominsi.png");
        InputStream in = getClass().getResourceAsStream("/public/images/gominsi.png");
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
    }// :


    /** applicatin/json 반환 */
    @GetMapping("/fetch-timeout")
    public ResponseEntity<ResponseDemoBean> fetchTimeout(HttpServletRequest request, HttpServletResponse response) {
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName("홍길동");
        bean.setAge(10);

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<ResponseDemoBean>(bean, HttpStatus.OK);
    }// :    


    /** JavaScript 리턴 */
    @GetMapping("/fetch-javascript")
    public ResponseEntity<String> fetchJavascript(HttpServletRequest request, HttpServletResponse response) {
        String html = "alert('hello');";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "javascript", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>(html, headers, HttpStatus.OK);
    }// :



}
