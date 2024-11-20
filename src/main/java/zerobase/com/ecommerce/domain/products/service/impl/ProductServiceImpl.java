package zerobase.com.ecommerce.domain.products.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.products.dto.ProductListDto;
import zerobase.com.ecommerce.domain.products.dto.ProductUpdateDto;
import zerobase.com.ecommerce.domain.products.exception.ProductFindService;
import zerobase.com.ecommerce.domain.user.exception.UserFindService;
import zerobase.com.ecommerce.domain.products.dto.ProductRegisterDto;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.mapper.ProductMapper;
import zerobase.com.ecommerce.domain.products.repository.ProductRepository;
import zerobase.com.ecommerce.domain.products.service.ProductService;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserFindService userFindService;
    private final ProductFindService productFindService;

    //상품 등록
    @Override
    public ProductRegisterDto register(ProductRegisterDto productRegisterDto) {
        // 사용자 ID가 있는지
        UserEntity user = userFindService.findUserByIdOrThrow(productRegisterDto.getUserId());
        // 사용자 권한 확인
        userFindService.checkUserRole(productRegisterDto.getUserId(), Role.SELLER);

        Optional<ProductsEntity> optionalProducts =
                productRepository.findByProduct(productRegisterDto.getProduct());

        if (optionalProducts.isPresent()){
            log.info("같은 상품이 존재합니다 ");
            throw new RuntimeException("같은 상품이 존재합니다.");
        }

        // DTO -> Entity 변환 및 저장
        ProductsEntity productsEntity = productMapper.toProductsEntity(productRegisterDto,user);
        ProductsEntity saveEntity = productRepository.save(productsEntity);
        return productMapper.toProductDto(saveEntity);
    }

    //상품 삭제
    @Override
    public boolean delete(String userId, String product) {
        // 사용자 ID가 있는지
        UserEntity user = userFindService.findUserByIdOrThrow(userId);
        // 사용자 권한 확인
        userFindService.checkUserRole(userId, Role.SELLER);

        //상품 존재 여부 확인
        ProductsEntity products = productFindService
                .findByProductOrThrow(product);

        //상품 삭제
        productRepository.delete(products);
        return true;
    }

    //상품 수정
    @Override
    public ProductUpdateDto update(ProductUpdateDto updateDto) {
        // 사용자 권한 확인
        userFindService.checkUserRole(updateDto.getUserId(), Role.SELLER);

        ProductsEntity products =
                productRepository.findByUserId_UserId(updateDto.getUserId());

        //상품 정보 업데이트
        products.setProduct(updateDto.getProduct());
        products.setProductImg(updateDto.getProductImg());
        products.setProductsContents(updateDto.getProductsContents());
        products.setProductsAmount(updateDto.getProductsAmount());

        //변경된 상품 정보 저장
        ProductsEntity updatedProduct = productRepository.save(products);

        //업데이트된 정보 DTO 변환하여 반환
        return ProductUpdateDto.builder()
                .userId(updatedProduct.getUserId().getUserId())
                .product(updatedProduct.getProduct())
                .productImg(updateDto.getProductImg())
                .productsContents(updateDto.getProductsContents())
                .productsAmount(updateDto.getProductsAmount())
                .build();
    }

    //상품 목록
    @Override
    public List<ProductListDto> list(String keyword) {
        List<ProductsEntity> products;

        // 키워드가 있는 경우 검색, 없으면 전체 목록 반환
        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productRepository.searchByKeyword(keyword.trim());
        } else {
            products = productRepository.findAll();
        }


        return products.stream()
                .map(productMapper::toProductListDto)
                .collect(Collectors.toList());
    }

    //상품 상세보기
    @Override
    public ProductListDto detail(Long id) {
        Optional<ProductsEntity> optionalProducts = productRepository.findById(id);

        //상품 존재 여부 확인
        ProductsEntity products = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("해당 상품이 없습니다."));

        return productMapper.toProductListDto(products);

    }



}
