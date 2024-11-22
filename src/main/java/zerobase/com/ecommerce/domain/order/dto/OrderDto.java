package zerobase.com.ecommerce.domain.order.dto;

import lombok.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;

import java.util.function.LongFunction;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String userId;
    private String product;
    private String email;
    private String phone;
    private String address;
    private int quantity;
    private int totalPrice;
    private int SalesCount;
}
