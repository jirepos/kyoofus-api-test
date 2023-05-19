<!DOCTYPE html>
<html>
    <head>
        <title>Home page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="/static/js/http/http.js"></script>
        <script src="/static/js/codec/base64.js"></script>
        <script src="/static/js/crypt/jsencrypt.js"></script>
        <script>
           let jws = "${jws}";
        </script>
    </head>
    <body>
    <h1>JWT Demo</h1>
    <div>아래는 서버에서 생성한 JWS입니다.</div>
    <div>
      <textarea style="width:600px; height: 200px;">${jws}</textarea>
    </div>
    <button id="btnSendToken">Token 헤더 전송 테스트</button> 

    <script>
        document.querySelector("#btnSendToken").addEventListener("click", function(){
            
            let options = {
              method: 'POST', 
              url:'/demo/jwt/send' ,
              body: {
                input : "test"
              },
              headers: {
                'Authorization': 'Bearer ' + jws,
                'Accept': 'text/plain', // 클라이언트가 이해 가능한 컨텐츠 타입이 무엇인지 
                'Content-Type': 'application/text',  // 서버에 어떤 형식의 데이터를 보내는지 알려줌
              }
            }
            ajax(options)
            .then((response) => {
              console.log(response);
              log(response);
            })
            .catch(error => {
            })
        }); 
    </script>
    </body>
</html>