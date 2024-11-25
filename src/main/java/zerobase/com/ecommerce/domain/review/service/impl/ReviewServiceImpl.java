package zerobase.com.ecommerce.domain.review.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.domain.order.repository.OrderRepository;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.service.ProductFindService;
import zerobase.com.ecommerce.domain.review.dto.RegisterDto;
import zerobase.com.ecommerce.domain.review.dto.UpdateDto;
import zerobase.com.ecommerce.domain.review.entity.ReviewEntity;
import zerobase.com.ecommerce.domain.review.mapper.ReviewMapper;
import zerobase.com.ecommerce.domain.review.repository.ReviewRepository;
import zerobase.com.ecommerce.domain.review.service.ReviewService;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.repository.UserRepository;
import zerobase.com.ecommerce.domain.user.service.UserFindService;
import zerobase.com.ecommerce.exception.review.DuplicateReviewException;
import zerobase.com.ecommerce.exception.review.ProductNotPurchasedException;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final ProductFindService productFindService;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;
    @Override
    public RegisterDto register(RegisterDto registerDto) {
        //유저 조회
        UserEntity user = userFindService
                .findUserByIdOrThrow(registerDto.getUserId());

        //상품 조회
        ProductsEntity product = productFindService
                .findByProductOrThrow(registerDto.getProduct());

        // 사용자가 해당 상품을 이용했는지 확인
        boolean hasPurchased = orderRepository.existsByUserIdAndProduct(user, product);
        if (!hasPurchased) {
            throw new ProductNotPurchasedException("해당 상품을 구매한 적이 없으므로 리뷰를 남길 수 없습니다.");
        }

        // 해당 상품에 대해 유저가 이미 리뷰를 남겼는지 확인
        boolean hasReviewed = reviewRepository.existsByUserIdAndProduct(user, product);
        if (hasReviewed) {
            throw new DuplicateReviewException("이미 해당 상품에 대해 리뷰를 남겼습니다.");
        }

        // DTO -> Entity 변환 및 저장
        ReviewEntity review = reviewMapper.toEntity(registerDto,user,product);
        ReviewEntity saveEntity = reviewRepository.save(review);

        return reviewMapper.toDto(saveEntity);
    }

    //리뷰목록
    @Override
    public Page<RegisterDto> list(String product , Pageable pageable) {
        // 페이징을 지원하는 방식으로 조회
        Page<ReviewEntity> reviewEntitiesPage = reviewRepository
                .findAllByProduct_ProductOrderByCreateAtDesc(product, pageable);

        // ReviewEntity -> RegisterDto 변환
        return reviewEntitiesPage.map(reviewMapper::toDto);
    }

    //리뷰 수정
    @Override
    public UpdateDto update(UpdateDto updateDto) {
        // 입력값 검증
        if (updateDto == null || updateDto.getId() == null || updateDto.getUserId() == null) {
            throw new IllegalArgumentException("요청 데이터가 유효하지 않습니다.");
        }

        // 유저 조회
        UserEntity user = userFindService.findUserByIdOrThrow(updateDto.getUserId());

        // 리뷰 조회
        ReviewEntity review = reviewRepository.findById(updateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰가 없습니다."));

        // 권한 확인: 리뷰 작성자와 요청한 사용자가 동일한지 확인
        if (!review.getUserId().getId().equals(user.getId())) {
            throw new AccessDeniedException("리뷰를 수정할 권한이 없습니다.");
        }

        // 내용 업데이트
        review.setContents(updateDto.getContents());

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
                .orElseThrow(() -> new EntityNotFoundException("해당하는 리뷰를 찾을 수 없습니다."));

        // 권한 확인: 리뷰 작성자와 요청한 사용자가 동일한지 확인
        if (!review.getUserId().getId().equals(user.getId())) {
            throw new AccessDeniedException("리뷰를 삭제할 권한이 없습니다.");
        }
        // 리뷰 삭제
        reviewRepository.delete(review);
    }
}