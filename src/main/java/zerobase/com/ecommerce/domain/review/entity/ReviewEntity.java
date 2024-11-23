package zerobase.com.ecommerce.domain.review.entity;

import jakarta.persistence.*;
import lombok.*;
import zerobase.com.ecommerce.domain.baseEntity.BaseEntity;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private ProductsEntity product;

    private String contents; // 리뷰 내용

}
