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
        newUser.getRoles().add(defaultRole);
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

            // Lấy danh sách vai trò của người dùng và chuyển đổi thành danh sách tên vai trò
            Set<String> roles = u.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet());
            userResponse.setRoles(roles);

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
            // Chuyển đổi từ Set<RoleEntity> sang Set<String>
            Set<String> roles = userEntity.getRoles().stream()
                    .map(RoleEntity::getName)
                    .collect(Collectors.toSet());

            response.setRoles(roles);

            response.setCreateDate(userEntity.getCreateDate());

            return response;
        }else {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
    }

    @Override
    public List<UserResponse> searchUsers(String query) {
        return null;
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
        Optional<UserEntity> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            UserEntity userEntity = optional.get();

            userEntity.setUsername(userRequest.getUsername());
            userEntity.setEmail(userRequest.getEmail());
            userEntity.setAddress(userRequest.getAddress());
            userEntity.setPhone(userRequest.getPhone());

            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
                userEntity.setPassword(encodedPassword);
            }
            if (userRequest.getRoles() != null) {
                Set<RoleEntity> updatedRoles = new HashSet<>();
                for (RoleEntity role : userRequest.getRoles()) {
                    RoleEntity existingRole = roleRepository.findByName(role.getName()).get();

                    if (existingRole != null) {
                        updatedRoles.add(existingRole);
                    } else {
                    }
                }
                userEntity.setRoles(updatedRoles);
            }

            userEntity.setCreateDate(new Date());

            UserEntity updatedUser = userRepository.save(userEntity);

            return true;
        } else {
            return false;
        }
    }


}
