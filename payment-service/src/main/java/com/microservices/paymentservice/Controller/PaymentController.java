package com.microservices.paymentservice.Controller;

import com.microservices.paymentservice.Entity.PaymentStatus;
import com.microservices.paymentservice.Helper.PaymentConverter;
import com.microservices.paymentservice.Payload.Response.BaseResponse;
import com.microservices.paymentservice.Payload.Response.PaymentResponse;
import com.microservices.paymentservice.Service.PaymentService;
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
    @Autowired
    private PaymentConverter paymentConverter;

    @GetMapping("")
    public ResponseEntity<?> getAllPayments(){
        List<PaymentResponse> paymentResponses = paymentService.getAllPayments();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(HttpStatus.OK.value());
        baseResponse.setMessage("Get all payments");
        baseResponse.setData(paymentResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    /*@GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPaymentByOrderId(@PathVariable int orderId) {
        List<PaymentResponse> paymentResponses = paymentConverter.toPaymentResponseList(paymentService.getPaymentsByOrderId(orderId));

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(HttpStatus.OK.value());
        baseResponse.setMessage("Get payment by order id");
        baseResponse.setData(paymentResponses);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }*/
    @PostMapping("")
    public ResponseEntity<?> createPayment(
            @RequestParam int idOrder,
            @RequestParam boolean isPayed,
            @RequestParam PaymentStatus paymentStatus) {

        boolean result = paymentService.createPayment(idOrder, isPayed, paymentStatus);
        BaseResponse baseResponse = new BaseResponse();
        if (result) {
            baseResponse.setStatusCode(200);
            baseResponse.setMessage("Payment created successfully");
            baseResponse.setData(result);
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        } else {
            baseResponse.setStatusCode(404);
            baseResponse.setMessage("Order not found");
            baseResponse.setData(result);
            return new ResponseEntity(baseResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentById(@PathVariable int id, int idOrder, boolean idPayed, PaymentStatus paymentStatus) {
        boolean updatedPayment = paymentService.updatePaymentById(id, idOrder, idPayed, paymentStatus);

        BaseResponse baseResponse = new BaseResponse();
        if (updatedPayment) {
            baseResponse.setStatusCode(HttpStatus.OK.value());
            baseResponse.setMessage("Payment updated successfully");
            baseResponse.setData(updatedPayment);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            baseResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Payment not found");
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentById(@PathVariable int id) {
        boolean deleted = paymentService.deletePaymentById(id);

        BaseResponse baseResponse = new BaseResponse();
        if (deleted) {
            baseResponse.setStatusCode(HttpStatus.OK.value());
            baseResponse.setMessage("Payment deleted successfully");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            baseResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Payment not found");
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }
    }
}