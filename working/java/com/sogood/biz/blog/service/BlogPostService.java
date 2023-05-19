package com.sogood.biz.blog.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.sogood.biz.blog.code.BlogPostCode;
import com.sogood.biz.blog.mapper.BlogPostMapper;
import com.sogood.biz.blog.mapper.BlogSqlMapper;
import com.sogood.biz.blog.vo.BlogPostVo;
import com.sogood.biz.blog.vo.BlogTagPostVo;
import com.sogood.biz.blog.vo.BlogTagVo;
import com.sogood.biz.org.vo.OrgUserVo;
import com.sogood.core.config.util.PropertyUtil;
import com.sogood.core.constants.CoreConstant;
import com.sogood.core.error.ErrorCode;
import com.sogood.core.exception.CustomException;
import com.sogood.core.util.DateUtils;
import com.sogood.core.util.IdGenerator;
import com.sogood.core.util.IoUtils;
import com.sogood.core.util.StringUtil;
import com.sogood.core.web.session.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlogPostService {
  @Autowired
  private BlogPostMapper blogPostMapper;
  @Autowired
  private BlogTagService blogTagService; 
  @Autowired 
  private BlogService blogService; 
  @Autowired
  private BlogSqlMapper blogSqlMapper; 
//  @Autowired
//  private BlogIndexService blogIndexService;
  @Autowired 
  private BlogTagPostService blogTagPostService; 


  public List<BlogPostVo> selectBlogPosts2() {
    return this.blogSqlMapper.selectBlogPosts2();
  }


  @Transactional
  public BlogPostVo insertPost(BlogPostVo post) throws IOException {
    
    // insert a tag-to-post 
    OrgUserVo user = (OrgUserVo)SessionUtils.getSignedUser();
    if(user == null) {
      throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
    }

    // save a content to a file 
    String uploadDir = PropertyUtil.getProperty(CoreConstant.YAML_KEY_UPLOAD_DIR) +  CoreConstant.POST_DIR_NAME + "/" + post.getBlogId();
    IoUtils.createDirectories(uploadDir);
    String postId =  IdGenerator.generatorId();
    String fileName = postId + ".md";
    File f = new File(uploadDir + "/" + fileName); 
    IoUtils.writeStringToFile(f, post.getContent(), "utf-8");
    log.debug("The content was saved to a file.");
    
    // insert a post
    //post.setBlogId()); parameter 로 전달되어양 함 
    post.setPostId(postId);
    //post.setPostTitle("postTitle");
    //post.setTagNames("tagNames");
    post.setUserId(user.getUserId());
    post.setFileName(postId);
    post.setFileExt("md");
    Date curDate = DateUtils.getCurrentDate();
    post.setUpdDt(curDate);
    post.setRegDt(curDate);
    post.setDelYn(BlogPostCode.BLOG_POST_DELETE_YN_N); // enum type 
    this.blogPostMapper.insertPost(post); 
    log.debug("The post was inserted.");

    // insert a tag if a tag doesn't exist in the datbase and the filed, tagnames is not null.
    if(!(post.getTagNames() == null || post.getTagNames().equals(""))) {
      String[] tmpTags = post.getTagNames().split(",");
      for(String tagName :tmpTags) {
        BlogTagVo tag = this.blogTagService.selectBlogTagName(post.getBlogId(), tagName);
        if(tag == null) { // if the tag does not exist 
          tag = new BlogTagVo();
          tag.setTagId(IdGenerator.generatorId());
          tag.setBlogId(post.getBlogId());
          tag.setTagName(tagName);
          this.blogTagService.insertTag(tag); 
          log.debug("The tag was inserted");
        }
        // insert a tag-to-post 
        BlogTagPostVo tagPost = new BlogTagPostVo();
        tagPost.setBlogId(post.getBlogId());
        tagPost.setPostId(post.getPostId());
        tagPost.setTagId(tag.getTagId());
        this.blogTagPostService.insertTagPost(tagPost);
        log.debug("The tag-to-post was saved.");
      }// for 
    }// inser a tag 

    // 색인 생성
    // try { 
    //   this.blogIndexService.addDocument(post);
    //   this.blogIndexService.commit();
    //   //this.blogIndexService.close();
    // }catch(Exception e) { 
    //   e.printStackTrace();
    //   throw new BusinessException(BlogErrorCode.INDEX_ERROR.getErrorCode(), BlogErrorCode.INDEX_ERROR.getI18nKey());
    // }
    return post; 
  }//:

  @Transactional
  public BlogPostVo updatePost(BlogPostVo post) throws IOException {
    // Deletes all tags of this post 
    this.blogTagPostService.deleteTagPostWithPost(post.getPostId());

    if(!StringUtil.isEmpty(post.getTagNames())) {
      String[] tmpTags = post.getTagNames().split(",");
      for(String tagName :tmpTags) {
        BlogTagVo existTag = this.blogTagService.selectBlogTagName(post.getBlogId(), tagName);
        BlogTagVo newTag = null;
        if(existTag == null) { // if the tag does not exist 
          newTag = new BlogTagVo();
          newTag.setTagId(IdGenerator.generatorId());
          newTag.setBlogId(post.getBlogId());
          newTag.setTagName(tagName);
          this.blogTagService.insertTag(newTag); 
          log.debug("The tag was inserted");
        }
        BlogTagPostVo tagPost = new BlogTagPostVo();
        tagPost.setPostId(post.getPostId());
        if(existTag == null) {
          tagPost.setTagId(newTag.getTagId());
        }else {
          tagPost.setTagId(existTag.getTagId());  
        }
        tagPost.setBlogId(post.getBlogId());
        this.blogTagPostService.insertTagPost(tagPost);  

      } // for 
    }// if

    // 사용하지 않는 태그 삭제 
    this.blogTagService.deleteUnusedTag(post.getBlogId());
    // 사용하지 않는  태그 포스트 매핑 삭제 
    //this.blogTagPostService.deleteUnusedTagPost(post.getBlogId());
        
    // save a content to a file 
    String uploadDir = PropertyUtil.getProperty(CoreConstant.YAML_KEY_UPLOAD_DIR) +  CoreConstant.POST_DIR_NAME + "/" + post.getBlogId();
    IoUtils.createDirectories(uploadDir);
    String fileName = post.getPostId() + ".md";
    File f = new File(uploadDir + "/" + fileName); 
    f.delete();
    IoUtils.writeStringToFile(f, post.getContent(), "utf-8");
    log.debug("The content was saved to a file.");
    this.blogPostMapper.updatePost(post);
    return post; 
  }


  public List<BlogPostVo> selectBlogPosts(String blogId) { 
    return this.blogPostMapper.selectBlogPosts(blogId);
  }
  public List<BlogPostVo> selectTagPosts(BlogTagVo tagVo) { 
    return this.blogPostMapper.selectTagPosts(tagVo);
  }
  
  private List<BlogPostVo> selectPrevPosts(Date regDt) { 
    return this.blogPostMapper.selectPrevPosts(regDt);
  }
  private List<BlogPostVo> selectNextPosts(Date regDt) { 
    if(regDt == null) { 
      regDt = DateUtils.getCurrentDate();
    }
    return this.blogPostMapper.selectNextPosts(regDt);
  }


  public List<BlogPostVo> selectPosts(String direction, String dateStr) { 
    Date regDt = null;
    if(dateStr.equals("null"))  {
      dateStr = null;
    }  
    if(dateStr != null) {
      regDt = new Date(Long.valueOf(dateStr));
    }
    List<BlogPostVo> list = null;
    if(direction.equals("prev")) {
      list = this.selectPrevPosts(regDt);
    }else {
      list = this.selectNextPosts(regDt);
    }
    return list; 
  }//:


  public BlogPostVo selectPost(String postId) {
    if(postId == null || postId.equals("")) {
      // TODO
      //throw new BusinessException(CommonErrorCode.PARAM_INSUFFICIENT.getErrorCode(), CommonErrorCode.PARAM_INSUFFICIENT.getI18nKey());
    }
    BlogPostVo post =  this.blogPostMapper.selectPost(postId);
    String uploadDir = PropertyUtil.getProperty(CoreConstant.YAML_KEY_UPLOAD_DIR) +  CoreConstant.POST_DIR_NAME + "/" + post.getBlogId();
    File f = new File(uploadDir + "/" + post.getFileName() + "." + post.getFileExt());
    post.setContent(IoUtils.readFileToString(f, "utf-8"));
    return post; 
  }
}///~
