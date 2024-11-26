package zerobase.com.ecommerce.exception.user;

import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;

public class UserLoginException extends CommerceException {
    public UserLoginException(){
        super(ErrorCode.USER_LOGIN_REQUIRED,"로그인 후 이용해 주세요.");
    }
}
