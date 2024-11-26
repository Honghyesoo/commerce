package zerobase.com.ecommerce.domain.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.repository.ProductRepository;
import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;

@Service
@RequiredArgsConstructor
public class ProductFindService {
    private final ProductRepository productRepository;

    public ProductsEntity findByProductOrThrow(String products) {
        return productRepository.findByProduct(products)
                .orElseThrow(() ->
                        new CommerceException(ErrorCode.PRODUCT_NOT_FOUND)
                );
    }
}
