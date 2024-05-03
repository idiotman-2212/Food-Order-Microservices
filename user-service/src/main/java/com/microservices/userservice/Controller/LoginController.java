package com.microservices.userservice.Controller;

import com.microservices.userservice.Entity.UserEntity;
import com.microservices.userservice.Payload.Request.SignUpRequest;
import com.microservices.userservice.Repository.UserRepository;
import com.microservices.userservice.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public String showLogin() {
        return "signin";
    }

    @PostMapping("/signin")
    public String login(@ModelAttribute(name = "loginForm") SignUpRequest signUpRequest, Model m) {

        UserEntity user = userRepository.findByEmail(signUpRequest.getUsername());

        if (user != null && passwordEncoder.matches(signUpRequest.getPassword(), user.getPassword())) {
            m.addAttribute("user", signUpRequest.getUsername());
            m.addAttribute("pass", signUpRequest.getPassword());
            if (user.getRole().getName().equals("ADMIN")) {
                return "adminPage";
            } else {
                return "index";
            }
        } else {
            m.addAttribute("error", "Incorrect Username & Password");
            return "signin";
        }
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("signupRequest", new SignUpRequest());
        return "signup";
    }
    @PostMapping("/signup")
    public String processRegistrationForm(@ModelAttribute("signupRequest") @Valid SignUpRequest signUpRequest,
                                          BindingResult bindingResult, Model model) {

        if(signUpRequest.getEmail().isEmpty()){
            model.addAttribute("error", "Email have already exits");
            return "signup";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the following errors:");
            return "signup";
        }

        // Set default role ID (Assuming "USER" role has ID 1, modify accordingly)
        signUpRequest.setIdRole(2);

        boolean registrationSuccess = userService.insertUser(signUpRequest);

        if (registrationSuccess) {
            return "redirect:/login/signin";
        } else {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "signup";
        }
    }
}