package com.microservice.orderservice.Payload.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Set;

public class CartResponse {
    private int id;
    private int userId;
    private Set<OrderResponse> orderResponseSet;
    private UserResponse userResponse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Set<OrderResponse> getOrderResponseSet() {
        return orderResponseSet;
    }

    public void setOrderResponseSet(Set<OrderResponse> orderResponseSet) {
        this.orderResponseSet = orderResponseSet;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
