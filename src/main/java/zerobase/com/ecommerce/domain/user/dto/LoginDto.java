package zerobase.com.ecommerce.domain.user.dto;

import lombok.*;
import zerobase.com.ecommerce.domain.constant.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto{
    private String userId;
    private String password;
    private String token;
    private Role role;
}
