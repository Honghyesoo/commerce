package zerobase.com.ecommerce.domain.review.mapper;

import org.springframework.stereotype.Component;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.review.dto.OrderRegisterDto;
import zerobase.com.ecommerce.domain.review.dto.OrderUpdateDto;
import zerobase.com.ecommerce.domain.review.entity.ReviewEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Component
public class ReviewMapper {

    //리뷰 등록 Dto -> Entity
    public ReviewEntity toEntity (OrderRegisterDto orderRegisterDto, UserEntity user
            , ProductsEntity product){
        return ReviewEntity.builder()
                .userId(user)
                .product(product)
                .contents(orderRegisterDto.getContents())
                .build();
    }

    //리뷰 등록 Entity -> Dto
    public OrderRegisterDto toDto(ReviewEntity review){
        return OrderRegisterDto.builder()
                .userId(review.getUserId().getUserId())
                .product(review.getContents())
                .contents(review.getContents())
                .build();
    }

    //리뷰 수정
    public OrderUpdateDto updateDto(ReviewEntity review){
        return OrderUpdateDto.builder()
                .id(review.getId())
                .userId(review.getUserId().getUserId())
                .contents(review.getContents())
                .build();
    }
}
