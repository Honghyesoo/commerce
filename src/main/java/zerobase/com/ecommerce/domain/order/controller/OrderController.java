package zerobase.com.ecommerce.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.com.ecommerce.domain.order.dto.OrderDto;
import zerobase.com.ecommerce.domain.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/")
public class OrderController {
    private final OrderService orderService;

    //주문요청 -> 구매완료
    @PostMapping("request")
    public ResponseEntity<OrderDto> request(@RequestBody OrderDto orderDto){
        return ResponseEntity.ok().body(orderService.request(orderDto));
    }
}
