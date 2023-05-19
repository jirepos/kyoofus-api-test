package com.sogood.biz.org.mapper;

import com.sogood.biz.org.vo.OrgUserVo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgUserMapper {
  public int insertUser(OrgUserVo vo);
  public OrgUserVo selectUser(String email);
}
