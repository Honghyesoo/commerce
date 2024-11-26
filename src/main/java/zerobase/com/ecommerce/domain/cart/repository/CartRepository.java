package zerobase.com.ecommerce.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.com.ecommerce.domain.cart.entity.CartEntity;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    List<CartEntity> findByUserId_UserId(String userId);

    Optional<CartEntity> findByUserId_UserIdAndProduct_Id(String userId, Long id);
}
