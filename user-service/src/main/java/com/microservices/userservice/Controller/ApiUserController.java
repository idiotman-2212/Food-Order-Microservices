package com.microservices.userservice.Controller;

import com.microservices.userservice.Entity.UserEntity;
import com.microservices.userservice.Payload.Response.BaseResponse;
import com.microservices.userservice.Payload.Response.UserResponse;
import com.microservices.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        List<UserResponse> userResponses = userService.getAllUser();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get all user");
        baseResponse.setData(userResponses);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        UserResponse userResponse = userService.getUserById(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Get user by id");
        baseResponse.setData(userResponse);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserEntity userRequest) {
        boolean isUpdated = userService.updateUser(id, userRequest);

        if (isUpdated) {
            return ResponseEntity.ok().body("User updated successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found or update failed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id){

        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("User deleted successfully");
            baseResponse.setStatusCode(200);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found or unable to delete", HttpStatus.OK);
        }
    }
}
