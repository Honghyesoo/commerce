package zerobase.com.ecommerce.domain.products.entity;

import jakarta.persistence.*;
import lombok.*;
import zerobase.com.ecommerce.domain.baseEntity.BaseEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="product")
public class ProductsEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    private String product; //상품
    private String productImg; // 상품 이미지
    private String productsContents; // 상품소개
    private String productsAmount; //상품 금액

}
