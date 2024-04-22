package com.microservices.paymentservice.Helper;

import com.microservices.paymentservice.Payload.Response.PaymentResponse;
import com.microservices.paymentservice.Entity.PaymentEntity;
import com.microservices.paymentservice.Payload.Response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentConverter {
    @Autowired
    private OrderConverter orderConverter;
    public PaymentResponse toPaymentResponse(PaymentEntity paymentEntity) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(paymentEntity.getId());
        paymentResponse.setPaymentStatus(paymentEntity.getPaymentStatus());
        paymentResponse.setPayed(paymentEntity.getPayed());
        paymentResponse.setOrderId(paymentEntity.getOrderId());
        paymentResponse.setUserId(paymentEntity.getUserId());
        return paymentResponse;
    }

    public PaymentEntity toPaymentEntity(PaymentResponse paymentResponse) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(paymentResponse.getId());
        entity.setPayed(paymentResponse.getPayed());
        entity.setPaymentStatus(paymentResponse.getPaymentStatus());
        entity.setUserId(paymentResponse.getUserId());
        entity.setOrderId(paymentResponse.getOrderId());

        return entity;
    }
    public List<PaymentResponse> toPaymentResponseList(List<PaymentEntity> paymentEntities) {
        return paymentEntities.stream()
                .map(this::toPaymentResponse)
                .collect(Collectors.toList());
    }
}
