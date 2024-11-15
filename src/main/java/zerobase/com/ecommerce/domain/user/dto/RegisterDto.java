package zerobase.com.ecommerce.domain.user.dto;

import lombok.*;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.constant.Status;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private Status userStatus;

}
