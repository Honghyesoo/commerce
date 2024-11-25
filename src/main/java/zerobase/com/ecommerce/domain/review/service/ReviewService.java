package zerobase.com.ecommerce.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerobase.com.ecommerce.domain.review.dto.RegisterDto;
import zerobase.com.ecommerce.domain.review.dto.UpdateDto;

import java.util.List;

public interface ReviewService {
    RegisterDto register(RegisterDto registerDto);

    Page<RegisterDto> list(String product, Pageable pageable);

    UpdateDto update(UpdateDto updateDto);

    void delete(Long id, String userId);


}
