package com.sogood.biz.blog.mapper;

import com.sogood.biz.blog.vo.BlogTagPostVo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogTagPostMapper {
  int insertTagPost(BlogTagPostVo vo);
  int deleteTagPostWithPost(String postId);
  int deleteUnusedTagPost(String blogId); 
}
