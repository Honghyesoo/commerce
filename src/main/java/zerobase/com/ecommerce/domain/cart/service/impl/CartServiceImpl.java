package zerobase.com.ecommerce.domain.cart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.com.ecommerce.components.SecurityUtil;
import zerobase.com.ecommerce.domain.cart.dto.CartDto;
import zerobase.com.ecommerce.domain.cart.entity.CartEntity;
import zerobase.com.ecommerce.domain.cart.mapper.CartMapper;
import zerobase.com.ecommerce.domain.cart.repository.CartRepository;
import zerobase.com.ecommerce.domain.cart.service.CartService;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.repository.ProductRepository;
import zerobase.com.ecommerce.domain.products.service.ProductFindService;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.service.UserFindService;
import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserFindService userFindService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    //장바구니 등록
    @Override
    public CartDto register(CartDto cartDto) {
        // 현재 로그인된 사용자 정보 가져오기
        SecurityUtil.UserInfo userInfo = SecurityUtil.getCurrentUserInfo();
        String currentUserId = userInfo.username();

        // 입력된 userId와 현재 로그인된 사용자 정보가 일치하는지 확인
        if (!currentUserId.equals(cartDto.getUserId())) {
            throw new CommerceException(ErrorCode.INVALID_REQUEST);
        }

        // 사용자 ID 확인
        UserEntity user = userFindService.findUserByIdOrThrow(currentUserId);

        // 상품 id 가져오기
        ProductsEntity product = productRepository.findById(cartDto.getProduct())
                .orElseThrow(() -> new CommerceException(ErrorCode.PRODUCT_NOT_FOUND));

        // 이미 해당 유저가 해당 상품을 장바구니에 담았는지 확인
        Optional<CartEntity> existingCart = cartRepository.findByUserId_UserIdAndProduct_Id(
                cartDto.getUserId(), product.getId());

        if (existingCart.isPresent()) {
            // 이미 장바구니에 같은 상품이 있으면 수량만 추가
            CartEntity cartEntity = existingCart.get();
            cartEntity.setQuantity(cartEntity.getQuantity() + cartDto.getQuantity());
            cartEntity.setTotalPrice(cartEntity.getQuantity() * cartEntity.getPrice());

            CartEntity savedEntity = cartRepository.save(cartEntity);
            return cartMapper.toDto(savedEntity);
        }

        // 새로 등록할 경우 (상품이 장바구니에 없을 경우)
        // 금액 계산 (수량 * 가격)
        int totalPrice = cartDto.getQuantity() * product.getPrice();

        // DTO -> Entity 변환 및 저장
        CartEntity cart = cartMapper.toEntity(cartDto, product);
        cart.setUserId(user); // User 설정
        cart.setTotalPrice(totalPrice); // totalPrice 설정

        CartEntity saveEntity = cartRepository.save(cart);

        return cartMapper.toDto(saveEntity);
    }


    //상품 목록
    @Override
    public List<CartDto> list(CartDto cartDto) {
        // 로그인 여부 확인
        userFindService.UserLoginException(cartDto.getUserId());

        // 특정 유저의 장바구니 목록 조회
        List<CartEntity> cartEntities = cartRepository.findByUserId_UserId(cartDto.getUserId());

        // 엔티티 목록을 DTO 목록으로 변환
        return cartEntities.stream()
                .map(cartMapper::toCartListDto)
                .collect(Collectors.toList());
    }

    //장바구니 삭제
    @Transactional
    @Override
    public void delete(Long id) {
        // 장바구니 항목 존재 여부 확인
        if (!cartRepository.existsById(id)) {
            throw new CommerceException(ErrorCode.CART_ITEM_NOT_FOUND);
        }

        cartRepository.deleteById(id);
    }

}
