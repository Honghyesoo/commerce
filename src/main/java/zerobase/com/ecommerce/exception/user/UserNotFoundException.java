package zerobase.com.ecommerce.exception.user;

import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;

public class UserNotFoundException extends CommerceException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND,"사용자 정보를 찾을 수 없습니다.");
    }
}