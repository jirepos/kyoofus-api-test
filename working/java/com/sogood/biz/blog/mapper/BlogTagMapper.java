package com.sogood.biz.blog.mapper;

import java.util.List;

import com.sogood.biz.blog.vo.BlogTagVo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogTagMapper {

  List<BlogTagVo> selectBlogTags(String blogId);
  BlogTagVo selectBlogTagName(BlogTagVo tagVo);
  int insertTag(BlogTagVo vo);
  int deleteTag(String tagId);
  int deleteUnusedTag(String blogId);
}
