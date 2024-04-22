package com.microservices.notificationservice.Service.Imp;

import com.microservices.notificationservice.Entity.PaymentStatus;
import com.microservices.notificationservice.Payload.PaymentResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PaymentServiceImp {
    Mono<Boolean> createPayment(int idOrder, int idUser, boolean idPayed, PaymentStatus paymentStatus);
    List<PaymentResponse> getAllPayments();
    PaymentResponse getPaymentById(int id);
    boolean updatePaymentById(int id, int idOrder, int idUser, boolean idPayed, PaymentStatus paymentStatus);
    boolean deletePaymentById(int id);
}
