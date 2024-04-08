package com.microservice.orderservice.Service;

import com.microservice.orderservice.Entity.CartEntity;
import com.microservice.orderservice.Entity.OrderEntity;
import com.microservice.orderservice.Helper.OrderConverter;
import com.microservice.orderservice.Payload.Request.OrderRequest;
import com.microservice.orderservice.Payload.Response.Order.CartResponse;
import com.microservice.orderservice.Payload.Response.Order.OrderResponse;
import com.microservice.orderservice.Repository.CartRepository;
import com.microservice.orderservice.Repository.OrderRepository;
import com.microservice.orderservice.Service.Imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.CacheResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private CartRepository cartRepository;
    @Override
    public List<OrderResponse> getAllOrder() {
        List<OrderEntity> list = orderRepository.findAll();
        List<OrderResponse> responseList = new ArrayList<>();

        for (OrderEntity o: list){
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(o.getId());
            orderResponse.setOrderFee(o.getOrderFee());
            orderResponse.setOrderDesc(o.getOrderDesc());
            orderResponse.setOrderDate(o.getOrderDate());

            CartResponse cartResponse = new CartResponse();
            cartResponse.getId();
            orderResponse.setCartResponse(cartResponse);

            responseList.add(orderResponse);
        }
        return responseList;
    }

    @Override
    public boolean insertOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(new Date());
        orderEntity.setOrderFee(orderRequest.getOrderFee());
        orderEntity.setOrderDesc(orderRequest.getOrderDesc());
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(orderRequest.getCartId());
        orderEntity.setCart(cartEntity);

        orderRepository.save(orderEntity);
        return true;

    }

    @Override
    public OrderResponse getOrderById(int orderId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()){
            OrderEntity orderEntity = optionalOrder.get();
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(orderEntity.getId());
            orderResponse.setOrderDate(orderEntity.getOrderDate());
            orderResponse.setOrderDesc(orderEntity.getOrderDesc());
            orderResponse.setOrderFee(orderEntity.getOrderFee());

            CartResponse cartResponse = new CartResponse();
            cartResponse.getId();
            orderResponse.setCartResponse(cartResponse);

            return orderResponse;
        }else {
            return null;
        }
    }


    @Override
    public boolean updateOrderById(int orderId, String orderDesc, Double orderFee, int cartId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            OrderEntity orderEntity = optionalOrder.get();

            if (orderDesc != null && !orderDesc.isEmpty()) {
                orderEntity.setOrderDesc(orderDesc);
            }
            if (orderFee != null && orderFee > 0) {
                orderEntity.setOrderFee(orderFee);
            }

            Optional<CartEntity> optionalCart = cartRepository.findById(cartId);
            if (optionalCart.isPresent()) {
                CartEntity cart = optionalCart.get();
                orderEntity.setCart(cart);
            } else {
                return false;
            }

            orderEntity.setOrderDate(new Date());

            orderRepository.save(orderEntity);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteById(int orderId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(orderId);
            return true;
        } else {
            return false;
        }
    }


}
