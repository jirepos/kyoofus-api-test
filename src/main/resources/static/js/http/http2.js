
/**
 * Ajax 호출 시 예외가 발생한  경우 사용할 예외 클래스이다. 
 */
class AjaxError extends Error {
    /** 기본 생성자이다. */
    constructor(status, body, headers) {
        super(""); // 부모 생성자에 메시지를 전달해야 하는데 body가 json인 경우 전달할 수 없어 빈 문자열을 전달한다.
        this.status = status;  // HTTP Status 
        this.name = 'AjaxError';  // 이 객체의 오류 이름 
        this.body = body;  // JSON으로 응답을 받은 경우 응답 객체 
        this.headers = headers; // 응답 헤더
        // 응답이 json인지 여부 
        this.isJson = headers.get('content-type').includes('application/json');
    }
}///~



/**
 * Ajax 통신을 처리하기 여러 메서드를 제공한다. 
 */
class Ajax {

    // Constnats 
    // HTTP METHOD
    static HTTP_METHOD_GET = "GET";
    static HTTP_METHOD_POST = "POST";

    // CONTENT-TYPE
    static CONTENT_TYPE_HTML = "text/html";
    static CONTENT_TYPE_JSON = "application/json";
    static CONTENT_TYPE_XML = "text/xml";
    static CONTENT_TYPE_TEXT = "text/plain";
    // application/javascript를 사용할 경우에는 IE8 이하는 지원이 되지 않게 된다.
    // 
    static CONTENT_TYPE_JAVASCRIPT = "text/javascript";
    static CONTENT_TYPE_BINARY = "application/octet-stream";
    static CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";

    // HttpStaatus 
    static HttpStatus = {
        OK: 200,
        FORBIDDEN: 403,
        NOT_FOUND: 404,
        INTERNAL_SERVER_ERROR: 500,
        BAD_REQUEST: 400,
        UNAUTHORIZED: 401,
        METHOD_NOT_ALLOWED: 405,
        NOT_ACCEPTABLE: 406,
        SERVICE_UNAVAILABLE: 503,
        CONFLICT: 409,
    }


    // GET Method Section 

    /**
     * GET Method로 JSON을 요청한다.
     * @param {*} url 요청 URL 
     * @param {*} options  요청 옵션
     * @returns     Promise 객체
     */
    static json(url, options) {
        // debugger;
        let defaultOptions = {
            method: this.HTTP_METHOD_GET,
            headers: {
                'Content-Type': this.CONTENT_TYPE_JSON,
                'Accept': this.CONTENT_TYPE_JSON
            },
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.ajax(url, reqOptions)
    }

   /**
     * GET Method로 HTML을 요청한다.
     * @param {*} url 요청 URL 
     * @param {*} options  요청 옵션
     * @returns     Promise 객체
     */
    static html(url, options) {
        // debugger;
        let defaultOptions = {
            method: this.HTTP_METHOD_GET,
            headers: {
                'Content-Type': this.CONTENT_TYPE_JSON,
                'Accept': this.CONTENT_TYPE_HTML
            },
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.ajax(url, reqOptions)
    }


    /**
     * GET Method로 TEXt을 요청한다.
     * @param {*} url 요청 URL
     * @param {*} options   요청 옵션
     * @returns     Promise 객체
     */
    static text(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_GET,
            headers: {
                'Content-Type': this.CONTENT_TYPE_JSON,
                'Accept': this.CONTENT_TYPE_TEXT
            },
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.ajax(url, reqOptions)
    }

    static javascript(url, async = false) { 
        return new Promise((resolve, reject) => {
            try {
                const script = document.createElement("script");
                script.setAttribute("src", url); 
                // async가 false이면 다음 액션에 진행하기 전에 로드되고 실행된다. 
                script.setAttribute("async", async); 
                script.setAttribute("type", Ajax.CONTENT_TYPE_JAVASCRIPT); 
    
                script.addEventListener("load", (ev) => {
                    resolve({ status: true });
                });
    
                script.addEventListener("error", (ev) => {
                    reject({
                        status: false,
                        message: `Failed to load the script ＄{url}`
                    });
                });
                document.body.appendChild(script);
            } catch (error) {
                reject(error);
            }
        });
    }//:





    /**
     * GET 방식으로 파일을 요청한다. Image의 src 속성에 blob을 적용할 때 사용한다. 
     * @param {*} url   요청 URL
     * @param {*} options   요청 옵션
     * @returns     Promise 객체
     */
    static blob(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_GET,
            headers: {
                'Content-Type': this.CONTENT_TYPE_JSON,
                'Accept': this.CONTENT_TYPE_BINARY
            },
            downloadFileName: 'undefined'
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.ajax(url, reqOptions)
    }


    /**
     * GET 방식으로 파일을 다운로드 한다. 
     * @param {*} url   요청 URL
     * @param {*} options   요청 옵션
     * @returns     Promise 객체
     */
    static download(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_GET,
            headers: {
                'Content-Type': this.CONTENT_TYPE_JSON,
                'Accept': this.CONTENT_TYPE_BINARY
            },
            downloadFileName: 'undefined'  // 다운로드 받을 파일 이름
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return new Promise((resolve, reject) => {
            this.ajax(url, reqOptions)
                .then(blob => {
                    // debugger;
                    let a = document.createElement('a');
                    a.href = URL.createObjectURL(blob);
                    //<a href='filepath' download>
                    // download 속성에는 filename 옵션이 존재하는데,  download="filename" 을 입력함으로 실제 파일과 다른 이름으로 다운로드 제공이 가능하다.
                    a.download = reqOptions.downloadFileName;
                    a.click();
                    //
                    resolve(blob);
                })
                .catch(err => {
                    reject(err);
                });
        });
    }// download()

    

    // POST Method Section 


    /**
     * POST Method로 JSON을 요청한다.
     * @param {*} url  요청 URL
     * @param {*} options  요청 옵션
     * @returns  Promise 객체 
     */
    static postJson(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_POST,
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.json(url, reqOptions)
    }

   /**
     * POST Method로 JSON을 요청한다.
     * @param {*} url  요청 URL
     * @param {*} options  요청 옵션
     * @returns  Promise 객체 
     */
    static postHtml(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_POST,
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.html(url, reqOptions)
    }


    /**
     * POST Method로 TEXT를 요청한다.
     * @param {*} url  요청 URL
     * @param {*} options  요청 옵션
     * @returns  Promise 객체
     */
    static postText(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_POST,
        }
        options = (options) ? options : {};
        let reqOptions = Object.assign(defaultOptions, options)
        return this.text(url, reqOptions)
    }


    /**
     * POST Method로 파일을 요청한다. Image의 src 속성에 blob을 적용할 때 사용한다. 
     * @param {*} url  요청 URL
     * @param {*} options  요청 옵션
     * @returns  Promise 객체
     */
    static postBlob(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_POST,
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.blob(url, reqOptions)
    }

    /**
     * POST Method로 파일을 다운로드 한다.
     * @param {*} url  요청 URL
     * @param {*} options  요청 옵션
     * @returns  Promise 객체
     */
    static postDownload(url, options) {
        let defaultOptions = {
            method: this.HTTP_METHOD_POST,
        }
        let reqOptions = Object.assign(defaultOptions, options)
        return this.download(url, reqOptions)
    }

    /**
     * 파일업로드. 파일업로드는 Post방식만 가능하다. 
     * @param {*} url  요청 URL
     * @param {*} options  요청 옵션
     * @returns     Promise 객체
     */
    static upload(url, options) {
        debugger;
        let defaultOptions = {
            method: this.HTTP_METHOD_POST,
            isFileUpload: true, 
            headers: {
                'Accept': this.CONTENT_TYPE_JSON
            },
        }
        let reqOptions = Object.assign(defaultOptions, options);
        // reqOptions.headers = {}; // 헤더를 확실히 비움
        return this.ajax(url, reqOptions)
    }//:

    /**
     * fetch() 함수에 전달할 옵션을 생성한다. 
     * @param {*} options 전달된 옵션
     * @returns fetch() 함수에 전달할 옵션
     */
    static #createOptions(options) {
        // debugger;
        //  fetch() 함수에 전달될 기본 옵션이다. 
        let defaultOptions = {
            async: false,   // 기본은 await 한다. 
            timeout: 60 * 1000 * 2,  // 타임아웃 120초
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
            // redirect 
            //   - manual : 리다이렉트를 허용하지 않는다.\
            //   - error : 리다이렉트 응답을 에러로 처리한다.
            //   - follow : 리다이렉트 응답을 허용한다. (HTTP status code = 301,302,303,307,308)
            // fetch는 바로 redirect된 페이지로 이동이 안된다. 
            // redirect response가 왔을 때 다시 한번 해당 url로 요청을 보내야한다.           
            // response.redirected가 true일 경우, 다음 방법 중 하나를 사용해야한다.
            //  - window.location.href \= response.url
            //  - response.redirect(response.url)
            //redirect: 'follow', // manual, *follow, error
            //referrer: 'no-referrer', // no-referrer, *client
            //body: JSON.stringify(data), // body data type must match "Content-Type" header
        }

        let reqOptions = Object.assign(defaultOptions, options)
        if (reqOptions.body) {
            if (!reqOptions.isFileUpload) { 
                if (reqOptions.headers['Content-Type'] == this.CONTENT_TYPE_URLENCODED) {
                    // name=kim&age=20
                    reqOptions.body = new URLSearchParams(reqOptions.body);
                } else {
                    // JSON 형식으로 
                    reqOptions.body = JSON.stringify(reqOptions.body)
                }
            }
        }
        return reqOptions;

    }// createOptions()


    /**
     * AJAX 요청을 수행한다.
     * @param {*} url  요청 URL 
     * @param {*} options  요청 옵션
     * @returns  Promise 객체
     */
    static ajax = (url, options) => {
        // debugger;
        let reqOptions = this.#createOptions(options);
        // async가 true이면 응답을 기다리지 않고 처리하고 , 아니면 응답을 기다린다.
        return  (reqOptions.async)? this.fetchTimeoutNoAwait(url, reqOptions) : this.#fetchTimeoutAwait(url, reqOptions);
    }


    // /**
    //      * 모든 비동기 HTTP 요청을 처리하기 위한 함수이다. 내부적으로 는 Fetch API를 사용한다. 
    //      * @param {String} url  the request URL 
    //      * @param {Object} options the request options
    //      * @returns 
    //      */
    // static ajax = (url, options) => {
    //     // 옵션을 만든다. 
    //     let reqOptions = this.createOptions(options);
    //     // 여기서 loding image 표시 
    //     // this.showLoadingImage();
    //     return new Promise((resolve, reject) => {
    //         fetch(url, reqOptions)
    //             // 첫번재 then()에서 json() 함수를 호출하고 return해야 한다. 
    //             // 두번째 then()에서 응답 body의 데이터를 받을 수 있다. 
    //             .then(res => {
    //                 if (res.ok) {  // res.ok로 반드시 체크
    //                     debugger;
    //                     // 여기서는 reqOptions을 넘기지 않고 options을 넘긴다. 
    //                     this.setDownloadFileName(res, options);
    //                     return this.responseOK(res);
    //                 } else {
    //                     this.responseNotOK(res);
    //                 }
    //             })
    //             // 응답이 성공인 경우 
    //             .then(res => {
    //                 resolve(res);
    //             })
    //             .catch(error => {
    //                 this.showError(error);
    //                 reject(error);
    //             })
    //             .finally(() => {
    //                 // 로딩 이미지 제거
    //                 // this.removeLoadingImage();
    //             });
    //     })
    // }// ajax()


    /**
     * 주어진 시간까지 응답이 없으면 요청을 취소한다. 
     * @param {*} url   요청 URL
     * @param {*} options  요청 옵션
     * @returns Promise 객체
     */
    static #request(url, options) {
        debugger;
        const controller = new AbortController();  // 요청 취소를 위한 컨트롤러 생성 
        options.signal = controller.signal;  // AbortController 객체의 signal 속성을 fetch()의 signal 옵션에 전달합니다.  
        const promise = fetch(url, options);
        if (options.signal) options.signal.addEventListener("abort", () => {
            console.log("aborted"); // 요청이 취소 되었음 
        });
        const timeout = setTimeout(() => controller.abort(), options.timeout); // 시간이 경과하면 요청을 취소한다. 
        return promise.finally(() => clearTimeout(timeout));  // promise 반환, timeout 제거 
    }//:

    /**
     * 기다리지 않고 fetch 처리한다. 
     */
    static #fetchTimeoutNoAwait(url, options) {
        
        return new Promise((resolve, reject) => {
            this.#request(url, options)
                .then(res => {
                    if (res.ok) {  // res.ok로 반드시 체크
                        debugger;
                        // 여기서는 reqOptions을 넘기지 않고 options을 넘긴다. 
                        this.#setDownloadFileName(res, options);
                        return this.responseOK(res);
                    } else {
                        this.#responseNotOK(res);
                    }
                })
                .then(res => {
                    resolve(res);
                })
                .catch(error => {
                    this.#showError(error);
                    reject(error);
                })
                .finally(() => {
                    // 로딩 이미지 제거
                    // this.removeLoadingImage();
                });
        });
    }//:
             
           

    /**
     * fetch()와  response 처리를 기다린다. 
     */
    static async #fetchTimeoutAwait(url, options) {
        // debugger; 
        console.log("option created");
        try {
            const response = await this.#request(url, options);
            console.log("response received");
            if (response.ok) {
                this.#setDownloadFileName(response, options);
                console.log("response ok");
                return await this.#responseOK(response);
            } else {
                this.#responseNotOK(response);
            }
        } catch (e) {
            this.#showError(e);
            throw e;
        }finally { 
             // 로딩 이미지 제거
            // this.removeLoadingImage();
        }
    }//:


    /**
     * 파일 다운로드 시 파일 이름을 설정한다.
     * @param {*} response 
     * @param {*} options 
     */
    static #setDownloadFileName(response, options) {
        const header = response.headers.get('Content-Disposition');
        if (header) {
            const parts = header.split(';');
            options.downloadFileName = parts[1].split('=')[1];
            console.log(response);
        }
    }

    static #showLoadingImage(isShow, target) {

    }
    static #removeLoadingImage(loginImageId) {
        const loginImage = document.getElementById(loginImageId);
        if (loginImage) {
            loginImage.remove();
        }
    }



    /**
     * 응답이 정상일 때 처리
     * @param {*} res 응답객체 
     */
    static #responseNotOK(res) {
        // debugger;
        const isJson = res.headers.get('content-type').includes(this.CONTENT_TYPE_JSON);
        if (isJson) {
            throw new AjaxError(res.status, res.json(), res.headers)
        } else {
            throw new AjaxError(res.status, null, res.headers)
        }

    }// responseNotOK()

    /**
     * 응답이 정상이 아닐 때 처리 
     * @param {*} res 응답 객체 
     */
    static #responseOK(res) {
        // debugger;
        let contentType = res.headers.get("Content-Type")
        //console.log(contentType) 
        if (!contentType) {
            return null // REST API 호출 시 값이 없으면 Content-Type이 없음 
        }
        if (contentType.includes(this.CONTENT_TYPE_JSON)) {
            return res.json()
        } else if (contentType.includes(this.CONTENT_TYPE_HTML)) {
            return res.text()
        } else if (contentType.includes(this.CONTENT_TYPE_TEXT)) {
            return res.text()
        } else if (contentType.includes("image/png")) {
            // 서버에서는 이 타입으로 사용하지 말고 application/octet-stream으로 사용할 것
            return res.blob()
        } else if (contentType.includes(this.CONTENT_TYPE_BINARY)) {
            return res.blob();
        } else {
            // 모르는 것은 무조건 blob으로 처리
            return res.blob();
        }
    }// responseOK()


    static #showError(error) {
        switch (error.status) {
            case this.HttpStatus.INTERNAL_SERVER_ERROR:  //  500:
                alert("서버 오류가 발생했습니다.");
                break;
            case this.HttpStatus.CONFLICT: //   409:  // 이 상태에 대해서는 서버에서 정의한 것이 없음
                alert("서버 오류가 발생했습니다.");
                break;
            case this.HttpStatus.BAD_REQUEST:   //   400:
                if (error.isJson) {
                    error.body.then(res => {
                        alert(res.code + "//" + res.message);
                    })
                }
                break;
            case this.HttpStatus.NOT_FOUND: //  404:
                alert("404: 잘못된 요청으로 해당된 리소스를 찾을 수 없습니다.")
                break;
            case this.HttpStatus.METHOD_NOT_ALLOWED: // 405:
                alert("호출 방식이 잘못되었습니다. POST/GET 방식을 확인하여 주세요.")
                break;
            case this.HttpStatus.NOT_ACCEPTABLE: //   406:
                alert("서버에서 요청을 허용하지 않습니다. 메서드 정의를 살펴주세요.")
                break;
            case this.HttpStatus.FORBIDDEN: //   403:
                    alert("권한 없음")
                    break;
            case this.HttpStatus.UNAUTHORIZED: //  401:
                alert("로그인 후에 이용하여 주세요")
                break;
        }
        if (error.name == "AbortError") {
            alert("요청이 취소되었습니다.");
        } else if (error instanceof TypeError) {
            alert("호출 형식이 잘못되었습니다:" + error.message);
        }

    }// showErorr()
}// Ajax



