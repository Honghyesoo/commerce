package zerobase.com.ecommerce.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    //회원가입
    Optional<UserEntity> findByUserId(String userId);
}
