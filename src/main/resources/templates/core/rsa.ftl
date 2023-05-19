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
           // 공개키
           let publicKey = `${publicKey}`;
        </script>
    </head>
    <body>
    <h1>RSA Demo</h1>
    <div>아래는 서버에서 생성한 공개키입니다.</div>
    <pre>
      ${publicKey}
    </pre>
    <br>
    <input type="text" id="pwd" value="" placeHolder="비밀번호">
    <br>
    <br>
    <button id="btn">암호전송</button>
    <br>
    <div id="encryptedPwd"></div>
    <script>
        document.querySelector("#btn").addEventListener("click", function(){
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            var pwdElement = document.querySelector("#pwd");
            var encrypted = encrypt.encrypt(pwdElement.value);
            document.querySelector("#encryptedPwd").innerHTML = encrypted;

            let options = {
              method: 'POST', 
              url:'/demo/crypt/decrypt' ,
              body: {
                pwd: encrypted
              },
              headers: {
                'Accept': 'text/plain', // 클라이언트가 이해 가능한 컨텐츠 타입이 무엇인지 
                'Content-Type': 'application/json',  // 서버에 어떤 형식의 데이터를 보내는지 알려줌
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