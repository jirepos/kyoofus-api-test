package com.sogood.biz.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sogood.biz.login.service.LoginService;
import com.sogood.biz.org.vo.OrgLoginUserVo;
import com.sogood.biz.org.vo.OrgUserVo;
import com.sogood.core.web.message.Message;
import com.sogood.core.web.session.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class LoginController {
  @Autowired
  private LoginService loginService; 
  
  @PostMapping("/login")
  public ResponseEntity<OrgLoginUserVo> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Message<OrgUserVo>  vo) throws Exception {
    log.debug("디버그 모드"); 
    log.info("인포 모드");
    log.info("인포 모드");

    OrgLoginUserVo loginUser = this.loginService.login(vo.getPayload());

    //String key = JSONTokenUtils.createKey();
    return new ResponseEntity<OrgLoginUserVo>(loginUser, HttpStatus.OK);
  }//:
  
  @GetMapping("/get-login-user")
  public ResponseEntity<OrgLoginUserVo> getLoginUser(HttpServletRequest request, HttpServletResponse response) {
    OrgLoginUserVo loginUser = (OrgLoginUserVo)SessionUtils.getSignedUser();
    return new ResponseEntity<OrgLoginUserVo>(loginUser, HttpStatus.OK);
  }//:
  
  @GetMapping("/is-signed")
  public ResponseEntity<Boolean> isSigned(HttpServletRequest request, HttpServletResponse response) {
    OrgUserVo signedUser = (OrgUserVo)SessionUtils.getSignedUser();
    if(signedUser == null) {
      return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
    }else {
      return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
  }//:
}///~