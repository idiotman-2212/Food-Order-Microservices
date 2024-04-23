package com.microservice.orderservice.Service;

import com.microservice.orderservice.Payload.Response.ProductResponse;
import com.microservice.orderservice.Payload.Response.UserResponse;
import com.microservice.orderservice.Service.Imp.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Component
public class CallAPI {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<UserResponse> getUserById(int userId, String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/users/{userId}", userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserResponse.class);

    }

    public Mono<ApiResponse<ProductResponse>> getProductById(int productId) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/products/{productId}", productId)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<ProductResponse>>() {});
    }

}