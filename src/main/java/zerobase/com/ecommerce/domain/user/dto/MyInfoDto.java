package zerobase.com.ecommerce.domain.user.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyInfoDto {
    private String userId;
    private String email;
    private String phone;
    private LocalDateTime createAt;
}
