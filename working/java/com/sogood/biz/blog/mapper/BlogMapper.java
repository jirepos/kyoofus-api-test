package com.sogood.biz.blog.mapper;

import java.util.List;

import com.sogood.biz.blog.vo.BlogVo;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BlogMapper {
  BlogVo selectBlog(String blogId);
  List<BlogVo> selectUserBlogs(String userId);
  int insertBlog(BlogVo vo);
}
