package com.microservice.orderservice.Payload.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservice.orderservice.Payload.Response.OrderResponse;
import com.microservice.orderservice.Payload.Response.UserResponse;

import java.util.Set;

public class CartRequest {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
