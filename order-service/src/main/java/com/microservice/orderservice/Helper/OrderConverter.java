package com.microservice.orderservice.Helper;

import com.microservice.orderservice.Entity.OrderEntity;
import com.microservice.orderservice.Payload.Response.Order.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {
    public static OrderResponse toOrderResponse(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderEntity.getId());
        orderResponse.setOrderDate(orderEntity.getOrderDate());
        orderResponse.setOrderDesc(orderEntity.getOrderDesc());
        orderResponse.setOrderFee(orderEntity.getOrderFee());

        // Chuyển đổi từ CartEntity sang CartResponse nếu có
        orderResponse.setCartResponse(CartConverter.toCartResponse(orderEntity.getCart()));

        return orderResponse;
    }

    public static OrderEntity toOrderEntity(OrderResponse orderResponse) {
        if (orderResponse == null) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderResponse.getId());
        orderEntity.setOrderDate(orderResponse.getOrderDate());
        orderEntity.setOrderDesc(orderResponse.getOrderDesc());
        orderEntity.setOrderFee(orderResponse.getOrderFee());

        // Chuyển đổi từ CartResponse sang CartEntity nếu có
        orderEntity.setCart(CartConverter.toCartEntity(orderResponse.getCartResponse()));

        return orderEntity;
    }
}
