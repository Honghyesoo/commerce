package zerobase.com.ecommerce.exception.global;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommerceException extends RuntimeException {
    private final HttpStatus status;

    public CommerceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
