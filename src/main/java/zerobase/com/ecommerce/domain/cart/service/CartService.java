package zerobase.com.ecommerce.domain.cart.service;

import zerobase.com.ecommerce.domain.cart.dto.CartDto;

import java.util.List;

public interface CartService {

    void delete(Long id);

    CartDto register(CartDto cartDto);

    List<CartDto> list(CartDto cartDto);

}
