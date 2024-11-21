package zerobase.com.ecommerce.domain.cart.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private String userId;
    private Long product;  // product를 String에서 Long으로 수정
    private int quantity;
    private int price;
    private int totalPrice;
}
