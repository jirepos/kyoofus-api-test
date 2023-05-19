package basic.java8.enumclass;

import org.junit.jupiter.api.Test;

import com.jirepo.core.web.exception.HttpStatusEnum;

/**
 * Inteface를 구현한 Enum 테스트 케이스이다. 
 * 코드는 enum으로 작성하는 경우 어떤 메서드에 파라미터로 enum을 넘길 때 
 * 각각 구현한 enum을 타입캐스팅하지 않고 사용하는 것을 테스트한다. 
 */
public class EnumAndInterfaceTest {

    /**
     * 에러에 대한 정보를 담고 있는 인터페이스 
     */
    public static interface ErrorCode {
        HttpStatusEnum getStatus();
        String getCode();
        String getMessage();
    }
    

    // 이제 인터페이스를 구현한 Enum을 생성할 수 있다.
    /**
     * 인터페이스를 구현한 enum을 생성한다. 
     */
    public static enum ErrorCodeImpl implements ErrorCode {
        USER_NOT_FOUND(HttpStatusEnum.BAD_REQUEST, "C100", "로그인 정보가 부정확합니다."),
        PASSWORD_NOT_MATCH(HttpStatusEnum.BAD_REQUEST, "C101", "비밀번호가 부정확합니다.");

        private final HttpStatusEnum status;
        private final String code;
        private final String message;

        ErrorCodeImpl(HttpStatusEnum status, String code, String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }

        @Override
        public HttpStatusEnum getStatus() {
            return status;
        }
        @Override
        public String getCode() {
            return code;
        }
        @Override
        public String getMessage() {
            return message;
        }
    }///~

    /**
     * 인터페이스를 구현한 enum을 테스트한다  
     */
    @Test
    public void testEnum() {
        ErrorCodeImpl errorCode = ErrorCodeImpl.USER_NOT_FOUND;
        System.out.println(errorCode.getStatus());
        System.out.println(errorCode.getStatus().getStatusCode());
        System.out.println(errorCode.getCode());
        System.out.println(errorCode.getMessage());
    }//:


    /** 인터페이스를 구현한 enum을 파라미터로 넘기는 테스트 */
    @Test
    public void testEnum2() {
        ErrorCodeImpl errorCode = ErrorCodeImpl.USER_NOT_FOUND;
        printErrorCode(errorCode);
    }


    /** 
     * interface를 파라미터로 받는 메서드를 테스트한다. 
     */
    private void printErrorCode(ErrorCode errorCode) {
        System.out.println("status: " + errorCode.getStatus());
        System.out.println(errorCode.getStatus().getStatusCode());
        System.out.println("code: " + errorCode.getCode());
        System.out.println("message: " + errorCode.getMessage());
    }


}
