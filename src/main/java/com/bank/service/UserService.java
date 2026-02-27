package com.bank.service;

import com.bank.dto.LoginRequest;
import com.bank.dto.UserRequest;

public interface UserService {

    String register(UserRequest request);

    String login(LoginRequest request);
}