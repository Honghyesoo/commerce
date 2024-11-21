package zerobase.com.ecommerce.exception.user;

import zerobase.com.ecommerce.exception.global.CommerceException;

public class UserLoginException extends CommerceException {
    public UserLoginException(String message){
        super(message);
    }
}
