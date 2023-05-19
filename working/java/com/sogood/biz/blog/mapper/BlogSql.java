package com.sogood.biz.blog.mapper;

import org.apache.ibatis.jdbc.SQL;

public class BlogSql {

  public static String selectBlogPosts() {
    return new SQL() {
      {
        SELECT("*");
        FROM("blog_post");
      }
    }.toString();
  }//:
  
}///~
