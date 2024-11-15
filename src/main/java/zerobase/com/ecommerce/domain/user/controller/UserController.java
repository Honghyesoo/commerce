package zerobase.com.ecommerce.domain.user.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.com.ecommerce.domain.user.dto.RegisterDto;
import zerobase.com.ecommerce.domain.user.service.UserService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("register")
    public ResponseEntity<String> register(
            @RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    //이메일 인증
    @GetMapping("email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam("userId") String userId,
                                       @RequestParam("key") String emailAuthKey) {
        boolean result = userService.emailAuth(userId, emailAuthKey);

        if (result){
            return ResponseEntity.ok().body(Map.of(
                    "message", "이메일 인증이 완료 되었습니다.",
                    "success",true
            ));
        }
        return ResponseEntity.ok().body(Map.of(
                "message", "이메일 인증에 실패 했습니다.",
                "success",false
        ));
    }
}
