package com.ra.service.impl;

import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.Role;
import com.ra.model.entity.Users;
import com.ra.repository.UserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrinciple;
import com.ra.service.RoleService;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public UserResponse handleLogin(UserLogin userLogin) {
        Authentication authentication;
        try {
            authentication = authenticationProvider.
                    authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),userLogin.getPassword()));
        }catch (AuthenticationException exception){
            throw new RuntimeException("username or password Wrong");
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        //tao ra 1 token
        String token = jwtProvider.generateToken(userPrinciple);

        // Convert sang doi tuong UserResponse
        return UserResponse.builder().
                fullName(userPrinciple.getUser().getFullName()).
                id(userPrinciple.getUser().getId()).accessToken(token).
                status(userPrinciple.getUser().getStatus()).
                Roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public String handleRegister(UserRegister userRegister) {

        if (userRepository.existsByUserName(userRegister.getUsername())){
            throw new RuntimeException("username is exists");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName("ROLE_USER"));
        Users users = Users.builder()
                .fullName(userRegister.getFullName())
                .userName(userRegister.getUsername())
                .passWord(passwordEncoder.encode(userRegister.getPassword()))
                .status(true).
                roles(roles).
                build();
        userRepository.save(users);
        return "Register successful!";
    }
}
