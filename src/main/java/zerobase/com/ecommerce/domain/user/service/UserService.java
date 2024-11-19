package zerobase.com.ecommerce.domain.user.service;

import zerobase.com.ecommerce.domain.user.dto.LoginDto;
import zerobase.com.ecommerce.domain.user.dto.RePasswordDto;
import zerobase.com.ecommerce.domain.user.dto.RegisterDto;

public interface UserService {
    RegisterDto register(RegisterDto registerDto);
    boolean emailAuth(String userId, String emailAuthKey);
    LoginDto login(LoginDto loginDto);
    String rePassword(RePasswordDto rePasswordDto);

}
