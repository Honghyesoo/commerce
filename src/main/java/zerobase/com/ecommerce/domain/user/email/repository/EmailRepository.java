package zerobase.com.ecommerce.domain.user.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.com.ecommerce.domain.user.email.entity.EmailEntity;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity,Long> {
    Optional<EmailEntity> findByEmailAuthKey(String emailAuthKey);
}
