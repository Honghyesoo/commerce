package zerobase.com.ecommerce.domain.review.service;

import zerobase.com.ecommerce.domain.review.dto.RegisterDto;
import zerobase.com.ecommerce.domain.review.dto.UpdateDto;

import java.util.List;

public interface ReviewService {
    RegisterDto register(RegisterDto registerDto);


    List<RegisterDto> list(String product);

    UpdateDto update(UpdateDto updateDto);
}
