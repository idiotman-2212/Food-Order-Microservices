package com.microservice.orderservice.Service.Imp;

import com.microservice.orderservice.Payload.Request.CartRequest;
import com.microservice.orderservice.Payload.Response.CartResponse;

import java.util.List;

public interface CartServiceImp {
    List<CartResponse> getAllCart();
    CartResponse getCartById(int cartId);
    boolean insertCart(CartRequest cartRequest);
    boolean updateCartById(int cartId,int userId);
    boolean deleteCartById(int cartId);
}
