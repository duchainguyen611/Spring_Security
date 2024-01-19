package com.ra.service.UserAndRole;

import com.ra.model.entity.ENUM.RoleName;
import com.ra.model.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleService {
    Role findByRoleName(RoleName roleName);

    List<Role> getAll();

}
