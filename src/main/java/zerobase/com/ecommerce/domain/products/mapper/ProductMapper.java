package zerobase.com.ecommerce.domain.products.mapper;

import org.springframework.stereotype.Component;
import zerobase.com.ecommerce.domain.products.dto.ProductListDto;
import zerobase.com.ecommerce.domain.products.dto.ProductRegisterDto;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Component
public class ProductMapper {
    //상점등록 DTO -> Entity
    public ProductsEntity toProductsEntity(ProductRegisterDto productRegisterDto,
                                           UserEntity optionalUsers){
        return ProductsEntity.builder()
                .userId(optionalUsers)
                .product(productRegisterDto.getProduct())
                .productImg(productRegisterDto.getProductImg())
                .productsContents(productRegisterDto.getProductsContents())
                .productsAmount(productRegisterDto.getProductsAmount())
                .build();
    }

    //상점등록 Entity -> Dto
    public ProductRegisterDto toProductDto(ProductsEntity productsEntity){
        return ProductRegisterDto.builder()
                .userId(productsEntity.getUserId().toString())
                .product(productsEntity.getProduct())
                .productImg(productsEntity.getProductImg())
                .productsContents(productsEntity.getProductsContents())
                .productsAmount(productsEntity.getProductsAmount())
                .build();
    }

    //상품 목록
    public ProductListDto toProductListDto(ProductsEntity product) {
        ProductListDto dto = new ProductListDto();
        dto.setId(product.getId());
        dto.setProduct(product.getProduct());
        dto.setProductImg(product.getProductImg());
        dto.setProductsContents(product.getProductsContents());
        dto.setProductsAmount(product.getProductsAmount());
        return dto;
    }

}
