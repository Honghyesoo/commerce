package zerobase.com.ecommerce.domain.user.service;

import zerobase.com.ecommerce.domain.user.dto.*;

public interface UserService {
    RegisterDto register(RegisterDto registerDto);

    boolean emailAuth(String userId, String emailAuthKey);

    LoginDto login(LoginDto loginDto);

    DeleteDto userDelete(String userId);

    MyInfoDto myInfo(String userId);

    MyInfoDto myPageUpdate(MyInfoDto myInfoDto);
}
