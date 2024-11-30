package zerobase.com.ecommerce.domain.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zerobase.com.ecommerce.domain.cart.entity.CartEntity;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity,Long> {
   Optional<ProductsEntity> findByProduct(String product);

   // 상품명 또는 상품소개에서 키워드 검색
   @Query("SELECT p FROM ProductsEntity p " +
           "WHERE p.product LIKE %:keyword% " +
           "OR p.productsContents LIKE %:keyword%")
   List<ProductsEntity> searchByKeyword(@Param("keyword") String keyword);

}

