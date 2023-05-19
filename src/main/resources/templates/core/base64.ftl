<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
    </head>
    <body>
        <h1>Base64 Encode/Decode 테스트</h1>
        <h3>Content</h3>
        <div>인코딩할 내용을 입력하세요.</div>
        <textarea id="txtContent" rows="10" cols="100"></textarea>
        <h3>Encoded</h3>
        <div>인코딩된 문자열입니다.</div>
        <textarea id="txtEncoded" rows="10" cols="100"></textarea><br />
        <input type="button" id="btnEncode" value="Encode" />
        <input type="button" id="btnDecode" value="Decode" />
        <h3>Decoded</h3>
        <div>디코딩된 문자열입니다.</div>
        <textarea id="txtDecoded" rows="10" cols="100"></textarea>
        <script src="/static/js/codec/base64.js"></script>
        <script>
            window.addEventListener('load', () => {
                let txtContent = document.querySelector('#txtContent');
                console.log(txtContent);
                let txtEncoded = document.querySelector('#txtEncoded');
                let txtDecoded = document.querySelector('#txtDecoded');

                /**
                 * 인코딩
                 */
                document.querySelector('#btnEncode').addEventListener('click', () => {
                    console.log(txtContent.value);
                    //txtEncoded.value = btoa(unescape(encodeURIComponent(txtContent.value)))
                    txtEncoded.value = Base64.encode(txtContent.value);
                });
                /** 
                 * Decoding
                 */
                document.querySelector('#btnDecode').addEventListener('click', () => {
                    //txtDecoded.value = decodeURIComponent(escape(window.atob(txtEncoded.value)))
                    txtDecoded.value = Base64.decode(txtEncoded.value);
                });
            });
        </script>
    </body>
</html>
