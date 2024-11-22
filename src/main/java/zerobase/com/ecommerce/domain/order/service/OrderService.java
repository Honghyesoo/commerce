package zerobase.com.ecommerce.domain.order.service;

import zerobase.com.ecommerce.domain.order.dto.OrderDto;

public interface OrderService {
    OrderDto request(OrderDto orderDto);

}
