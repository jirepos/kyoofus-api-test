package com.sogood.biz.blog.vo;

import java.util.Date;

import com.sogood.biz.blog.code.BlogPostCode;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogPostVo {
  private String blogId; 
  private String postId; 
  private String postTitle; 
  private String content;
  private String tagNames; 
  private String userId; 
  private String fileName; 
  private String fileExt; 
  private Date regDt; 
  private Date updDt; 
  private BlogPostCode delYn; 
}