package zerobase.com.ecommerce.exception.global;

import lombok.*;
import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.type.ErrorCode;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.status = errorCode.getStatus();
        this.message = message;
    }
}

