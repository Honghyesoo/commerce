package zerobase.com.ecommerce.domain.user.mapper;

import org.springframework.stereotype.Component;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.constant.Status;
import zerobase.com.ecommerce.domain.user.dto.RegisterDto;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Component
public class UserMapper {
    // 회원가입 DTO -> Entity
    public UserEntity toEntity(RegisterDto registerDto){
        return UserEntity.builder()
                .userId(registerDto.getUserId())
                .password(registerDto.getPassword())
                .email(registerDto.getEmail())
                .phone(registerDto.getPhone())
                .address(registerDto.getAddress())
                .role(Role.USER)
                .userStatus(Status.STOP)//초기상태 (이메일 인증 후 ING변경)
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
}
