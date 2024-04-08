package com.microservice.orderservice.Payload.Response.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservice.orderservice.Payload.Response.Order.CartResponse;

import java.io.Serializable;
import java.util.Date;

public class UserResponse implements Serializable {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CartResponse getCartResponse() {
        return cartResponse;
    }

    public void setCartResponse(CartResponse cartResponse) {
        this.cartResponse = cartResponse;
    }
}
