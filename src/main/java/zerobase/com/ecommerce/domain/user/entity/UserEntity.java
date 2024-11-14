package zerobase.com.ecommerce.domain.user.entity;

import jakarta.persistence.*;
import zerobase.com.ecommerce.domain.baseEntity.BaseEntity;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.constant.Status;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String user_id;
    private String password;
    private String phone;
    private String address;
    private Role role; //사용자타입
    private Status userStatus; // 회원탈퇴 및 회원정지

}
