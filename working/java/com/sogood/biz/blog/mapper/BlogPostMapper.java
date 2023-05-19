package com.sogood.biz.blog.mapper;

import java.util.Date;
import java.util.List;

import com.sogood.biz.blog.vo.BlogPostVo;
import com.sogood.biz.blog.vo.BlogTagVo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogPostMapper {
  List<BlogPostVo> selectBlogPosts(String blogId);
  List<BlogPostVo> selectTagPosts(BlogTagVo tagVo);
  List<BlogPostVo> selectPrevPosts(Date regDt);
  List<BlogPostVo> selectNextPosts(Date regDt);
  BlogPostVo selectPost(String postId);
  int insertPost(BlogPostVo vo);
  int deletePost(String postId);
  int updatePost(BlogPostVo vo);
}
