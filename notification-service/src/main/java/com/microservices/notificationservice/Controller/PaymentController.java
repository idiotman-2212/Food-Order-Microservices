package com.microservices.notificationservice.Controller;

import com.microservices.notificationservice.Entity.PaymentStatus;
import com.microservices.notificationservice.Payload.BaseResponse;
import com.microservices.notificationservice.Payload.PaymentResponse;
import com.microservices.notificationservice.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("")
    public ResponseEntity<?> getAllPayments() {
        List<PaymentResponse> paymentResponses = paymentService.getAllPayments();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(paymentResponses);
        baseResponse.setMessage("Get all payments");
        baseResponse.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable int id) {
        PaymentResponse paymentResponse = paymentService.getPaymentById(id);
        BaseResponse baseResponse = new BaseResponse();
        if (paymentResponse != null) {
            baseResponse.setData(paymentResponse);
            baseResponse.setMessage("Get payment by id");
            baseResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(baseResponse);
        } else {
            baseResponse.setMessage("Payment not found");
            baseResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createPayment(@RequestParam int idOrder, @RequestParam int idUser, @RequestParam boolean idPayed, @RequestParam PaymentStatus paymentStatus) {
        boolean isSuccess = paymentService.createPayment(idOrder, idUser, idPayed, paymentStatus).block();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(isSuccess);
        baseResponse.setMessage("Create payment");
        baseResponse.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.ok(baseResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentById(@PathVariable int id, @RequestParam int idOrder, @RequestParam int idUser, @RequestParam boolean idPayed, @RequestParam PaymentStatus paymentStatus) {
        boolean isUpdate = paymentService.updatePaymentById(id, idUser, idOrder, idPayed, paymentStatus);
        BaseResponse baseResponse = new BaseResponse();
        if (isUpdate) {
            baseResponse.setData(true);
            baseResponse.setMessage("Payment updated successfully");
            baseResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(baseResponse);
        } else {
            baseResponse.setMessage("Payment not found");
            baseResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentById(@PathVariable int id) {
        boolean isDeleted = paymentService.deletePaymentById(id);
        BaseResponse baseResponse = new BaseResponse();
        if (isDeleted) {
            baseResponse.setData(true);
            baseResponse.setMessage("Payment deleted successfully");
            baseResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok(baseResponse);
        } else {
            baseResponse.setMessage("Payment not found");
            baseResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        }
    }
}

