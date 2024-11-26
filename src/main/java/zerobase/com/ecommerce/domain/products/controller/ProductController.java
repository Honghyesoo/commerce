package zerobase.com.ecommerce.domain.products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.com.ecommerce.domain.constant.SortOption;
import zerobase.com.ecommerce.domain.products.dto.ProductListDto;
import zerobase.com.ecommerce.domain.products.dto.ProductRegisterDto;
import zerobase.com.ecommerce.domain.products.dto.ProductUpdateDto;
import zerobase.com.ecommerce.domain.products.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/")
public class ProductController {
    private final ProductService productService;

    //상품 등록
    @PostMapping("register")
    public ResponseEntity<ProductRegisterDto> register(@RequestBody ProductRegisterDto productRegisterDto) {
        return ResponseEntity.ok().body(productService.register(productRegisterDto));
    }

    //상품 삭제
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@RequestParam(name = "id") Long id,
                                         @RequestParam(name = "userId") String userId) {
        productService.delete(userId, id); // 예외가 발생하면 자동으로 상위 예외 핸들러로 전달
        return ResponseEntity.ok("Success");
    }

    //상품 수정
    @PutMapping("update")
    public ResponseEntity<ProductUpdateDto> update(@RequestBody
                                                   ProductUpdateDto updateDto) {
        return ResponseEntity.ok().body(productService.update(updateDto));
    }

    // 상점 목록
    @GetMapping("list")
    public ResponseEntity<List<ProductListDto>> list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sort", defaultValue = "SALES") SortOption sortOption
    ) {
        return ResponseEntity.ok(productService.list(keyword, sortOption));
    }

    // 상점 상세보기
    @GetMapping("detail")
    public ResponseEntity<ProductListDto> detail(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok().body(productService.detail(id));
    }


}
