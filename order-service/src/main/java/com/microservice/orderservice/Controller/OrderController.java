package com.microservice.orderservice.Controller;

import com.microservice.orderservice.Payload.Request.OrderRequest;
import com.microservice.orderservice.Payload.Response.BaseResponse;
import com.microservice.orderservice.Payload.Response.OrderResponse;
import com.microservice.orderservice.Service.CallAPI;
import com.microservice.orderservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CallAPI callAPI;

    @GetMapping("")
    public ResponseEntity<?> getAllOrder() {
        List<OrderResponse> orderResponses = orderService.getAllOrder();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all order");
        baseResponse.setStatusCode(200);
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id) {
        OrderResponse orderResponses = orderService.getOrderById(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get order by id");
        baseResponse.setStatusCode(200);
        baseResponse.setData(orderResponses);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<?> insertOrder(@RequestBody OrderRequest orderRequest) {
        boolean isSuccess = orderService.insertOrder(orderRequest);
        if (isSuccess) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Insert order successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(isSuccess);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Can't insert order");
            errorResponse.setStatusCode(400);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrderById(@PathVariable int orderId, @RequestParam String orderDesc, @RequestParam Double orderFee, @RequestParam int cartId, @RequestParam int productId) {
        boolean idUpdate = orderService.updateOrderById(orderId, orderDesc, orderFee, cartId, productId);
        if (idUpdate) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Update order successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(idUpdate);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Can't update order");
            errorResponse.setStatusCode(400);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable int id){
            boolean idDelete = orderService.deleteById(id);
        if (idDelete) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Delete order successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(idDelete);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Can't delete order");
            errorResponse.setStatusCode(400);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
