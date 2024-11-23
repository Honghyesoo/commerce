package zerobase.com.ecommerce.exception.review;

import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.global.CommerceException;

public class ProductNotPurchasedException extends CommerceException {
    public ProductNotPurchasedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
