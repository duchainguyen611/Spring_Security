package com.ra.service;

import com.ra.model.entity.Role;
import org.springframework.stereotype.Service;

public interface RoleService {
    Role findByRoleName(String roleName);
}
