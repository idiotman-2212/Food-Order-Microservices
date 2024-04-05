package com.microservice.orderservice.Service.Imp;

import com.microservice.orderservice.Payload.Request.OrderRequest;
import com.microservice.orderservice.Payload.Response.OrderResponse;

import java.util.List;

public interface OrderServiceImp {
    List<OrderResponse> getAllOrder();
    boolean insertOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(int orderId);
    boolean updateOrderById(int orderId, String orderDesc, Double orderFee, int cartId);
    boolean deleteById(int orderId);
}
