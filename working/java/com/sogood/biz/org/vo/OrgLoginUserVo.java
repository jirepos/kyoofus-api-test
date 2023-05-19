package com.sogood.biz.org.vo;

import java.util.List;

import com.sogood.biz.blog.vo.BlogVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgLoginUserVo extends OrgUserVo {
  protected List<BlogVo> blogs;
}
