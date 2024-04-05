package com.microservices.paymentservice.Service;

import com.microservices.paymentservice.Entity.OrderEntity;
import com.microservices.paymentservice.Helper.OrderConverter;
import com.microservices.paymentservice.Service.Imp.PaymentServiceImp;
import com.microservices.paymentservice.Entity.PaymentEntity;
import com.microservices.paymentservice.Entity.PaymentStatus;
import com.microservices.paymentservice.Helper.PaymentConverter;
import com.microservices.paymentservice.Payload.Response.PaymentResponse;
import com.microservices.paymentservice.Repository.OrderRepository;
import com.microservices.paymentservice.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements PaymentServiceImp {
    @Autowired
    private OrderRepository orderRepository;

private final OrderConverter orderConverter;
    private final PaymentRepository paymentRepository;
    private final PaymentConverter paymentConverter;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentConverter paymentConverter, OrderConverter orderConverter) {
        this.paymentRepository = paymentRepository;
        this.paymentConverter = paymentConverter;
        this.orderConverter = orderConverter;
    }
    @Override
    public boolean createPayment(int idOrder, boolean isPayed, PaymentStatus paymentStatus) {
        OrderEntity orderEntity = orderRepository.findById(idOrder).orElse(null);

        if (orderEntity == null) {
            return false;
        }

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrder(orderEntity);
        paymentEntity.setPayed(isPayed);
        paymentEntity.setPaymentStatus(paymentStatus);
        paymentEntity.setPaymentDate(new Date());

        paymentRepository.save(paymentEntity);

        return true;
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        List<PaymentEntity> payments = paymentRepository.findAll();
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        for (PaymentEntity paymentEntity : payments) {
            PaymentResponse paymentResponse = paymentConverter.toPaymentResponse(paymentEntity);
            paymentResponse.setOrderResponse(orderConverter.toOrderResponse(paymentEntity.getOrder()));
            paymentResponses.add(paymentResponse);
        }

        return paymentResponses;
    }

    @Override
    public PaymentResponse getPaymentById(int id) {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        return payment.map(paymentConverter::toPaymentResponse).orElse(null);
    }

    @Override
    public boolean updatePaymentById(int id, int idOrder, boolean idPayed, PaymentStatus paymentStatus) {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        if (optionalPayment.isPresent()) {
            PaymentEntity existingPayment = optionalPayment.get();
            existingPayment.setPayed(idPayed);
            existingPayment.setPaymentDate(new Date());
            existingPayment.setPaymentStatus(paymentStatus);

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(idOrder);
            existingPayment.setOrder(orderEntity);

            paymentRepository.save(existingPayment);
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
    /*@Override
    public List<PaymentEntity> getPaymentsByOrderId(int orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);

        return null;
    }*/
}
