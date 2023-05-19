package com.jirepo.core.lucene;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.junit.jupiter.api.Test;

public class LuceneSearcherTest {
  

    /** 검색 테스트 케이스  */
    @Test
    public void testSearch() throws Exception  {
      List<String> searchFields = Arrays.asList("title", "updDt", "content");
      //List<String> must = Arrays.asList( "김연아");
      List<String> should = Arrays.asList("송해교");
      SearchOption options = new SearchOption();
      // options.setMust(must);
      options.setShould(should);
      options.setSearchFields(searchFields);
      SearchResult result  = LuceneSearcher.search(options, 1, 10);
      List<Document> list = result.getDocuments();
      for(Document d : list) {
        System.out.println(d.get("id") + "/" + d.get("title") + "/" + d.get("content") + "/"  + d.get("updDt"));
      }
    }//;
  
}
