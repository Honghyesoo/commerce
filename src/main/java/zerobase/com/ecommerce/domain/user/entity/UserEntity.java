package zerobase.com.ecommerce.domain.user.entity;

import jakarta.persistence.*;
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

    private String userId;
    private String password;
    private String email;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING) // 또는 EnumType.ORDINAL
    private Role role; //사용자타입
    private Status userStatus; // 회원탈퇴 및 회원정지

}
