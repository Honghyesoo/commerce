package zerobase.com.ecommerce.exception.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static zerobase.com.ecommerce.exception.type.ErrorCode.INTERNAL_SERVER_ERROR;
import static zerobase.com.ecommerce.exception.type.ErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    // CommerceException 처리
    @ExceptionHandler(CommerceException.class)
    public ResponseEntity<ErrorResponse> handleCommerceException(CommerceException e) {
        log.error("CommerceException occurred: {}", e.getMessage(), e);
        // 예외가 던져진 ErrorCode에 맞는 HttpStatus와 메시지를 반환
        return new ResponseEntity<>(
                new ErrorResponse(e.getErrorCode(), e.getErrorMessage()),
                e.getErrorCode().getStatus()
        );
    }

    // DataIntegrityViolationException 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(
                new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getDescription()),
                INVALID_REQUEST.getStatus()
        );
    }

    // 기타 일반 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(
                new ErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getDescription()),
                INTERNAL_SERVER_ERROR.getStatus()
        );
    }


}