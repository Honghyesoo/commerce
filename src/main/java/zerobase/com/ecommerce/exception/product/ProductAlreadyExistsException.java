package zerobase.com.ecommerce.exception.product;

import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.global.CommerceException;

public class ProductAlreadyExistsException extends CommerceException {
    public ProductAlreadyExistsException(String message) {
        super(message,HttpStatus.CONFLICT);
    }
}
