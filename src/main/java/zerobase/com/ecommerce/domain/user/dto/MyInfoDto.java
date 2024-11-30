package zerobase.com.ecommerce.domain.user.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.constant.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyInfoDto {
    private String userId;
    private String password;
    private String email;
    private String phone;
    private Role role;
    private Status userStatus;
    private LocalDateTime createAt;

}
