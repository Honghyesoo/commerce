package zerobase.com.ecommerce.domain.review.mapper;

import org.springframework.stereotype.Component;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.review.dto.RegisterDto;
import zerobase.com.ecommerce.domain.review.dto.UpdateDto;
import zerobase.com.ecommerce.domain.review.entity.ReviewEntity;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;

@Component
public class ReviewMapper {

    //리뷰 등록 Dto -> Entity
    public ReviewEntity toEntity (RegisterDto registerDto, UserEntity user
            , ProductsEntity product){
        return ReviewEntity.builder()
                .userId(user)
                .product(product)
                .contents(registerDto.getContents())
                .build();
    }

    //리뷰 등록 Entity -> Dto
    public RegisterDto toDto(ReviewEntity review){
        return RegisterDto.builder()
                .userId(review.getUserId().getUserId())
                .product(review.getContents())
                .contents(review.getContents())
                .build();
    }

    //리뷰 수정
    public UpdateDto updateDto(ReviewEntity review){
        return UpdateDto.builder()
                .id(review.getId())
                .userId(review.getUserId().getUserId())
                .contents(review.getContents())
                .build();
    }
}
