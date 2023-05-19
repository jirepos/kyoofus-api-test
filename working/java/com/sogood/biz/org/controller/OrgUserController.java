package com.sogood.biz.org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sogood.biz.org.service.OrgUserService;
import com.sogood.biz.org.vo.OrgUserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/org")
public class OrgUserController {
  @Autowired
  private OrgUserService orgUserService; 
  
  public ResponseEntity<OrgUserVo> insertUser(HttpServletRequest request, HttpServletResponse responise, @RequestBody OrgUserVo vo) {
    OrgUserVo user = this.orgUserService.insertUser(vo);
    return new ResponseEntity<OrgUserVo>(user, HttpStatus.OK);
  }
}///~