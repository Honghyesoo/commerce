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
    private int quantity;
    private String product;
    private String address;
    private int totalPrice;
    private int SalesCount;
}
