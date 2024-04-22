package com.microservices.notificationservice.Service;

import com.microservices.notificationservice.Entity.PaymentEntity;
import com.microservices.notificationservice.Entity.PaymentStatus;
import com.microservices.notificationservice.Payload.PaymentResponse;
import com.microservices.notificationservice.Repository.PaymentRepository;
import com.microservices.notificationservice.Service.Imp.PaymentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements PaymentServiceImp {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Mono<Boolean> createPayment(int orderId, int idUser, boolean idPayed, PaymentStatus paymentStatus) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrderId(orderId);
        paymentEntity.setUserId(idUser);
        paymentEntity.setPayed(idPayed);
        paymentEntity.setPaymentStatus(paymentStatus);
        paymentEntity.setPaymentDate(new Date());

        return Mono.fromCallable(() -> {
            paymentRepository.save(paymentEntity);
            return true;
        });
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        List<PaymentEntity> payments = paymentRepository.findAll();
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        for (PaymentEntity paymentEntity : payments) {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setId(paymentEntity.getId());
            paymentResponse.setPaymentStatus(paymentEntity.getPaymentStatus());
            paymentResponse.setPayed(paymentEntity.getPayed());
            paymentResponse.setUserId(paymentEntity.getUserId());
            paymentResponse.setOrderId(paymentEntity.getOrderId());
            paymentResponse.setCreateDate(paymentEntity.getPaymentDate());
            paymentResponses.add(paymentResponse);
        }

        return paymentResponses;
    }

    @Override
    public PaymentResponse getPaymentById(int id) {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        PaymentResponse paymentResponse = new PaymentResponse();
        if (optionalPayment.isPresent()) {

            PaymentEntity paymentEntity = optionalPayment.get();
            paymentResponse.setId(paymentEntity.getId());
            paymentResponse.setOrderId(paymentEntity.getOrderId());
            paymentResponse.setUserId(paymentEntity.getUserId());
            paymentResponse.setPayed(paymentEntity.getPayed());
            paymentResponse.setPaymentStatus(paymentEntity.getPaymentStatus());
            paymentResponse.setCreateDate(paymentEntity.getPaymentDate());

        }
        return paymentResponse;
    }

    @Override
    public boolean updatePaymentById(int id, int idUser, int idOrder, boolean idPayed, PaymentStatus paymentStatus) {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            PaymentEntity paymentEntity = optionalPayment.get();
            paymentEntity.setUserId(idUser);
            paymentEntity.setOrderId(idOrder);
            paymentEntity.setPayed(idPayed);
            paymentEntity.setPaymentStatus(paymentStatus);

            paymentRepository.save(paymentEntity);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePaymentById(int id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
