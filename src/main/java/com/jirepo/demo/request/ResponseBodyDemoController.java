package com.jirepo.demo.request;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/** ResponseBody 어노테이션을 테스트하기 위한 컨트롤러 */
@Controller
@RequestMapping("/demo/res-body")
public class ResponseBodyDemoController {

    
    /** index page */
    @GetMapping("/index")
    public String index() {
        return "request/response-body";
    }

    
    /** plain/text 반환 */
    @GetMapping(value = "/get-text", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    public @ResponseBody String getText(HttpServletRequest request, HttpServletResponse response) {
        String greeting = "반가워요";
        return greeting;
    }//:

    /** plain/text 반환 */
    @GetMapping(value = "/get-html", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    public @ResponseBody String getHtml(HttpServletRequest request, HttpServletResponse response) {
        String html = "<strong>나는 강하다</strong>";
        return html;
    }//:

    /** applicatin/json 반환 */
    @GetMapping(value="/get-json", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public @ResponseBody ResponseDemoBean getJson(HttpServletRequest request, HttpServletResponse response) {
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName("홍길동");
        bean.setAge(10);
        return bean;
    }//:

    /** @PathVariable 사용 */
    @GetMapping("/path-variable/{age}/{name}")
    public @ResponseBody ResponseDemoBean getJsonPath(HttpServletRequest request, HttpServletResponse response
            , @PathVariable int age, @PathVariable String name) {
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName(name);
        bean.setAge(age);
        return bean;
    }//:

    /** @RequestParam 사용 */
    @GetMapping("/request-param")
    public @ResponseBody ResponseDemoBean requestParam(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("age") int age, @RequestParam("name") String name) {
        ResponseDemoBean bean = new ResponseDemoBean();
        bean.setUserName(name);
        bean.setAge(age);
        return bean;
    }//:

    /** applicatin/json 반환 */
    @PostMapping("/request-body")
    public @ResponseBody ResponseDemoBean requestBody(HttpServletRequest request, HttpServletResponse response
            , @RequestBody ResponseDemoBean bean) {
        System.out.println("test");
        return bean;
    }//:

    /** @ModelAttribue 사용. JSON 반환 */
    @PostMapping("/model-attribute")
    public @ResponseBody ResponseDemoBean modelAttribute(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute ResponseDemoBean bean) {
        return bean;
    }//:

    /** 이미지 다운로드 */
    @GetMapping(value = "/get-image", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = getClass().getResourceAsStream("/public/images/gominsi.png");
        return IOUtils.toByteArray(in);
    }// :

    /** octet-stream으로 이미지 다운로드 */
    @GetMapping(value = "/get-image-octet", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getImageOctet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = getClass().getResourceAsStream("/public/images/gominsi.png");
        response.setHeader("Content-Disposition", "attachment;filename=gominsi.png");
        response.setHeader("Content-Transfer-Encoding", "binary");
        //response.setContentLength((int) file.length());
        return IOUtils.toByteArray(in);
    }// :
    
}
