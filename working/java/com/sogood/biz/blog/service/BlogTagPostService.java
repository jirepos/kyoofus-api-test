package com.sogood.biz.blog.service;

import com.sogood.biz.blog.mapper.BlogTagPostMapper;
import com.sogood.biz.blog.vo.BlogTagPostVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogTagPostService {
  
  @Autowired
  private BlogTagPostMapper blogTagPostMapper; 

  public int insertTagPost(BlogTagPostVo vo) { 
    return this.blogTagPostMapper.insertTagPost(vo);
  }//:
  public int deleteTagPostWithPost(String postId) { 
    return this.blogTagPostMapper.deleteTagPostWithPost(postId);
  }//:
  public int deleteUnusedTagPost(String blogId)  {
    return this.blogTagPostMapper.deleteUnusedTagPost(blogId);
  }

}///~
