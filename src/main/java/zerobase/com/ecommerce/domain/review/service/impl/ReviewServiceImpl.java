package zerobase.com.ecommerce.domain.review.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.domain.order.repository.OrderRepository;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.service.ProductFindService;
import zerobase.com.ecommerce.domain.review.dto.OrderRegisterDto;
import zerobase.com.ecommerce.domain.review.dto.OrderUpdateDto;
import zerobase.com.ecommerce.domain.review.entity.ReviewEntity;
import zerobase.com.ecommerce.domain.review.mapper.ReviewMapper;
import zerobase.com.ecommerce.domain.review.repository.ReviewRepository;
import zerobase.com.ecommerce.domain.review.service.ReviewService;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.service.UserFindService;
import zerobase.com.ecommerce.exception.global.CommerceException;
import zerobase.com.ecommerce.exception.type.ErrorCode;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserFindService userFindService;
    private final ProductFindService productFindService;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;
    @Override
    public OrderRegisterDto register(OrderRegisterDto orderRegisterDto) {
        //유저 조회
        UserEntity user = userFindService
                .findUserByIdOrThrow(orderRegisterDto.getUserId());

        //상품 조회
        ProductsEntity product = productFindService
                .findByProductOrThrow(orderRegisterDto.getProduct());

        // 사용자가 해당 상품을 이용했는지 확인
        boolean hasPurchased = orderRepository.existsByUserIdAndProduct(user, product);
        if (!hasPurchased) {
            throw new CommerceException(ErrorCode.PRODUCT_NOT_PURCHASED);
        }

        // 해당 상품에 대해 유저가 이미 리뷰를 남겼는지 확인
        boolean hasReviewed = reviewRepository.existsByUserIdAndProduct(user, product);
        if (hasReviewed) {
            throw new CommerceException(ErrorCode.DUPLICATE_REVIEW);
        }

        // DTO -> Entity 변환 및 저장
        ReviewEntity review = reviewMapper.toEntity(orderRegisterDto,user,product);
        ReviewEntity saveEntity = reviewRepository.save(review);

        return reviewMapper.toDto(saveEntity);
    }

    //리뷰목록
    @Override
    public Page<OrderRegisterDto> list(String product , Pageable pageable) {
        // 페이징을 지원하는 방식으로 조회
        Page<ReviewEntity> reviewEntitiesPage = reviewRepository
                .findAllByProduct_ProductOrderByCreateAtDesc(product, pageable);

        // ReviewEntity -> RegisterDto 변환
        return reviewEntitiesPage.map(reviewMapper::toDto);
    }

    //리뷰 수정
    @Override
    public OrderUpdateDto update(OrderUpdateDto orderUpdateDto) {
        // 입력값 검증
        if (orderUpdateDto == null || orderUpdateDto.getId() == null || orderUpdateDto.getUserId() == null) {
            throw new CommerceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        // 유저 조회
        UserEntity user = userFindService.findUserByIdOrThrow(orderUpdateDto.getUserId());

        // 리뷰 조회
        ReviewEntity review = reviewRepository.findById(orderUpdateDto.getId())
                .orElseThrow(() -> new CommerceException(ErrorCode.REVIEW_NOT_FOUND));

        // 권한 확인: 리뷰 작성자와 요청한 사용자가 동일한지 확인
        if (!review.getUserId().getId().equals(user.getId())) {
            throw new CommerceException(ErrorCode.ACCESS_DENIED);
        }

        // 내용 업데이트
        review.setContents(orderUpdateDto.getContents());

        // 수정된 리뷰 저장
        ReviewEntity updatedReview = reviewRepository.save(review);

        return reviewMapper.updateDto(updatedReview);
    }

    //리뷰 삭제
    @Override
    public void delete(Long id, String userId) {
        // 유저 조회: 사용자 정보 확인
        UserEntity user = userFindService.findUserByIdOrThrow(userId);

        // 특정 리뷰 ID로 리뷰 조회: 리뷰가 존재하는지 확인
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new CommerceException(ErrorCode.PRODUCT_NOT_PURCHASED));

        // 권한 확인: 리뷰 작성자와 요청한 사용자가 동일한지 확인
        if (!review.getUserId().getId().equals(user.getId())) {
            throw new CommerceException(ErrorCode.ACCESS_DENIED);
        }
        // 리뷰 삭제
        reviewRepository.delete(review);
    }
}
