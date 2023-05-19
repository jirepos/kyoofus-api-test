
// 이 스크립트가 한 번 이상 로드될 때 global space에 두 번 선언되면 오류 발생 
// 즉시 실행함수를 scope 변경 
(function (register) {

    // 여기서 클래스 선언 
    class ListPortlet {

        constructor() {
        }


        /** 이 메서드에서 화면을 그린다. */
        render() {
            let html = `<div>
                <h1>List Portlet</h1>
                <ul>
                    <li>Item 1</li>
                    <li>Item 2</li>
                    <li>Item 3</li>
                </ul>
            </div>`;
            let listEle = document.createElement('div');
            listEle.innerHTML = html;
            document.body.appendChild(listEle);
        }//:

    }///~

    // 여기서 등록 
    // register this 
    if (!register['ListPortlet'] ) {
        register['ListPortlet'] = ListPortlet;
    }

})(PortletRegister);

