package zerobase.com.ecommerce.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerobase.com.ecommerce.domain.review.dto.OrderRegisterDto;
import zerobase.com.ecommerce.domain.review.dto.OrderUpdateDto;

public interface ReviewService {
    OrderRegisterDto register(OrderRegisterDto orderRegisterDto);

    Page<OrderRegisterDto> list(String product, Pageable pageable);

    OrderUpdateDto update(OrderUpdateDto orderUpdateDto);

    void delete(Long id, String userId);


}
