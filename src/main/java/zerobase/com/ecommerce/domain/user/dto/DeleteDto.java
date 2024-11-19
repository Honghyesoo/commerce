package zerobase.com.ecommerce.domain.user.dto;

import lombok.*;
import zerobase.com.ecommerce.domain.constant.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDto {
    private String userId;
    private Role role;
}
