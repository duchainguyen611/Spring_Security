package com.ra.model.dto.response;

import com.ra.model.entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String accessToken;
    private final String type = "Bearer";
    private String fullName;
    private String userName;
    private Boolean status;
    private String Email;
    private String avatar;
    private String phone;
    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Set<String> Roles;

}
