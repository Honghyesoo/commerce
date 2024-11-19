package zerobase.com.ecommerce.domain.user.service;

import zerobase.com.ecommerce.domain.user.dto.*;

public interface UserService {
    RegisterDto register(RegisterDto registerDto);

    boolean emailAuth(String userId, String emailAuthKey);

    LoginDto login(LoginDto loginDto);

    String rePassword(RePasswordDto rePasswordDto);

    DeleteDto userDelete(String userId);

    MyInfoDto myInfo(String userId);
}
