package com.sogood.biz.blog.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sogood.biz.blog.service.BlogPostService;
import com.sogood.biz.blog.service.BlogService;
import com.sogood.biz.blog.service.BlogTagService;
import com.sogood.biz.blog.vo.BlogPostVo;
import com.sogood.biz.blog.vo.BlogTagVo;
import com.sogood.biz.blog.vo.BlogVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController {
  @Autowired
  private BlogPostService blogPostService; 
  @Autowired
  private BlogTagService blogTagService; 
  @Autowired
  private BlogService blogService; 

  // Post
  @PostMapping( value="/insert-post")
  public ResponseEntity<BlogPostVo> insertPost(HttpServletRequest request, HttpServletResponse response, @RequestBody BlogPostVo vo) throws IOException {
    log.debug(vo.getContent());
    //vo.setUserId("latte");
    vo = blogPostService.insertPost(vo);
    //ResponseEntity<BlogPostVo> re =  new ResponseEntity<BlogPostVo>(vo, null, 300); /// custom status 
    return  new ResponseEntity<BlogPostVo>(vo, HttpStatus.OK);
  }//:

    // Post
    @PostMapping( value="/update-post")
    public ResponseEntity<BlogPostVo> updatePost(HttpServletRequest request, HttpServletResponse response, @RequestBody BlogPostVo vo) throws IOException {
      log.debug(vo.getContent());
      //vo.setUserId("latte");
      vo =blogPostService.updatePost(vo);
      //ResponseEntity<BlogPostVo> re =  new ResponseEntity<BlogPostVo>(vo, null, 300); /// custom status 
      return  new ResponseEntity<BlogPostVo>(vo,  HttpStatus.OK);
    }//:

  @GetMapping( value="/select-blog-posts/{blogId}")
  public ResponseEntity<List<BlogPostVo>> selectblogPosts(HttpServletRequest request, HttpServletResponse response, @PathVariable String blogId) throws IOException {
    List<BlogPostVo> postList = this.blogPostService.selectBlogPosts(blogId);
    return  new ResponseEntity<List<BlogPostVo>>(postList ,  HttpStatus.OK);
  }//:

  @GetMapping( value="/select-blog-posts2")
  public ResponseEntity<List<BlogPostVo>> selectblogPosts2(HttpServletRequest request, HttpServletResponse response) throws IOException {
    List<BlogPostVo> postList = this.blogPostService.selectBlogPosts2();
    return  new ResponseEntity<List<BlogPostVo>>(postList ,  HttpStatus.OK);
  }//:



  @GetMapping( value="/select-blog/{blogId}")
  public ResponseEntity<BlogVo> selectBlog(HttpServletRequest request, HttpServletResponse response, @PathVariable String blogId) throws IOException {
    BlogVo blog = this.blogService.selectBlog(blogId);
    return  new ResponseEntity<BlogVo>(blog ,  HttpStatus.OK);
  }//:
  @GetMapping( value="/select-post/{id}")
  public ResponseEntity<BlogPostVo> selectPost(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws IOException {
    BlogPostVo post = this.blogPostService.selectPost(id);
    return  new ResponseEntity<BlogPostVo>(post ,  HttpStatus.OK);
  }//:
  @GetMapping( value="/select-tag-posts/{blogId}/{tagId}")
  public ResponseEntity<List<BlogPostVo>> selectTagPosts(HttpServletRequest request, HttpServletResponse response, @PathVariable String blogId, @PathVariable String tagId ) throws IOException {
    BlogTagVo param = new BlogTagVo();
    param.setBlogId(blogId);
    param.setTagId(tagId);
    List<BlogPostVo> list = this.blogPostService.selectTagPosts(param);
    return  new ResponseEntity<List<BlogPostVo>>(list ,  HttpStatus.OK);
  }//:

  // Tags
  @GetMapping( value="/select-blog-tags/{blogId}")
  public ResponseEntity<List<BlogTagVo>> selectBlogTags(HttpServletRequest request, HttpServletResponse response, @PathVariable String blogId) throws IOException {
    List<BlogTagVo> tagList = this.blogTagService.selectBlogTags(blogId);
    return  new ResponseEntity<List<BlogTagVo>>(tagList ,  HttpStatus.OK);
  }//:
  
  @GetMapping( value="/select-posts/{direction}/{dateStr}")
  public ResponseEntity<List<BlogPostVo>> selectPosts(HttpServletRequest request, HttpServletResponse response,@PathVariable String direction, @PathVariable(required=false) String dateStr) throws IOException {
    
    List<BlogPostVo> list = this.blogPostService.selectPosts(direction, dateStr);
    return  new ResponseEntity<List<BlogPostVo>>(list ,  HttpStatus.OK);
  }//:
 
}////~
