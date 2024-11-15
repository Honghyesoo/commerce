package zerobase.com.ecommerce.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto{
    private String userId;
    private String password;
    private String token;
}
