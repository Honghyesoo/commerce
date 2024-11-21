package zerobase.com.ecommerce.exception.user;

import zerobase.com.ecommerce.exception.global.CommerceException;

public class InsufficientPermissionException extends CommerceException {
    public InsufficientPermissionException(String message) {
        super(message);
    }

}
