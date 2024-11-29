package zerobase.com.ecommerce.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import zerobase.com.ecommerce.domain.baseEntity.BaseEntity;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.constant.Status;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디 입력 필수")
    private String userId;

    @NotBlank(message = "비밀번호 입력 필수")
    @Size(min = 4, max=20, message = "4 - 20사이의 길이로 입력해 주세요.")
    private String password;

    @NotBlank(message = "이메일 입력 필수")
    private String email;

    @NotBlank(message = "전화번호 입력 필수")
    private String phone;

    @NotBlank(message = "주소 입력 필수")
    private String address;

    @Enumerated(EnumType.STRING) // 또는 EnumType.ORDINAL
    private Role role; //사용자타입
    private Status userStatus; // 회원탈퇴 및 회원정지

}
