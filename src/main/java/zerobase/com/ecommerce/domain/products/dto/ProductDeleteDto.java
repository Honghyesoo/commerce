package zerobase.com.ecommerce.domain.products.dto;

import lombok.Getter;
import lombok.Setter;
import zerobase.com.ecommerce.domain.constant.Role;

@Getter
@Setter
public class ProductDeleteDto {
    private Long id;
    private String userId;
    private Role role;
}
