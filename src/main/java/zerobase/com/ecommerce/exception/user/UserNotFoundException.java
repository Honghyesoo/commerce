package zerobase.com.ecommerce.exception.user;

import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.global.CommerceException;

public class UserNotFoundException extends CommerceException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
