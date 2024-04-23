package com.microservice.orderservice.Service;

import com.microservice.orderservice.Entity.CartEntity;
import com.microservice.orderservice.Entity.OrderEntity;
import com.microservice.orderservice.Helper.OrderConverter;
import com.microservice.orderservice.Payload.Request.OrderRequest;
import com.microservice.orderservice.Payload.Response.CartResponse;
import com.microservice.orderservice.Payload.Response.OrderResponse;
import com.microservice.orderservice.Payload.Response.ProductResponse;
import com.microservice.orderservice.Repository.CartRepository;
import com.microservice.orderservice.Repository.OrderRepository;
import com.microservice.orderservice.Service.Imp.ApiResponse;
import com.microservice.orderservice.Service.Imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CallAPI callAPI;

    @Override
    public List<OrderResponse> getAllOrder() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(orderEntity.getId());
            orderResponse.setOrderFee(orderEntity.getOrderFee());
            orderResponse.setOrderDesc(orderEntity.getOrderDesc());
            orderResponse.setProductId(orderEntity.getProductId());
            orderResponse.setOrderDate(orderEntity.getOrderDate());

            // Gọi API để lấy thông tin sản phẩm
            Mono<ApiResponse<ProductResponse>> productResponseMono = callAPI.getProductById(orderEntity.getProductId());
            ProductResponse productResponse = Objects.requireNonNull(productResponseMono.block()).getData(); // Block để đợi kết quả từ API

            if (productResponse != null) {
                orderResponse.setProductResponse(productResponse);
            } else {
            }

            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }


    @Override
    public boolean insertOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(new Date());
        orderEntity.setOrderFee(orderRequest.getOrderFee());
        orderEntity.setOrderDesc(orderRequest.getOrderDesc());
        orderEntity.setProductId(orderRequest.getProductId());
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(orderRequest.getCartId());
        orderEntity.setCart(cartEntity);

        orderRepository.save(orderEntity);
        return true;

    }

    @Override
    public OrderResponse getOrderById(int orderId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            OrderEntity orderEntity = optionalOrder.get();
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(orderEntity.getId());
            orderResponse.setOrderDate(orderEntity.getOrderDate());
            orderResponse.setOrderDesc(orderEntity.getOrderDesc());
            orderResponse.setOrderFee(orderEntity.getOrderFee());
            orderResponse.setProductId(orderEntity.getProductId());
            // Fetch product asynchronously
            Mono<ApiResponse<ProductResponse>> productResponseMono = callAPI.getProductById(orderEntity.getProductId());
            ProductResponse productResponse = Objects.requireNonNull(productResponseMono.block()).getData();

            if (productResponse != null) {
                orderResponse.setProductResponse(productResponse);
            } else {
                // Handle product not found
                // You might want to throw an exception or set a default product response
            }

            // Populate cart response
            CartResponse cartResponse = new CartResponse();
            cartResponse.setId(orderEntity.getProductId()); // Assuming this is the cart ID
            // Populate other fields in cartResponse if needed
            orderResponse.setCartResponse(cartResponse);

            return orderResponse;
        } else {
            // Order not found
            // You might want to throw an exception or return an empty response object
            return null;
        }
    }


    @Override
    public boolean updateOrderById(int orderId, String orderDesc, Double orderFee, int cartId, int productId) {
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
            orderEntity.setProductId(productId);
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
