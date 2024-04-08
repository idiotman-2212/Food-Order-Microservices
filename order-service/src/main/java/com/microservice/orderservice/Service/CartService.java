package com.microservice.orderservice.Service;

import com.microservice.orderservice.Entity.CartEntity;
import com.microservice.orderservice.Helper.CartConverter;
import com.microservice.orderservice.Payload.Request.CartRequest;
import com.microservice.orderservice.Payload.Response.Order.CartResponse;
import com.microservice.orderservice.Repository.CartRepository;
import com.microservice.orderservice.Service.Imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartServiceImp {
    @Autowired
    private CartRepository cartRepository;

   @Autowired
   private CartConverter cartConverter;

    @Override
    public List<CartResponse> getAllCart() {
        List<CartEntity> cartEntities = cartRepository.findAll();
        List<CartResponse> cartResponses = new ArrayList<>();
        for (CartEntity cartEntity: cartEntities) {
            CartResponse response = cartConverter.toCartResponse(cartEntity);
            cartResponses.add(response);
        }
        return cartResponses;
    }

    @Override
    public CartResponse getCartById(int cartId) {
        Optional<CartEntity> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            CartEntity cartEntity = optionalCart.get();
            CartResponse cartResponse = cartConverter.toCartResponse(cartEntity);
            return cartResponse;
        } else {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
    }

    @Override
    public boolean insertCart(CartRequest cartRequest) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserId(cartRequest.getUserId());

        cartRepository.save(cartEntity);
        return true;
    }

    @Override
    public boolean updateCartById(int cartId, int userId) {
        Optional<CartEntity> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            CartEntity existingCart = optionalCart.get();
            existingCart.setUserId(userId);
            cartRepository.save(existingCart);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteCartById(int cartId) {
        Optional<CartEntity> optionalCart = cartRepository.findById(cartId);
        if(optionalCart.isPresent()){
            cartRepository.deleteById(cartId);
            return true;
        }else{
            return false;
        }
    }
}
