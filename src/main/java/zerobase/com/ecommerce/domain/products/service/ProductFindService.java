package zerobase.com.ecommerce.domain.products.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.repository.ProductRepository;
import zerobase.com.ecommerce.exception.product.ProductNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductFindService {
    private final ProductRepository productRepository;

    public ProductsEntity findByProductOrThrow(String products) {
        return productRepository.findByProduct(products)
                .orElseThrow(() ->
                        new ProductNotFoundException("해당 상점을 찾을 수 없습니다: " + products)
                );
    }
}
