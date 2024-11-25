package zerobase.com.ecommerce.domain.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.com.ecommerce.components.MailComponents;
import zerobase.com.ecommerce.domain.order.dto.OrderDto;
import zerobase.com.ecommerce.domain.order.entity.OrderEntity;
import zerobase.com.ecommerce.domain.order.repository.OrderRepository;
import zerobase.com.ecommerce.domain.order.service.OrderService;
import zerobase.com.ecommerce.domain.products.entity.ProductsEntity;
import zerobase.com.ecommerce.domain.products.repository.ProductRepository;
import zerobase.com.ecommerce.domain.products.service.ProductFindService;
import zerobase.com.ecommerce.domain.user.entity.UserEntity;
import zerobase.com.ecommerce.domain.user.service.UserFindService;
import zerobase.com.ecommerce.exception.order.OrderException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserFindService userFindService;
    private final ProductFindService productFindService;
    private final MailComponents mailComponents;
    private final ProductRepository productRepository;
    @Override
    public OrderDto request(OrderDto orderDto) {
        // 로그인 여부 확인
        userFindService.UserLoginException(orderDto.getUserId());

        //유저 조회
        UserEntity user = userFindService.findUserByIdOrThrow(orderDto.getUserId());

        //상품 조회
        ProductsEntity products = productFindService.findByProductOrThrow(orderDto.getProduct());

        // 금액 계산 (수량 * 가격)
        int totalPrice = orderDto.getQuantity() * products.getPrice();

        // 판매누적량 계산
        products.increaseSalesCount(orderDto.getQuantity());
        productRepository.save(products);

        //주문 엔티티 생성
        OrderEntity order = OrderEntity.builder()
                .id(orderDto.getId())
                .userId(user)
                .product(products)
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .quantity(orderDto.getQuantity())
                .totalPrice(totalPrice)
                .build();

        // 주문 번호 저장
        OrderEntity saveOrder = orderRepository.save(order);

        // 주문 내역 이메일 전송
        sendOrderConfirmationEmail(saveOrder);

        // 저장된 주문 정보를 DTO로 변환하여 반환
        return OrderDto.builder()
                .id(saveOrder.getId())
                .userId(saveOrder.getUserId().getUserId())
                .product(saveOrder.getProduct().getProduct())
                .quantity(saveOrder.getQuantity())
                .totalPrice(saveOrder.getTotalPrice())
                .build();
    }

    // 이메일 발송
    private void sendOrderConfirmationEmail(OrderEntity order) {
        String subject = "주문 확인: 주문번호 " + order.getId();
        String content = getOrderConfirmationEmailTemplate(order);
        boolean emailSent = mailComponents.sendMail(order.getEmail(), subject, content);
        if (!emailSent) {
            throw new OrderException("주문 확인 이메일 전송에 실패했습니다.");
        }
    }

    private String getOrderConfirmationEmailTemplate(OrderEntity order) {
        return "<div style='margin:100px;'>" +
                "<h1>주문 확인</h1>" +
                "<br/>" +
                "<p>주문번호: " + order.getId() + "</p>" +
                "<p>상품명: " + order.getProduct().getProduct() + "</p>" +
                "<p>수량: " + order.getQuantity() + "</p>" +
                "<p>총 금액: " + order.getTotalPrice() + "원</p>" +
                "<p>배송 주소: " + order.getAddress() + "</p>" +
                "<br/>" +
                "<p>주문해 주셔서 감사합니다!</p>" +
                "</div>";
    }
}
