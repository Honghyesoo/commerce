package zerobase.com.ecommerce.domain.user.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.com.ecommerce.domain.user.dto.*;
import zerobase.com.ecommerce.domain.user.service.UserService;

import java.util.Map;

@Slf4j
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
    //로그인
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        LoginDto responseDto = userService.login(loginDto);
        return ResponseEntity.ok(responseDto);
    }
    //회원 탈퇴 및 정지
    @DeleteMapping("delete")
    public ResponseEntity<DeleteDto> delete(@RequestParam(name = "userId")
                                                String userId){
        return ResponseEntity.ok().body(userService.userDelete(userId));
    }

    // 내 정보 가져오기
    @GetMapping("myinfo")
    public ResponseEntity<MyInfoDto> myInfo(@RequestParam("userId") String userId){
        return ResponseEntity.ok().body(userService.myInfo(userId));
    }

    //내 정보 수정
    @PatchMapping("mypageupdate")
    public ResponseEntity<MyInfoDto> myPageUpdate(@RequestBody MyInfoDto myInfoDto){
        return ResponseEntity.ok().body(userService.myPageUpdate(myInfoDto));
    }

}
