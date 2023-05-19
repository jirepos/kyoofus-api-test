package com.sogood.biz.blog.service;


import com.sogood.biz.blog.vo.BlogPostVo;
import com.sogood.core.lucene.LuceneIndexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.springframework.stereotype.Service;


/**
 * 블로그 인덱스를 생성하고 검색을 지원한다.
 */
//@Service
public class BlogIndexService {
  /** Indexer  */
  private LuceneIndexer indexer = null;
  /**
   * 생성자 
   * @param openMode OpenMode 값 
   * @throws Exception
   */
  public BlogIndexService(OpenMode openMode) throws Exception  {
    this.indexer = new LuceneIndexer(openMode); 
  }

  /** 생성자 */
  public BlogIndexService() throws Exception  {
    this.indexer = new LuceneIndexer(OpenMode.CREATE_OR_APPEND); 
  }

  /**
   * IndexWriter를 닫는다. 
   * @throws Exception
   */
  public void close() throws Exception  {
    this.indexer.close();
  }
  /** 변경사항을 반영한다.  */
  public void commit() throws Exception { 
    this.indexer.commit();
  }
  /**
   * Post에 대한 인덱스를 만들기 위한 Document를 생성한다. 
   * @param post
   * @throws Exception
   */
  public void addDocument(BlogPostVo post) throws Exception   {
    Document document = new Document();
    document.add(new TextField("id", post.getPostId(), Field.Store.YES));
    document.add(new TextField("blogId", post.getBlogId(), Field.Store.YES));
    document.add(new TextField("content", post.getContent(), Field.Store.YES));
    document.add(new TextField("title", post.getPostTitle(), Field.Store.YES));
    document.add(new TextField("updDt", post.getUpdDt().toInstant().toString(), Field.Store.YES));

    // document.add(new TextField("title", title, Field.Store.YES));
    // document.add(new TextField("author", author, Field.Store.YES));
    // document.add(new TextField("contents", contents, Field.Store.YES));
    // document.add(new TextField("updDate", updDate.toInstant().toString(), Field.Store.YES));
    
    indexer.add(document);
  }
  
}///~

