package com.jirepo.demo.opengraph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpenGraphData {
    /** 컨텐츠 타이츨 */
    private String title; 
    /** 컨텐츠 이미지  */
    private String image; 
    /** URL */
    private String url; 
    /** Domain. https://를 뺀 URL  */
    private String domain; 

    /** 간단한 컨텐츠 설명 */
    private String description;
}
