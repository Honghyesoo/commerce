package zerobase.com.ecommerce.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.com.ecommerce.domain.order.entity.OrderEntity;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    boolean existsByUserIdAndProduct(UserEntity user, ProductsEntity product);
}
