package com.microservices.paymentservice.Service.Imp;

import com.microservices.paymentservice.Entity.PaymentStatus;
import com.microservices.paymentservice.Payload.Response.PaymentResponse;

import java.util.List;

public interface PaymentServiceImp {
    boolean createPayment(int idOrder, boolean idPayed, PaymentStatus paymentStatus);
    List<PaymentResponse> getAllPayments();
    PaymentResponse getPaymentById(int id);
    boolean updatePaymentById(int id, int idOrder, boolean idPayed, PaymentStatus paymentStatus);
    boolean deletePaymentById(int id);
   /* List<PaymentEntity> getPaymentsByOrderId(int orderId);*/
}
