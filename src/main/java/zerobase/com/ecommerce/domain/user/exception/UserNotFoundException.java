package zerobase.com.ecommerce.domain.user.exception;

import zerobase.com.ecommerce.domain.exception.CommerceException;

public class UserNotFoundException extends CommerceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
