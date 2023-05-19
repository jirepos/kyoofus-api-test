package com.sogood.biz.blog.service;

import java.util.List;

import com.sogood.biz.blog.mapper.BlogTagMapper;
import com.sogood.biz.blog.vo.BlogTagVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class BlogTagService {

  @Autowired
  private BlogTagMapper blogTagMapper; 
  @Autowired
  private BlogService blogService; 

  public BlogTagVo selectBlogTagName(String blogId, String tagName) {
    BlogTagVo tagParam = new BlogTagVo();
    tagParam.setBlogId(blogId);
    tagParam.setTagName(tagName);
    return  blogTagMapper.selectBlogTagName(tagParam);
  }

  
  public boolean existTag(String blogId, String tagName) {
    BlogTagVo tagVo = selectBlogTagName(blogId, tagName);
    return (tagVo == null) ? false: true; 
  }
  
  public List<BlogTagVo> selectBlogTags(String blogId) { 
    return this.blogTagMapper.selectBlogTags(blogId);
  }

  public int insertTag(BlogTagVo vo) {
    return this.blogTagMapper.insertTag(vo);
  }
  public int deleteTag(String tagId) {
    return this.blogTagMapper.deleteTag(tagId);
  }
  public int deleteUnusedTag(String blogId)  {
    return this.blogTagMapper.deleteUnusedTag(blogId);
  }
}