package zerobase.com.ecommerce.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.com.ecommerce.domain.review.dto.OrderRegisterDto;
import zerobase.com.ecommerce.domain.review.dto.OrderUpdateDto;
import zerobase.com.ecommerce.domain.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review/")
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰등록
    @PutMapping("register")
    public ResponseEntity<OrderRegisterDto> register(@RequestBody OrderRegisterDto orderRegisterDto){
        return ResponseEntity.ok().body(reviewService.register(orderRegisterDto));
    }

    //리뷰목록
    @GetMapping("list")
    public  ResponseEntity<Page<OrderRegisterDto>> list(
            @RequestParam(name = "product") String product,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return  ResponseEntity.ok().body(reviewService.list(product, pageable));
    }

    //리뷰 수정
    @PutMapping("update")
    public ResponseEntity<OrderUpdateDto> update(@RequestBody OrderUpdateDto orderUpdateDto){
        return ResponseEntity.ok().body(reviewService.update(orderUpdateDto));
    }

    //리뷰삭제
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@RequestParam(name = "id") Long id,
                                         @RequestParam(name = "userId") String userId) {
        reviewService.delete(id, userId);

        // 삭제 후 성공 메시지 반환
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }


}
