package zerobase.com.ecommerce.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.com.ecommerce.domain.review.dto.RegisterDto;
import zerobase.com.ecommerce.domain.review.dto.UpdateDto;
import zerobase.com.ecommerce.domain.review.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review/")
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰등록
    @PutMapping("register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok().body(reviewService.register(registerDto));
    }

    //리뷰목록
    @GetMapping("list")
    public  ResponseEntity<List<RegisterDto>> list(@RequestParam(name = "product") String product){
        return ResponseEntity.ok().body(reviewService.list(product));
    }

    //리뷰 수정
    @PutMapping("update")
    public ResponseEntity<UpdateDto> update(@RequestBody UpdateDto updateDto){
        return ResponseEntity.ok().body(reviewService.update(updateDto));
    }


}
