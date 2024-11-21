package zerobase.com.ecommerce.domain.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.com.ecommerce.domain.cart.dto.CartDto;
import zerobase.com.ecommerce.domain.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart/")
public class CartController {
    private final CartService cartService;

    //장바구니 등록
    @PostMapping("register")
    public ResponseEntity<CartDto> register(@RequestBody @Valid CartDto cartDto) {
        return ResponseEntity.ok().body(cartService.register(cartDto));
    }

    //장바구니 삭제
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@RequestParam(name = "id") Long id) {
        cartService.delete(id); // 예외 발생 시 공통 핸들러에서 처리

        return ResponseEntity.ok("해당 상품이 삭제되었습니다.");
    }

    //장바구니 목록
    @GetMapping("list")
    public ResponseEntity<List<CartDto>> list(@RequestParam(name = "userId") String userId) {
        CartDto cartDto = new CartDto();
        cartDto.setUserId(userId);

        // 장바구니 목록 조회 서비스 호출
        List<CartDto> cartList = cartService.list(cartDto);

        return ResponseEntity.ok().body(cartList);
    }
}
