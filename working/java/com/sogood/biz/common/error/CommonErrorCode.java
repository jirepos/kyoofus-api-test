package com.sogood.biz.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode {
    // Blog Section
    // 에러코드, 다국어 메시지 키
    BLOG_NOT_FOUND(HttpStatus.BAD_REQUEST, "Blog Not Found"),
    PARAM_INSUFFICIENT(HttpStatus.BAD_REQUEST, "Parameter[s] is/are insufficient."),
    INDEX_ERROR(HttpStatus.BAD_REQUEST, "Lucene Indexing faield.");

    private final HttpStatus httpStatus;
    private final String detail;
}///~
