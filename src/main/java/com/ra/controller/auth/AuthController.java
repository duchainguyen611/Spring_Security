package com.ra.controller.auth;


import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.UserResponse;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrinciple;
import com.ra.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;


    @PostMapping("/sign-in")
    public ResponseEntity<UserResponse> handleLogin(@RequestBody @Valid UserLogin userLogin){
        UserResponse userResponse = userService.handleLogin(userLogin);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> handleRegister(@RequestBody @Valid UserRegister userRegister){
//        Users users = userService.handleRegister(userRegister)
        return new ResponseEntity<>(userService.handleRegister(userRegister),HttpStatus.CREATED);
    }
}
