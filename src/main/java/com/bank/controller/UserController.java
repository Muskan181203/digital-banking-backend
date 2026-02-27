package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.dto.UserRequest;
import com.bank.service.UserService;
import com.bank.dto.LoginRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile() {
        return "This is a protected profile API";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
    @PostMapping("/register")
    public String register(@Valid @RequestBody UserRequest request) {
        return userService.register(request);
    }
}