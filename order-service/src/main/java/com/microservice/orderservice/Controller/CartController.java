package com.microservice.orderservice.Controller;

import com.microservice.orderservice.Payload.Request.CartRequest;
import com.microservice.orderservice.Payload.Response.BaseResponse;
import com.microservice.orderservice.Payload.Response.Order.CartResponse;
import com.microservice.orderservice.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ResponseEntity<?> getAllCart(@RequestBody CartResponse cartResponse){
        List<CartResponse> cartResponses = cartService.getAllCart();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(cartResponses);
        baseResponse.setMessage("Get all cart");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertCart(@RequestBody CartRequest cartRequest){
            boolean isSuccess = cartService.insertCart(cartRequest);
        if (isSuccess) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Insert cart successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(isSuccess);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Can't insert cart");
            errorResponse.setStatusCode(400);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartById(@PathVariable int id){
        boolean idDelete = cartService.deleteCartById(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Insert cart successfully");
        baseResponse.setStatusCode(200);
        baseResponse.setData(idDelete);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartById(@PathVariable int id, @RequestParam int userId){
        boolean idUpdate = cartService.updateCartById(id, userId);
        if (idUpdate) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Update cart successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(idUpdate);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Can't update cart");
            errorResponse.setStatusCode(400);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
