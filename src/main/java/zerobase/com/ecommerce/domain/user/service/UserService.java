package zerobase.com.ecommerce.domain.user.service;

import zerobase.com.ecommerce.domain.user.dto.RegisterDto;

public interface UserService {
    //회원가입
    RegisterDto register(RegisterDto registerDto);

    //이메일 인증
    boolean emailAuth(String userId, String emailAuthKey);
}
