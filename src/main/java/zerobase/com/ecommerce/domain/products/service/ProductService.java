package zerobase.com.ecommerce.domain.products.service;

import zerobase.com.ecommerce.domain.constant.SortOption;
import zerobase.com.ecommerce.domain.products.dto.ProductListDto;
import zerobase.com.ecommerce.domain.products.dto.ProductRegisterDto;
import zerobase.com.ecommerce.domain.products.dto.ProductUpdateDto;

import java.util.List;

public interface ProductService {
    ProductRegisterDto register(ProductRegisterDto productRegisterDto);

    boolean delete(String userId, Long id);

    ProductUpdateDto update(ProductUpdateDto updateDto);

    List<ProductListDto> list(String keyword, SortOption sortOption);

    ProductListDto detail(Long id);
}
