package zerobase.com.ecommerce.domain.products.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.components.SecurityUtil;
import zerobase.com.ecommerce.domain.constant.Role;
import zerobase.com.ecommerce.domain.constant.SortOption;
import zerobase.com.ecommerce.domain.products.dto.ProductListDto;
import zerobase.com.ecommerce.domain.products.dto.ProductUpdateDto;
import zerobase.com.ecommerce.domain.products.service.ProductFindService;
import zerobase.com.ecommerce.domain.user.service.UserFindService;
import zerobase.com.ecommerce.domain.products.dto.ProductRegisterDto;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.mapper.ProductMapper;
import zerobase.com.ecommerce.domain.products.repository.ProductRepository;
import zerobase.com.ecommerce.domain.products.service.ProductService;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserFindService userFindService;

    //상품 등록
    @Override
    public ProductRegisterDto register(ProductRegisterDto productRegisterDto) {
        //사용자 id 여부
        UserEntity user = userFindService
                .findUserByIdOrThrow(productRegisterDto.getUserId());

        // 사용자 권한 확인
        SecurityUtil.UserInfo userInfo = SecurityUtil.getCurrentUserInfo();
        String userName = userInfo.username();
        String role = userInfo.role();
        log.info(userName);
        log.info(role);

        //권한 여부
        if (role.equals(Role.USER.name())) {
            throw new CommerceException(ErrorCode.USER_CANNOT_REGISTER_PRODUCT);
        }

        Optional<ProductsEntity> optionalProducts =
                productRepository.findByProduct(productRegisterDto.getProduct());

        //상품 중복체크
        if (optionalProducts.isPresent()) {
            throw new CommerceException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        // DTO -> Entity 변환 및 저장
        ProductsEntity productsEntity = productMapper.toProductsEntity(productRegisterDto, user);
        ProductsEntity saveEntity = productRepository.save(productsEntity);
        return productMapper.toProductDto(saveEntity);
    }

    //상품 삭제
    @Override
    public boolean delete(String userId, Long id) {
        //해당 상품 ID 여부
        ProductsEntity products1 =
                productRepository.findById(id)
                        .orElseThrow(()-> new CommerceException(ErrorCode.PRODUCT_NOT_FOUND));

        // 사용자 권한 확인
        SecurityUtil.UserInfo userInfo = SecurityUtil.getCurrentUserInfo();
        String userName = userInfo.username();

        //요청한 사용자와 상품등록 사용자가 동일한지 확인
        if (!products1.getUserId().getUserId().equals(userName)) {
            throw new CommerceException(ErrorCode.ACCESS_DENIED,
                    "해당 상품은 다른 사용자가 등록한 상품입니다. 삭제할 수 없습니다.");
        }

        try {
            // 상품 삭제
            productRepository.delete(products1);
            return true;
        } catch (Exception e) {
            // 삭제 실패 시 로그 및 예외 처리
            throw new CommerceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    //상품 수정
    @Override
    public ProductUpdateDto update(ProductUpdateDto updateDto) {
        //해당 상품 ID 여부
        ProductsEntity products1 =
                productRepository.findById(updateDto.getId())
                        .orElseThrow(()-> new CommerceException(ErrorCode.PRODUCT_NOT_FOUND));

        // 사용자 권한 확인
        SecurityUtil.UserInfo userInfo = SecurityUtil.getCurrentUserInfo();
        String userName = userInfo.username();

        if (!products1.getUserId().getUserId().equals(userName)) {
            throw new CommerceException(ErrorCode.ACCESS_DENIED,
                    "해당 상품은 다른 사용자가 등록한 상품입니다. 수정할 수 없습니다.");
        }


        //상품 정보 업데이트
        products1.setProduct(updateDto.getProduct());
        products1.setProductImg(updateDto.getProductImg());
        products1.setProductsContents(updateDto.getProductsContents());
        products1.setPrice(updateDto.getPrice());

        //변경된 상품 정보 저장
        ProductsEntity updatedProduct = productRepository.save(products1);

        //업데이트된 정보 DTO 변환하여 반환
        return ProductUpdateDto.builder()
                .userId(updatedProduct.getUserId().getUserId())
                .product(updatedProduct.getProduct())
                .productImg(updateDto.getProductImg())
                .productsContents(updateDto.getProductsContents())
                .price(updateDto.getPrice())
                .build();
    }

    //상품 목록
    @Override
    public List<ProductListDto> list(String keyword, SortOption sortOption) {
        List<ProductsEntity> products;

        // 키워드 검색 또는 전체 목록 조회
        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productRepository.searchByKeyword(keyword.trim());
        } else {
            products = productRepository.findAll();
        }

        // 정렬 적용
        switch (sortOption) {
            case LATEST:
            default:
                products.sort(Comparator.comparing(ProductsEntity::getCreateAt).reversed());
                break;
            case SALES:
                products.sort(Comparator.comparing(ProductsEntity::getSalesCount).reversed());
                break;
        }

        // ProductListDto로 변환
        return products.stream()
                .map(productMapper::toProductListDto)
                .collect(Collectors.toList());
    }


//상품 상세보기
@Override
public ProductListDto detail(Long id) {

    //상품 존재 여부 확인
    ProductsEntity products = productRepository.findById(id)
            .orElseThrow(() -> new CommerceException(ErrorCode.PRODUCT_NOT_FOUND));

    return productMapper.toProductListDto(products);

}

}
