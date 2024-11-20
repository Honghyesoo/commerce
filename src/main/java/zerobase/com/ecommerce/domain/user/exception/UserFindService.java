package zerobase.com.ecommerce.domain.user.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.exception.CommerceException;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserFindService {
    private final UserRepository userRepository;

    public UserEntity findUserByIdOrThrow(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자 ID를 찾을 수 없습니다: " + userId));
    }


    public void checkUserRole(String userId, Role requiredRole) {
        UserEntity user = findUserByIdOrThrow(userId);
        if (user.getRole() == Role.USER && user.getRole() != requiredRole) {
            throw new InsufficientPermissionException("일반 사용자: 권한이 없습니다.");
        }
    }
}
