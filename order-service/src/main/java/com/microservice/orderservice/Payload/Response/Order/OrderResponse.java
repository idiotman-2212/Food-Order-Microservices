package com.microservice.orderservice.Payload.Response.Order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservice.orderservice.Payload.Response.Order.CartResponse;
import com.microservice.orderservice.Payload.Response.Product.ProductResponse;

import java.io.Serializable;
import java.util.Date;

public class OrderResponse implements Serializable {
    private int id;
    private Date orderDate;
    private String orderDesc;
    private Double orderFee;

    private Integer productId;

    private ProductResponse productResponse;
    @JsonProperty("cart")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CartResponse cartResponse;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Double getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(Double orderFee) {
        this.orderFee = orderFee;
    }

    public CartResponse getCartResponse() {
        return cartResponse;
    }

    public void setCartResponse(CartResponse cartResponse) {
        this.cartResponse = cartResponse;
    }
}
