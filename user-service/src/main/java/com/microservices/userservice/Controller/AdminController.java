package com.microservices.userservice.Controller;

import com.microservices.userservice.Entity.RoleEntity;
import com.microservices.userservice.Entity.UserEntity;
import com.microservices.userservice.Payload.Request.SignUpRequest;
import com.microservices.userservice.Payload.Response.RoleResponse;
import com.microservices.userservice.Payload.Response.UserResponse;
import com.microservices.userservice.Repository.UserRepository;
import com.microservices.userservice.Service.RoleService;
import com.microservices.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("")
    public String adminHome() {
        return "adminPage";
    }//page admin home

    //Accounts
    @GetMapping("/users")
    public String getAcc(Model model) {
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("roles", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/users/add")
    public String getUserAdd(Model model) {
        model.addAttribute("userDTO", new SignUpRequest());
        model.addAttribute("roles", roleService.getAllRoles());
        return "usersAdd";
    }

    @PostMapping("/users/add")
    public String postUserAdd(@ModelAttribute("userDTO") SignUpRequest userDTO) {
        UserEntity user = new UserEntity();
        RoleEntity roleEntity = new RoleEntity();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUsername());

        if (userDTO.getIdRole() != 0) {
            int roleId = userDTO.getIdRole();
            roleEntity = roleService.getRoleById(roleId);
        }
        if (roleEntity == null) {
            System.out.println("Vai trò không tồn tại");
            return "redirect:/admin/users";
        }

        user.setRole(roleEntity);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Thêm thất bại " + e.getMessage());
            return "redirect:/admin/users?error=true";
        }
        return "redirect:/admin/users";
    }//user add

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }//delete 1 user

    @GetMapping("/users/update/{id}")
    public String getUserUpdate(@PathVariable int id, Model model) {
        Optional<UserEntity> opUser = userRepository.findById(id);

        if (opUser.isPresent()) {
            UserEntity user = opUser.get();
            SignUpRequest userDTO = new SignUpRequest();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(null);
            userDTO.setUsername(user.getUsername());

            if (user.getRole() != null) {
                userDTO.setIdRole(user.getRole().getId());
            }
            model.addAttribute("userDTO", userDTO);
            model.addAttribute("roles", roleService.getAllRoles());
            return "usersEdit";
        } else {
            return "404";
        }
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("userDTO") SignUpRequest signUpRequest) {
        Optional<UserEntity> opUser = userRepository.findById(id);

        if (opUser.isPresent()) {
            UserEntity user = opUser.get();
            user.setEmail(signUpRequest.getEmail());
            if (signUpRequest.getPassword() != null && !signUpRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            }

            user.setUsername(signUpRequest.getUsername());

            if (signUpRequest.getIdRole() != 0) {
                int roleId = signUpRequest.getIdRole();
                RoleEntity userRole = roleService.getRoleById(roleId);
                if (userRole != null) {
                    user.setRole(userRole);
                } else {
                    System.out.println("Vai trò không tồn tại");
                    return "redirect:/admin/users";
                }
            }

            userRepository.save(user);
            return "redirect:/admin/users";
        } else {
            return "404";
        }
    }//update user

    @GetMapping("users/search")
    public String searchUser(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<UserResponse> list = userService.searchUsers(keyword);

        if (keyword != null && !keyword.isEmpty()) {
            if (list.isEmpty()) {
                model.addAttribute("noResults", true);
            }
        }

        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        return "users";
    }//search user

}
