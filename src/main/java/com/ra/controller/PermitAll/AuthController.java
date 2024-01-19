package com.ra.controller.PermitAll;


import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.UserResponse;
import com.ra.service.UserAndRole.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;


    @PostMapping("/log-in")
    public ResponseEntity<UserResponse> handleLogin(@RequestBody @Valid UserLogin userLogin){
        UserResponse userResponse = userService.handleLogin(userLogin);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> handleRegister(@RequestBody @Valid UserRegister userRegister){
        return new ResponseEntity<>(userService.handleRegister(userRegister),HttpStatus.CREATED);
    }
}
