package zerobase.com.ecommerce.domain.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.constant.Status;
import zerobase.com.ecommerce.domain.user.dto.LoginDto;
import zerobase.com.ecommerce.domain.user.dto.MyInfoDto;
import zerobase.com.ecommerce.domain.user.dto.RegisterDto;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    // 회원가입 DTO -> Entity
    public UserEntity toEntity(RegisterDto registerDto){
        String hashPassword = passwordEncoder.encode(registerDto.getPassword());

        return UserEntity.builder()
                .userId(registerDto.getUserId())
                .password(hashPassword)
                .email(registerDto.getEmail())
                .phone(registerDto.getPhone())
                .address(registerDto.getAddress())
                .role(Role.USER)
                .userStatus(Status.REQ)//초기상태 (이메일 인증 후 ING변경)
                .build();

    }
    // 회원가입 Entity -> Dto
    public RegisterDto toDto(UserEntity entity){
        return RegisterDto.builder()
                .userId(entity.getUserId())
                .password(entity.getPassword())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .role(Role.USER)
                .userStatus(entity.getUserStatus())
                .build();
    }

    //로그인 Entity -> Dto
    public LoginDto toLoginDto(UserEntity entity){
        return LoginDto.builder()
                .userId(entity.getUserId())
                .role(entity.getRole())
                .build();
    }

    // 내정보수정 Entity ->Dto
    public MyInfoDto myInfoDto(UserEntity userEntity){
        return MyInfoDto.builder()
                .userId(userEntity.getUserId())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .userStatus(userEntity.getUserStatus())
                .createAt(userEntity.getCreateAt())
                .build();
    }
}
