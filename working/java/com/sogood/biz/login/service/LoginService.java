package com.sogood.biz.login.service;

import com.sogood.biz.blog.service.BlogService;
import com.sogood.biz.org.service.OrgUserService;
import com.sogood.biz.org.vo.OrgLoginUserVo;
import com.sogood.biz.org.vo.OrgUserVo;
import com.sogood.core.error.ErrorCode;
import com.sogood.core.exception.CustomException;
import com.sogood.core.web.session.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

  @Autowired
  private OrgUserService orgUserService; 
  @Autowired
  private BlogService blogService; 


  public OrgLoginUserVo login(OrgUserVo vo) {
    OrgUserVo user = this.orgUserService.selectUser(vo.getEmail());
    if(user == null) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }
    if(!user.getPassword().equals(vo.getPassword())) {
      throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
    }
    OrgLoginUserVo loginUser = new OrgLoginUserVo();
    loginUser.setEmail(user.getEmail());
    loginUser.setUserId(user.getUserId());
    loginUser.setName(user.getName());
    loginUser.setBlogs(this.blogService.selectUserBlogs(user.getUserId()));
    SessionUtils.setSignedUser(loginUser);
    return loginUser;
  }
}
