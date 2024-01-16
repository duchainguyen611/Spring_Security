package com.ra.service.impl;

import com.ra.model.entity.Role;
import com.ra.repository.RoleRepository;
import com.ra.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceIMPL implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(String roleName) {
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(()->new RuntimeException("Role not found"));
        return role;
    }
}
