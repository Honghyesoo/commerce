package zerobase.com.ecommerce.domain.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import zerobase.com.ecommerce.domain.baseEntity.BaseEntity;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="cart")
public class CartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private ProductsEntity product;

    private int price;;
    private int quantity; // 수량
    private int totalPrice; // 수량 * 가격

}
