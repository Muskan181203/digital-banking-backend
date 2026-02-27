package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dto.LoginRequest;
import com.bank.dto.UserRequest;
import com.bank.entity.User;
import com.bank.exception.BankException;
import com.bank.repository.UserRepository;
import com.bank.security.JwtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginRequest request) {

        logger.info("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login failed - User not found: {}", request.getEmail());
                    return new BankException("User not found");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Login failed - Invalid password for email: {}", request.getEmail());
            throw new BankException("Invalid Password");
        }

        logger.info("Login successful for email: {}", request.getEmail());

        return jwtUtil.generateToken(user.getEmail());
    }

    @Override
    public String register(UserRequest request) {

        logger.info("Registration attempt for email: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Registration failed - Email already exists: {}", request.getEmail());
            throw new BankException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .build();

        userRepository.save(user);

        logger.info("User registered successfully with email: {}", request.getEmail());

        return "User Registered Successfully";
    }
}