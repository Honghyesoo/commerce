package zerobase.com.ecommerce.exception.review;

import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.global.CommerceException;

public class DuplicateReviewException extends CommerceException {
    public DuplicateReviewException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}