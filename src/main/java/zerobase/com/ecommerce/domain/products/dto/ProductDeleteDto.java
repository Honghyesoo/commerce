package zerobase.com.ecommerce.domain.products.dto;

import lombok.Getter;
import lombok.Setter;
import zerobase.com.ecommerce.domain.constant.Role;

@Getter
@Setter
public class ProductDeleteDto {
    private String userId;
    private String product;
    private Role role;
}
