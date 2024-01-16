package com.ra.service;

import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.JwtResponse;
import org.springframework.stereotype.Service;


public interface UserService {
    JwtResponse handleLogin(UserLogin userLogin);
    String handleRegister(UserRegister userRegister);
}
