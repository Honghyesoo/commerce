package zerobase.com.ecommerce.domain.cart.mapper;

import org.springframework.stereotype.Component;
import zerobase.com.ecommerce.domain.cart.dto.CartDto;
import zerobase.com.ecommerce.domain.cart.entity.CartEntity;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Component
    public class CartMapper {
        // 장바구니 DTO -> Entity
        public CartEntity toEntity(CartDto cartDto, ProductsEntity products) {
            UserEntity user = UserEntity.builder().userId(cartDto.getUserId()).build(); // UserEntity 변환
            return CartEntity.builder()
                    .userId(user)
                    .product(products)  // ProductsEntity로 설정
                    .quantity(cartDto.getQuantity())
                    .price(products.getPrice())
                    .build();
        }

        // 장바구니 Entity -> Dto
        public CartDto toDto(CartEntity cartEntity) {
            int totalPrice = cartEntity.getQuantity() * cartEntity.getPrice();  // totalPrice 계산

            return CartDto.builder()
                    .id(cartEntity.getId())
                    .userId(cartEntity.getUserId().getUserId())
                    .product(cartEntity.getProduct().getId())  // productId로 변환
                    .quantity(cartEntity.getQuantity())
                    .price(cartEntity.getPrice())
                    .totalPrice(totalPrice)
                    .build();
        }


    //상품목록
    public CartDto toCartListDto(CartEntity cart) {
        CartDto dto = new CartDto();

        // CartEntity에서 필요한 필드 값 가져와서 CartDto에 설정
        dto.setId(cart.getId());
        dto.setUserId(cart.getUserId().getUserId());  // UserId에서 userId를 가져옴
        dto.setProduct(cart.getProduct().getId());  // ProductsEntity에서 상품명을 가져옴
        dto.setPrice(cart.getPrice());  // 상품 가격 설정

        return dto;
    }
}

