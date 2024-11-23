package zerobase.com.ecommerce.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.review.entity.ReviewEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    boolean existsByUserIdAndProduct(UserEntity user, ProductsEntity product);


    List<ReviewEntity> findAllByProduct_ProductOrderByCreateAtDesc(String product);
}
