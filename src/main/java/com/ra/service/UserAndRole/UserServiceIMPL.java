package com.ra.service.UserAndRole;

import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.UserInforToDisplay;
import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.ENUM.RoleName;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.UserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
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

    public static Long idUserLogin;

    @Override
    public UserResponse handleLogin(UserLogin userLogin) {
        Authentication authentication;
        try {
            authentication = authenticationProvider.
                    authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
        } catch (AuthenticationException exception) {
            throw new RuntimeException("Username or password Wrong");
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        if (!userPrinciple.getUser().getStatus()) {
            throw new RuntimeException("Your account is block!");
        }
        //tao ra 1 token
        String token = jwtProvider.generateToken(userPrinciple);
        // Convert sang doi tuong UserResponse
        UserResponse userResponse = UserResponse.builder().
                fullName(userPrinciple.getUser().getFullName()).
                id(userPrinciple.getUser().getId()).accessToken(token).
                status(userPrinciple.getUser().getStatus()).
                Email(userPrinciple.getUser().getEmail()).
                avatar(userPrinciple.getUser().getAvatar()).
                phone(userPrinciple.getUser().getPhone()).
                address(userPrinciple.getUser().getAddress()).
                createdAt(userPrinciple.getUser().getCreatedAt()).
                updatedAt(userPrinciple.getUser().getUpdatedAt()).
                Roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
        idUserLogin = userResponse.getId();
        return userResponse;
    }

    @Override
    public String handleRegister(UserRegister userRegister) {
        if (userRepository.existsByUserName(userRegister.getUsername())) {
            throw new RuntimeException("Username is exists");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        User users = User.builder()
                .fullName(userRegister.getFullName())
                .userName(userRegister.getUsername())
                .passWord(passwordEncoder.encode(userRegister.getPassword()))
                .email(userRegister.getEmail())
                .avatar(userRegister.getAvatar())
                .phone(userRegister.getPhone())
                .status(true)
                .createdAt(LocalDate.now())
                .address(userRegister.getAddress()).
                roles(roles).
                build();
        userRepository.save(users);
        return "Register successful!";
    }

    @Override
    public Page<UserInforToDisplay> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAllByUser(pageable);
        return users.map(this::displayUser);
    }

    @Override
    public List<UserInforToDisplay> findByKeyWord(String KeyWord) {
        List<User> users = userRepository.findAllByUserName(KeyWord);
        return users.stream()
                .map(this::displayUser)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserInforToDisplay displayUser(User user) {
        return UserInforToDisplay.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .userName(user.getUserName())
                .status(user.getStatus())
                .Email(user.getEmail())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .address(user.getAddress())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .Roles(user.getRoles().toString())
                .build();
    }

}
