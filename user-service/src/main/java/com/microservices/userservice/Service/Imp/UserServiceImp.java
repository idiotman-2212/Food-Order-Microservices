package com.microservices.userservice.Service.Imp;

import com.microservices.userservice.Entity.UserEntity;
import com.microservices.userservice.Payload.Request.SignUpRequest;
import com.microservices.userservice.Payload.Response.UserResponse;

import java.util.List;

public interface UserServiceImp {
    boolean insertUser(SignUpRequest signUpRequest );

    List<UserResponse> getAllUser();

    UserResponse getUserById(int id);

    List<UserResponse> searchUsers(String keyword);

    boolean deleteUserById(int id);

    boolean updateUser(int id, UserEntity userRequest);

}
