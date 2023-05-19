package com.jirepo.demo.opengraph;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo/opengraph")
public class OpenGraphDemoController {

    /** 
     * 인덱스 페이지 
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        return "opengraph/og-index";
    }//:


    /** Open Graph Content */
    // public static final java.lang.String TEXT_HTML_VALUE = "text/html";
    @GetMapping(value = "/page", produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    public @ResponseBody String  page(HttpServletRequest request) {
        
        // 선언된 변수 들의 값은 Short URL 만들 때 값을 넣어야 한다. 
        // DB에서 가져왔다고 가정한다. 실제로는 DB에서 가져온다.
        String title =  "Short URL Test용 페이지입니다.";  
        String url = "http://jirepos.com/demo/opengraph/page"; 
        String image = "http://jirepos.com/images/gominsi.png"; 
        String description = "Short URL Test용 페이지입니다. 본문 내용을 요약해서 넣어야 합니다."; 

        // 반환할 Short URL용 HTML을 만든다. 
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>");
        builder.append("<head>");
        builder.append("<meta charset=\"UTF-8\" />");
        builder.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />");
        builder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        builder.append("<meta property=\"og:type\" content=\"website\" />");

        // og 태그에 값을 설정한다. 
        builder.append("<meta property=\"og:title\"       content=\"" + title       + "\">");
        builder.append("<meta property=\"og:url\"         content=\"" + url         + "\">");
        builder.append("<meta property=\"og:image\"       content=\"" + image       + "\">");
        builder.append("<meta property=\"og:description\" content=\"" + description + "\">");

        builder.append("</head>");
        builder.append("<body>&nbsp;</body>");
        builder.append("</html>");

        return builder.toString(); 
    }//:
    

    /** URL에 html을 가져와서 Java Bean에 담아 반환한다. */
    @GetMapping("/get-ogdata")
    public ResponseEntity<OpenGraphData> getOgData(){ 
        // 클라언트에서 Short URL 링크를 받으면 Open Graph Data를 생성해서 반환한다. 
        String url = "http://jirepos.com/demo/opengraph/page";
        OpenGraphData data = OpenGraphUtil.getOpenGraph(url); 
        return ResponseEntity.ok(data);
    }//:



}///~
