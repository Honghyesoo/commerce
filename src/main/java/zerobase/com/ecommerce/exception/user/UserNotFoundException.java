package zerobase.com.ecommerce.exception.user;

import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.global.CommerceException;

public class UserNotFoundException extends CommerceException {
    //제품이 이미 존재할 때 발생하는 예외
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
