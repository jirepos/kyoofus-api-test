package com.sogood.biz.org.service;

import com.sogood.biz.org.mapper.OrgUserMapper;
import com.sogood.biz.org.vo.OrgUserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgUserService {

  @Autowired
  private OrgUserMapper orgUserMapper; 
  
  public OrgUserVo insertUser(OrgUserVo vo) {
    this.orgUserMapper.insertUser(vo);
    return vo; 
  }
  public OrgUserVo selectUser(String email) {
    return this.orgUserMapper.selectUser(email);
  }

}
