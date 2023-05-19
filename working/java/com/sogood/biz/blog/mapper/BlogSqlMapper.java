package com.sogood.biz.blog.mapper;

import java.util.List;

import com.sogood.biz.blog.vo.BlogPostVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface BlogSqlMapper {
  
  @SelectProvider(type = BlogSql.class, method="selectBlogPosts")
  List<BlogPostVo> selectBlogPosts2();
  
}///~
