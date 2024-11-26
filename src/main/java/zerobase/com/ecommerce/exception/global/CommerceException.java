package zerobase.com.ecommerce.exception.global;


import lombok.*;
import org.springframework.http.HttpStatus;
import zerobase.com.ecommerce.exception.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommerceException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;  // HttpStatus 필드 추가

    // ErrorCode 기반 생성자
    public CommerceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
        this.httpStatus = errorCode.getStatus();  // errorCode에서 HttpStatus 설정
    }

    // ErrorCode와 메시지 기반 생성자 추가
    public CommerceException(ErrorCode errorCode, String customMessage) {
        this.errorCode = errorCode;
        this.errorMessage = customMessage;  // customMessage로 메시지를 덮어씀
        this.httpStatus = errorCode.getStatus();
    }

    // HttpStatus와 메시지 기반 생성자
    public CommerceException(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}