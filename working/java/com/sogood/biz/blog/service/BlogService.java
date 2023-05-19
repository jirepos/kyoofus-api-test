package com.sogood.biz.blog.service;

import java.util.List;

import com.sogood.biz.blog.mapper.BlogMapper;
import com.sogood.biz.blog.vo.BlogVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
  @Autowired
  private BlogMapper blogMapper; 

  public List<BlogVo> selectUserBlogs(String userId) {
    return this.blogMapper.selectUserBlogs(userId);
  }
  public BlogVo selectBlog(String blogId) {
    return this.blogMapper.selectBlog(blogId);
  }

  
}///~
