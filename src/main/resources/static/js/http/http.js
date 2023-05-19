const MIMEType = {
  text: ["text/plain"], // 특정 서브 타입이 없는 텍스트 문서들에 대해서는 text/plain이 사용되어야 한다. 
  xml: ["text/xml"],
  html: ["text/html"],
  css: ["text/css"],
  json: ["application/json"],
  javascript: ["text/javascript"],
  video: [
    "video/webm"
  ],
  audio: [
    "audio/mpeg"
  ],
  // 오직 널리 알려지고 웹에 안전하다고 취급되는 소수의 이미지 타입만이 사용될 수 있다. 
  image: ["image/gif", "image/png", "image/jpeg", "image/bmp"],
  // application은 모든 종류의 이진 데이터를 나타낸다. 
  // application/octet-stream은 잘 알려지지 않는 이진파일을 의미. 
  // 브라우저는 보통 자동으로 실행하지 않거나 실행해야 할지 묻기도 한다. 
  // 대부분의 웹 서버들은 기본 타입 중 하나인 application/octet-stream MIME 타입을 사ㅛㅇㅇ해 
  // 알려지지 않은 타입의 리소스를 전송한다. 보안상의 이유로 대부분의 브라우저들은 그런 리소스에 대한
  // 사용자화된 기본 동작 설정을 허용하지 않으며 그것을 사용하려면 디스크에 저장할 것을 사용자에게 강제한다. 
  binary: ["application/octet-stream"] // 서브타입이 없는 이진 문서에 대해서는 application/octet-stream을 사용 
}// MIMEype


/**
     * 모든 비동기 HTTP 요청을 처리하기 위한 함수이다. 내부적으로 는 Fetch API를 사용한다. 
     * @param {String} url  the request URL 
     * @param {Object} options the request options
     * @returns 
     */
const ajax = (options) => {
  //  fetch() 함수에 전달될 기본 옵션이다. 
  let defaultOptions = {
    credentials: 'include',   //'same-origin',    // 자격증명이 포함된 요청을 하려면 이 줄을 추가해야. 이 옵션이 없으면 쿠키 값을 서버로 보내지 않음 
    method: 'POST',  // GET, POST, PUT, DELETE, etc 
    mode: 'cors', //'no-cors',    // no-cors, cors, *same-origin // cors로 값을 설정해야 Content-Type의 값을 설정할 수 있음
    chache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    //credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Accept': 'application/json', // 클라이언트가 이해 가능한 컨텐츠 타입이 무엇인지 
      'Content-Type': 'application/json',  // 서버에 어떤 형식의 데이터를 보내는지 알려줌
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    // callback는 모두 필요 없을 것으로 판단 
    //successCallback: () => {},  // 성공했을 때 처리. 이 함수를 호출하는 코드에서 then()으로 처리하면 될 것 같음 
    //bizErrorCallback: () => {}, // http status는 성공이지만 business logic 오류가 있을 때 처리
    //errorCallback: () => {},    // 서버 오류가 발생했을 때 처리
    //redirect: 'follow', // manual, *follow, error
    //referrer: 'no-referrer', // no-referrer, *client
    //body: JSON.stringify(data), // body data type must match "Content-Type" header
  }

  let reqOptions = Object.assign(defaultOptions, options)
  if (reqOptions.body) {
    if (!reqOptions.isFileUpload) {
      reqOptions.body = JSON.stringify(reqOptions.body)
    }
  }


  return new Promise((resolve, reject) => {
    fetch(options.url, reqOptions)
      // 우선 json을 응답으로 받는다는 가정하에 
      // res.json()을 사용한다. 
      // 첫번재 then()에서 json() 함수를 호출하고 return해야 한다. 
      // 두번째 then()에서 응답 body의 데이터를 받을 수 있다. 
      .then(res => {
        //debugger; 

        if (res.ok) {  // res.ok로 반드시 체크
          let contentType = res.headers.get("Content-Type")
          //console.log(contentType) 
          if (!contentType) {
            return null // REST API 호출 시 값이 없으면 Content-Type이 없음 
          }
          if (contentType.indexOf("application/json") >= 0) {
            return res.json()
          } else if (contentType.indexOf("text/xml") >= 0) {
            return res.text()
          } else if (contentType.indexOf("text/plain") >= 0) {
            return res.text()
          } else if (contentType.indexOf("image/png") >= 0) {
            return res.blob()
          } else if (contentType.indexOf("application/octet-stream") >= 0) {
            return res.blob()
          } else {
            // application/xml, text/html은 여기서 처리함 
            return res.text()
          }
        } else {

          //debugger
          let MyError = function (status, body, headers) {
            this.status = status;
            this.body = body;
            this.headers = headers;
          }
          if (res.status == 400 && res.headers.get('content-type') === 'application/json') {
            throw new MyError(res.status, res.json(), res.headers)
          } else {
            throw new MyError(res.status, null, res.headers)
          }

        }
      })
      .then(res => {
        // 응답이 성공인 경우 
        resolve(res)
        // 응답은 성공이나 비즈니스 오류가 있는 경우 
        //if(bizErrorCallback) { bizErrorCallback() } 비즈니스 로직 오류가 발생한 경우에는 공통 처리만 필요
        //if(res.status == 200) { 
        //reject(res) 
        //}
      })
      .catch(error => {
        //debugger
        switch (error.status) {
          case 500:
            alert("system error")
            break;
          case 409:
            alert(error.headers.get("X-Message-Code") + ":" + error.headers.get("X-Message"))
            break;
          case 400:
            alert(error.headers.get("X-Message-Code") + ":" + error.headers.get("X-Message"))
            //
            error.body.then(res => {
              //console.log(res); 
              alert(res.code + "//" + res.error);
            })
            break;
          case 404:
            alert("404: 잘못된 요청으로 해당된 리소스를 찾을 수 없습니다.")
            break;
          case 403:
            alert("권한 없음")
            break;
          case 405:
            alert("호출 방식이 잘못되었습니다. POST/GET 방식을 확인하여 주세요.")
            break;
          case 406:
            alert("서버에서 요청을 허용하지 않습니다. 메서드 정의를 살펴주세요.")
            break;
          case 401:
            alert("로그인 후에 이용하여 주세요")
            break;
        }
        if (error instanceof TypeError) {
          alert("호출 형식이 잘못되었습니다:" + error.message);
        }
        // 응답이 오류인 경우 
        //if(errorCallback) { errorCallback() }
        reject(error)
      })
  })
}







  //export { MIMEType, ajax };
