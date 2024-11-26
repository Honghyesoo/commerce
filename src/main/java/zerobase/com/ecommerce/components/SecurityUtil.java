package zerobase.com.ecommerce.components;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    //  지금 이 코드는 일반적인 기본 클래스 코드인데 이걸 아래 쪽에 코드인 Recode 형식으로 불변 객체로 변경
//  recode 형식은 자동으로 getter 메소드를 사용되어짐
//    @Getter
//    @RequiredArgsConstructor
//    public static class UserInfo {
//        private final String username;
//        private final String role;
//    }
    // 현재 로그인한 사용자의 정보를 담는 정적 내부 클래스

    public record UserInfo(String username, String role) {}

    public static UserInfo getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("현재 인증된 사용자가 없습니다.");
        }

        Object principal = authentication.getPrincipal();
        String username = principal instanceof UserDetails ?
                ((UserDetails) principal).getUsername() : (String) principal;

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        return new UserInfo(username, role);
    }

}