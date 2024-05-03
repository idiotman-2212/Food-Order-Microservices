package com.microservices.userservice.Service;

import com.microservices.userservice.Entity.RoleEntity;
import com.microservices.userservice.Entity.UserEntity;
import com.microservices.userservice.Payload.Request.SignUpRequest;
import com.microservices.userservice.Payload.Response.UserResponse;
import com.microservices.userservice.Service.Imp.UserServiceImp;
import com.microservices.userservice.Repository.RoleRepository;
import com.microservices.userservice.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean insertUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())){
            System.out.println("Email đã tồn tại");
            return false;
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        newUser.setAddress(signUpRequest.getAddress());
        newUser.setPhone(signUpRequest.getPhone());

        // Set default role for the user if found
        RoleEntity defaultRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Default role not found"));
        newUser.setRole(defaultRole);
        newUser.setCreateDate(new Date());

        userRepository.save(newUser);

        return true;
    }

    @Override
    public List<UserResponse> getAllUser() {
       List<UserEntity> list = userRepository.findAll();
        List<UserResponse> responseList = new ArrayList<>();
        for (UserEntity u: list) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(u.getId());
            userResponse.setUsername(u.getUsername());
            userResponse.setEmail(u.getEmail());

            RoleEntity roleEntity = u.getRole();
            userResponse.setRoles(roleEntity.getId());

            userResponse.setAddress(u.getAddress());
            userResponse.setPhone(u.getPhone());
            userResponse.setCreateDate(u.getCreateDate());

            responseList.add(userResponse);
        }

        return responseList;
    }

    @Override
    public UserResponse getUserById(int id) {
        Optional<UserEntity> optional = userRepository.findById(id);

        if(optional.isPresent()){
            UserEntity userEntity = optional.get();
            UserResponse response = new UserResponse();

            response.setId(userEntity.getId());
            response.setUsername(userEntity.getUsername());
            response.setEmail(userEntity.getEmail());
            response.setEmail(userEntity.getEmail());
            response.setAddress(userEntity.getAddress());
            response.setPhone(userEntity.getPhone());

            RoleEntity roles = new RoleEntity();
            response.setRoles(roles.getId());

            response.setCreateDate(userEntity.getCreateDate());

            return response;
        }else {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
    }

    @Override
    public boolean deleteUserById(int id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if(optional.isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(int id, UserEntity userRequest) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserEntity existingUser = optionalUser.get();

            existingUser.setUsername(userRequest.getUsername());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setAddress(userRequest.getAddress());
            existingUser.setPhone(userRequest.getPhone());

            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            RoleEntity requestedRole = userRequest.getRole();
            if (requestedRole != null) {
                RoleEntity existingRole = roleRepository.findById(requestedRole.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Role not found"));
                existingUser.setRole(existingRole);
            }

            existingUser.setCreateDate(new Date());

            userRepository.save(existingUser);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {
        List<UserEntity> list = userRepository.searchUsers(keyword);
        List<UserResponse> responseList = new ArrayList<>();

        for (UserEntity u : list) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(u.getId());
            userResponse.setUsername(u.getUsername());
            userResponse.setEmail(u.getEmail());
            userResponse.setCreateDate(u.getCreateDate());
            responseList.add(userResponse);
        }

        return responseList;
    }

}
