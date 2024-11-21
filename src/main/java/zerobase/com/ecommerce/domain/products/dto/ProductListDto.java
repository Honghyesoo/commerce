package zerobase.com.ecommerce.domain.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDto {
    private Long id;
    private String product;
    private String productImg;
    private String productsContents;
    private int price;
}
