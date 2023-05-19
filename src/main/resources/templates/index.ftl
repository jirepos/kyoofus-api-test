<!DOCTYPE html>
<html>
    <head>
        <title>Home page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#include "./incs/css.ftl">
    </head>
    <body>
        <h1>Demo</h1>
        <ul>
          <li><a href="/demo/base64/index">JavaScript Base64 Demo</a></li>
          <li><a href="/demo/rsa/index">RSA Demo</a></li>
          <li><a href="/demo/jwt/index">JWT Demo</a></li>
          <li><a href="/demo/slf4j/test">SLF4J Logging Demo</a></li>
          <li><a href="/demo/yaml/read-yml">YAML Demo</a></li>
          <li><a href="/demo/yaml/config">@ConfigurationProperties Demo - how to read yaml</a></li>
          <li><a href="/demo/util/request-context">RequestContextHolder Demo</a></li> 
          <li><a href="/demo/res-entity/index">@ResponseEntity Demo</a></li> 
          <li><a href="/demo/res-body/index">@ResponseBody Demo</a></li> 
          <li><a href="/demo/partial-video/index">Video Partial Download Demo</a></li> 
          <li><a href="/demo/i18n/message-source">다국어 메시지 처리 데모</a></li> 
          <li><a href="/demo/error/invoke-biz-error">비즈니스 에러 데모</a></li> 
          <li><a href="/demo/error/invoke-not-login">인가 받지 않는 사용자 예외</a></li> 
          <li><a href="/demo/error/invoke-custom-error">기타 오류 데모</a></li> 
          <li><a href="/demo/cookie/index">서버에서 Cookie 처리 데모</a></li> 
          <li><a href="/demo/file/">파일업로드 데모</a></li> 
          <li><a href="/demo/filter/">Filter 데모</a></li> 
          <li><a href="/demo/event/">Spring Event Handling 데모</a></li> 
          <li><a href="/demo/redis/">Redis 데모</a></li> 
          <li><a href="/demo/google/">Gooogle API 데모</a></li> 
          <li><a href="/demo/fetch/index">fetch() 함수 Demo</a></li> 
          <li><a href="/demo/fetch/ajax">fetch() 함수 Demo2-http2.js 사용</a></li> 
        </ul>
        <#include "./incs/js.ftl">

    </body>
</html>