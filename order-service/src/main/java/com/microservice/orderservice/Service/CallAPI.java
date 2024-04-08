package com.microservice.orderservice.Service;

import com.microservice.orderservice.Payload.Response.Order.CartResponse;
import com.microservice.orderservice.Payload.Response.Product.CategoryResponse;
import com.microservice.orderservice.Payload.Response.Product.ProductResponse;
import com.microservice.orderservice.Payload.Response.User.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CallAPI {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public CallAPI(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<CartResponse> receiverCartResponse(int cartId) {
        return webClientBuilder.baseUrl("http://localhost:8082").build()
                .get()
                .uri("/api/carts/{cartId}", cartId)
                .retrieve()
                .bodyToMono(CartResponse.class);
    }

    public Mono<ProductResponse> receiverProductResponse(int productId) {
        return webClientBuilder.baseUrl("http://localhost:8083").build()
                .get()
                .uri("/api/products/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    public Mono<CategoryResponse> receiverCategoryResponse(int categoryId) {
        return webClientBuilder.baseUrl("http://localhost:8084").build()
                .get()
                .uri("/api/categories/{categoryId}", categoryId)
                .retrieve()
                .bodyToMono(CategoryResponse.class);
    }

    public Mono<UserResponse> receiverUserResponse(int userId, String token) {
        return webClientBuilder.baseUrl("http://localhost:8080").build()
                .get()
                .uri("/api/users/{userId}", userId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(UserResponse.class);
    }
}