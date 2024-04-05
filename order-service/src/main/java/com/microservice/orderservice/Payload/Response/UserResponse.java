package com.microservice.orderservice.Payload.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserResponse {

    private int id;
    private String username;
    private String email;
    private Date createDate;
    private  String password;
    private String phone;
    private String address;

    @JsonProperty("carts")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CartResponse cartResponse;


}
