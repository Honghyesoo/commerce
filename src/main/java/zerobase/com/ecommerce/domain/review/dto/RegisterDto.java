package zerobase.com.ecommerce.domain.review.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {
    private String userId;
    private String product;
    private String contents;
}
