package com.microservice.orderservice.Service.Imp;

import com.microservice.orderservice.Payload.Request.OrderRequest;
import com.microservice.orderservice.Payload.Response.OrderResponse;
import reactor.core.publisher.Flux;

import java.util.List;

public interface OrderServiceImp {
    List<OrderResponse> getAllOrder();
    boolean insertOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(int orderId);
    boolean updateOrderById(int orderId, String orderDesc, Double orderFee, int cartId, int productId);
    boolean deleteById(int orderId);
}
