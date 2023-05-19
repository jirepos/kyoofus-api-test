package com.jirepo.demo.request;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jirepo.core.util.FileDownloader;

/** Video Partial Download 테스트 컨트롤러 */
@Controller
@RequestMapping("/demo/partial-video")
public class PartialVideoDemoController {

    
    
    /** index page */
    @GetMapping("/index")
    public String index() {
        return "request/partial-video";
    }

    /** 비디오 파셜 다운로드 */
    @GetMapping(value = "/video/{fileName}")
    public ResponseEntity<ResourceRegion> getPartialVideo(HttpServletRequest request, HttpServletResponse response,
                                                          @PathVariable("fileName") String fileName, @RequestHeader HttpHeaders headers) throws Exception {
        UrlResource video = new UrlResource("classpath:/public/media/" + fileName);
        return FileDownloader.partialDownload(request, response, headers, video);
        // ResourceRegion region = resourceRegion(video, headers);
        // return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        // 		.contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM)).body(region);
    }// :



}///`
