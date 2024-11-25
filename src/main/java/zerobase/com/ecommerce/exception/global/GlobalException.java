package zerobase.com.ecommerce.exception.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import zerobase.com.ecommerce.exception.review.DuplicateReviewException;
import zerobase.com.ecommerce.exception.review.ProductNotPurchasedException;
import zerobase.com.ecommerce.exception.user.UserNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalException {
    // AccessDeniedException 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException e, WebRequest request) {
        log.error("AccessDeniedException: ", e);
        ErrorResponse response = ErrorResponse.of(
                HttpStatus.FORBIDDEN,
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(CommerceException.class)
    public ResponseEntity<ErrorResponse> handleCommerceException(
            CommerceException e, WebRequest request) {
        if (e instanceof ProductNotPurchasedException) {
            log.error("ProductNotPurchasedException: ", e);
        } else if (e instanceof DuplicateReviewException) {
            log.error("DuplicateReviewException: ", e);
        } else {
            log.error("CommerceException: ", e);
        }
        ErrorResponse response = ErrorResponse.of(
                e.getStatus(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, e.getStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException e, WebRequest request) {
        log.error("IllegalArgumentException: ", e);
        ErrorResponse response = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException e, WebRequest request) {
        log.error("UserNotFoundException: ", e);
        ErrorResponse response = ErrorResponse.of(
                e.getStatus(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
            Exception e, WebRequest request) {
        log.error("Unexpected error occurred: ", e);
        ErrorResponse response = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "내부 서버 오류가 발생했습니다.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}