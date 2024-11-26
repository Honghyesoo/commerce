package zerobase.com.ecommerce.domain.products.dto;

import lombok.*;
import zerobase.com.ecommerce.domain.constant.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {
    private Long id;
    private String userId;
    private String product;
    private String productImg;
    private String productsContents;
    private int price;
    private Role role;
}
