package zerobase.com.ecommerce.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.com.ecommerce.components.MailComponents;
import zerobase.com.ecommerce.domain.constant.Status;
import zerobase.com.ecommerce.domain.user.dto.RegisterDto;
import zerobase.com.ecommerce.domain.user.email.entity.EmailEntity;
import zerobase.com.ecommerce.domain.user.email.repository.EmailRepository;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.mapper.UserMapper;
import zerobase.com.ecommerce.domain.user.repository.UserRepository;
import zerobase.com.ecommerce.domain.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final MailComponents mailComponents;
    private final UserMapper userMapper;

    @Override
    public RegisterDto register(RegisterDto registerDto) {
        //중복체크
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(registerDto.getUserId());
        if (optionalUserEntity.isPresent()){
            UserEntity existingUser = optionalUserEntity.get();
            if (existingUser.getUserStatus() == Status.STOP){
                log.info("회원탈퇴한 ID 입니다. 다른 ID를 사용해 주세요. ");
                throw new RuntimeException("회원탈퇴한 ID 입니다. 다른 ID를 사용해 주세요.");
            }else {
                log.info("이미 존재하는 아이디 입니다.");
                throw new RuntimeException("이미 존재하는 아이디 입니다.");
            }
        }
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
}
