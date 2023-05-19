package com.jirepo.demo.google;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.api.services.calendar.model.Event;
import com.jirepo.core.config.util.ApplicationContextHolder;
import com.jirepo.core.google.GoogleApiSettings;
import com.jirepo.core.google.GoogleOauthUtil;
import com.jirepo.demo.google.service.GoogleCalendarBaseService;
import com.jirepo.demo.google.service.GoogleDocBaseService;
import com.jirepo.demo.google.service.GoogleMailService;

/**
 * Google API를 테스트하기 위한 컨트롤러이다.
 *
 */
@Controller
@RequestMapping("/demo/google")
public class GoogleApiController {

    @Autowired
    private GoogleCalendarBaseService googleCalendarService;
    @Autowired
    private GoogleDocBaseService googleDocService;

    @Autowired
    private GoogleMailService googleMailService;

    // @Autowired
    // private GoogleSamlSSOService googleSamlSSOService;

    // 그룹웨어 로그인 사용자의 Gmail ID
    private String gmailId = "jirepos@gmail.com";

    /** index page */
    @GetMapping("/")
    public String index() throws Exception {
        GoogleApiSettings settings = (GoogleApiSettings)ApplicationContextHolder.geApplicationContext().getBean("googleApiSettings");
        System.out.println(settings.getClientId());
        settings.getScopes().forEach(action -> System.out.println(action));
        return "google/google";
    }

    /**
     * 구글 로그인 화면 호출
     */
    @GetMapping("/consent")
    public String oauth2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 인증처리 결과를 받을 callback URL을 설정한다.
        GoogleApiSettings settings = (GoogleApiSettings)ApplicationContextHolder.getBean("googleApiSettings");
        if (GoogleOauthUtil.checkRefreshToken(request, response, gmailId)) {
            return "google/google";
        } else {
            // do something
            // Refresh Token이 없으면 구글 로그인 처리
            GoogleOauthUtil.showConsentScreen(request, response, settings.getCallbackUrl());
        }
        return "google/google";
    }// :

    /** 구글 인증 후 callback URL */
    @RequestMapping("/callback")
    public String oauthCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (GoogleOauthUtil.googleResponseCheck(request, response, gmailId)) {
            // Google Login이 성공하면, Callback URL로 넘긴 파라미터를 가져와서 처리
            // String successProc = request.getParameter("successProc");
            // System.out.println(successProc);
            return "google/google";
        } else {
            // 구글 로그인이 실패하면
            // do something
            return "error";
        }
    }// :



    /** 구글 기본 캘린더의 이벤트 목록을 가져오는 테스트 */
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getPrimaryCalendarEvents() {
        
        List<Event>  list = this.googleCalendarService.getPrimaryEventList(gmailId);
        return new ResponseEntity<List<Event>>(list, HttpStatus.OK);
    }// :

    /** Sevice Account를 사용한 Gmail 카운트 테스트 */
    @GetMapping("/mailcount")
    public ResponseEntity<String> mailCount() throws Exception {
        String mailId = "jirepos@gmail.com";
        int count = this.googleMailService.getMailCount(mailId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
        return new ResponseEntity<String>(String.valueOf(count), HttpStatus.OK);
    }// :

        /** Sevice Account를 사용한 Gmail 카운트 테스트 */
        @GetMapping("/mailcount-service-account")
        public ResponseEntity<String> mailCountServiceAccount() throws Exception {
            String mailId = "jirepos@gmail.com";
            int count = this.googleMailService.getMailCountUsingServiceAccount(mailId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("text", "plan", StandardCharsets.UTF_8)); // UTF-8 설정을 해 주어야 한다.
            return new ResponseEntity<String>(String.valueOf(count), HttpStatus.OK);
        }// :
    




    /** API LIST 화면 요청 */
    @RequestMapping("/apilist")
    public String listApi(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "oauthSuccess";
    }// :

    /** 구글 문서정보를 가져오는 테스트 */
    @RequestMapping("/docfetch")
    public void getDoc() {
        // String docId =
        // "https://docs.google.com/document/d/1iL_5dz5o3rGpdFiIu4JW_fZNzH7CGh3o1Jvf7d_1lVY/edit";
        String docId = "1iL_5dz5o3rGpdFiIu4JW_fZNzH7CGh3o1Jvf7d_1lVY";
        this.googleDocService.fetchDoc(gmailId, docId);
    }// :

    /** 구글 문서를 생성하는 테스트 */
    @RequestMapping("/doccreate")
    public void createDoc() {
        this.googleDocService.createDoc(gmailId);
    }// :

    /** 구글 문서를 복사하는 테스트 */
    @RequestMapping("/doccopy")
    public void copyDoc() {
        String docId = "1iL_5dz5o3rGpdFiIu4JW_fZNzH7CGh3o1Jvf7d_1lVY";
        String copyName = "Copied Document";
        this.googleDocService.copyDoc(gmailId, copyName, docId);
    }// :

    /** 구글 문서를 HTML로 변환하는 테스트 */
    @RequestMapping("/docexporttohtml")
    public void exportDocToHtml() {
        String docId = "1iL_5dz5o3rGpdFiIu4JW_fZNzH7CGh3o1Jvf7d_1lVY";
        this.googleDocService.exportDocToHtml(gmailId, docId);
    }// :

    /** 구글 문서를 다운로드 하는 테스트 */
    @RequestMapping("/docdownload")
    public void downloadDoc() {
        String docId = "1iL_5dz5o3rGpdFiIu4JW_fZNzH7CGh3o1Jvf7d_1lVY";
        this.googleDocService.downloadDoc(gmailId, docId);
    }// :

    /** 구글 문서를 공유하는 테스트 */
    @RequestMapping("/docshare")
    public void shareDoc() {
        try {
            String docId = "1iL_5dz5o3rGpdFiIu4JW_fZNzH7CGh3o1Jvf7d_1lVY";
            List<String> gmailIds = new ArrayList<String>();
            gmailIds.add("latteonterrace@gmail.com");
            this.googleDocService.shareDoc(gmailId, docId, gmailIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// :

    /** 구글 문서 공유 제거 테스트 */
    @RequestMapping("/docunshare")
    public void unshareDoc() {
        try {
            String docId = "1iL_5dz5o3rGpdFiIu4JW_fZNzH7CGh3o1Jvf7d_1lVY";
            List<String> gmailIds = new ArrayList<String>();
            gmailIds.add("latteonterrace@gmail.com");
            this.googleDocService.removePermission(gmailId, docId, gmailIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// :



    // /** SAML Login 테스트 */
    // @RequestMapping("/samllogin")
    // public String smalLogin(HttpServletRequest request, HttpServletResponse
    // response
    // , @RequestParam("SAMLRequest") String samlRequest
    // , @RequestParam("RelayState") String relayState) throws Exception {
    // return googleSamlSSOService.samlResponseAndPage(request, response, gmailId,
    // samlRequest, relayState);
    // }// :

}/// ~
