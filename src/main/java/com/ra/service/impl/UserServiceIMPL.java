package com.ra.service.impl;

import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.JwtResponse;
import com.ra.model.entity.Role;
import com.ra.model.entity.Users;
import com.ra.repository.UserRepository;
import com.ra.service.RoleService;
import com.ra.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;
    @Override
    public JwtResponse handleLogin(UserLogin userLogin) {
        return null;
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
        return "Success";
    }
}
