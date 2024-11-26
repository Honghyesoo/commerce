package zerobase.com.ecommerce.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.repository.UserRepository;
import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;
import zerobase.com.ecommerce.exception.user.UserLoginException;
import zerobase.com.ecommerce.exception.user.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserFindService {
    private final UserRepository userRepository;

    public UserEntity findUserByIdOrThrow(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public void UserLoginException(String userId) {
        userRepository.findByUserId(userId)
                .orElseThrow(UserLoginException::new);
    }
}
