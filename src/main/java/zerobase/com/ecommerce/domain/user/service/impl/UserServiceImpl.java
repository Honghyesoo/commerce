package zerobase.com.ecommerce.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.com.ecommerce.components.MailComponents;
import zerobase.com.ecommerce.domain.constant.Status;
import zerobase.com.ecommerce.domain.token.TokenProvider;
import zerobase.com.ecommerce.domain.user.dto.*;
import zerobase.com.ecommerce.domain.user.email.entity.EmailEntity;
import zerobase.com.ecommerce.domain.user.email.repository.EmailRepository;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.mapper.UserMapper;
import zerobase.com.ecommerce.domain.user.repository.UserRepository;
import zerobase.com.ecommerce.domain.user.service.UserFindService;
import zerobase.com.ecommerce.domain.user.service.UserService;
import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static zerobase.com.ecommerce.exception.type.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final MailComponents mailComponents;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserFindService userFindService;

    //회원가입
    @Override
    public RegisterDto register(RegisterDto registerDto) {
        //사용자 ID 여부
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(registerDto.getUserId());

        optionalUserEntity.ifPresent(userId -> {
            String message = (userId.getUserStatus() == Status.STOP)
                    ? "회원탈퇴한 ID 입니다. 다른 ID를 사용해 주세요."
                    : "이미 존재하는 아이디 입니다.";

            log.info(message);
            throw new CommerceException(USER_NOT_FOUND);
        });

        // DTO -> Entity 변환 및 저장
        UserEntity userEntity = userMapper.toEntity(registerDto);
        UserEntity saveEntity = userRepository.save(userEntity);

        // EmailEntity 저장 (이메일 인증 관련)
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setEmail(saveEntity);
        emailEntity.setEmailAuthKey(UUID.randomUUID().toString());
        emailRepository.save(emailEntity);

        // 인증 메일 발송
        String email = registerDto.getEmail();
        String subject = "Commerce 이메일 인증";
        String text = mailComponents.getEmailAuthTemplate(saveEntity.getUserId(), emailEntity.getEmailAuthKey());

        boolean sendResult = mailComponents.sendMail(email, subject, text);
        if (!sendResult) {
            log.error("회원가입 인증 메일 발송 실패");
        }

        return userMapper.toDto(saveEntity);
    }

    @Transactional
    public boolean emailAuth(String userId, String emailAuthKey) {
        //회원이 없을시
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()){
            return false;
        }
        UserEntity users = optionalUser.get();

        //이메일 인증키가 없을시
        Optional<EmailEntity> optionalEmail = emailRepository.findByEmailAuthKey(emailAuthKey);
        if (optionalEmail.isEmpty()){
            return false;
        }
        EmailEntity emailUser = optionalEmail.get();

        if (emailUser.isEmailAuthYn()){
            return false;
        }
        emailUser.setEmailAuthYn(true);
        emailUser.setEmailAuthDt(LocalDateTime.now());
        emailRepository.save(emailUser);

        users.setUserStatus(Status.ING);
        userRepository.save(users);

        return true;

    }

    //로그인
    @Override
    public LoginDto login(LoginDto loginDto) {
        log.info("서비스 + {}" ,loginDto.getUserId());
        // 1. 사용자 ID 여부
        UserEntity userId = userFindService.findUserByIdOrThrow(loginDto.getUserId());

        log.info("서비스 유저아이디 + {}" ,userId.getUserId());

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(loginDto.getPassword(), userId.getPassword())) {
            throw new CommerceException(ErrorCode.PASSWORD_MISMATCH);
        }

        // 3. 사용자 상태 확인
        if (userId.getUserStatus() == Status.STOP) {
            throw new CommerceException(ErrorCode.USER_STATUS_STOP);
        }

        // 4. 이메일 인증 확인
        EmailEntity emailAuth = emailRepository.findByEmailEmail(userId.getEmail());
        if (!emailAuth.isEmailAuthYn()) {
            throw new CommerceException(ErrorCode.EMAIL_NOT_AUTHENTICATED);
        }

        // 5.로그인 성공 처리
        LoginDto responseDto = userMapper.toLoginDto(userId);
        String accessToken = tokenProvider.generateAccessToken(responseDto);
        responseDto.setToken(accessToken);
        return responseDto;
    }

    //비밀번호 재설정
    @Override
    public String rePassword(RePasswordDto rePasswordDto) {
        //사용자 ID 여부
        UserEntity userId = userFindService.findUserByIdOrThrow(rePasswordDto.getUserId());

        // 2.이메일 검증
        if (!rePasswordDto.getEmail().equals(userId.getEmail())){
            throw new CommerceException(ErrorCode.EMAIL_NOT_AUTHENTICATED);
        }

        // 3.비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rePasswordDto.getRePassword());

        // 4.비밀번호 저장
        userId.setPassword(encodedPassword);
        userRepository.save(userId);
        return "success";
    }
    //회원 탈퇴 및 정지
    @Override
    public DeleteDto userDelete(String userId) {
        //사용자 ID 여부
        UserEntity users = userFindService.findUserByIdOrThrow(userId);

        // 사용자에 해당하는 이메일 테이블 삭제
        Optional<EmailEntity> optionalEmail = emailRepository.findByEmail(users);

        // 사용자 상태 변경
        users.setUserStatus(Status.STOP);
        UserEntity saveUser = userRepository.save(users);

        return new DeleteDto(saveUser.getUserId(),saveUser.getRole());
    }

    //내정보 가져오기
    @Override
    public MyInfoDto myInfo(String userId) {
        //사용자 ID 여부
        UserEntity userEntity = userFindService.findUserByIdOrThrow(userId);

        // Entity를 DTO로 변환
        return MyInfoDto.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .userStatus(userEntity.getUserStatus())
                .createAt(userEntity.getCreateAt())
                .build();
    }
}
