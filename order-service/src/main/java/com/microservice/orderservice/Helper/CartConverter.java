package com.microservice.orderservice.Helper;
import com.microservice.orderservice.Entity.CartEntity;
import com.microservice.orderservice.Payload.Response.CartResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartConverter {

    public static CartResponse toCartResponse(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cartEntity.getId());
        cartResponse.setUserId(cartEntity.getUserId());
        // Chuyển đổi tập hợp các OrderEntity thành tập hợp các OrderResponse
        cartResponse.setOrderResponseSet(
                cartEntity.getOrders().stream()
                        .map(OrderConverter::toOrderResponse) // Sử dụng converter của OrderEntity
                        .collect(Collectors.toSet())
        );

        return cartResponse;
    }

    public static CartEntity toCartEntity(CartResponse cartResponse) {
        if (cartResponse == null) {
            return null;
        }

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(cartResponse.getId());
        cartEntity.setUserId(cartResponse.getUserId());
        // Chuyển đổi tập hợp các OrderResponse thành tập hợp các OrderEntity
        cartEntity.setOrders(
                cartResponse.getOrderResponseSet().stream()
                        .map(OrderConverter::toOrderEntity) // Sử dụng converter của OrderResponse
                        .collect(Collectors.toSet())
        );

        return cartEntity;
    }
}

