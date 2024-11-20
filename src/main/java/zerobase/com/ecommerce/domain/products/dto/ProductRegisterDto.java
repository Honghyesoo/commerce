package zerobase.com.ecommerce.domain.products.dto;

import lombok.*;
import zerobase.com.ecommerce.domain.constant.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegisterDto {
    private String userId;
    private String product;
    private String productImg;
    private String productsContents;
    private String productsAmount;
    private Role role;

}
