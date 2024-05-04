package com.microservices.paymentservice.Payload.Request;

public class PaymentRequest {
    private int total;
    private String orderInfo;
    private String urlReturn;

    // Constructor
    public PaymentRequest() {
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getUrlReturn() {
        return urlReturn;
    }

    public void setUrlReturn(String urlReturn) {
        this.urlReturn = urlReturn;
    }
}
