package com.ra.service.user;

import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.request.UserToUpdateInfor;
import com.ra.model.dto.request.UserToUpdatePassword;
import com.ra.model.dto.response.UserResponseToAdmin;
import com.ra.model.dto.response.UserResponseToUser;
import com.ra.model.dto.response.UserResponseToLogin;
import com.ra.model.entity.ENUM.RoleName;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.UserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.user_principal.UserPrinciple;
import com.ra.service.role.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    public UserResponseToLogin handleLogin(UserLogin userLogin) {
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
        return UserResponseToLogin.builder().
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
    }

    private final Logger logger = LoggerFactory.getLogger(UserServiceIMPL.class);
    @Override
    public User userLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return findById(userPrinciple.getUser().getId());
        } else {
            logger.error("User - UserController - User id is not found.");
            return null;
        }
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
    public Page<UserResponseToAdmin> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAllByUser(pageable);
        return users.map(this::displayUserToAdmin);
    }

    @Override
    public List<UserResponseToAdmin> findByKeyWord(String KeyWord) {
        List<User> users = userRepository.findAllByUserName(KeyWord);
        return users.stream()
                .map(this::displayUserToAdmin)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User inforLoginAcc() {
        return userRepository.findById(Objects.requireNonNull(userLogin()).getId()).orElse(null);
    }

    @Override
    public UserResponseToUser displayInforAcc() {
        User user = inforLoginAcc();
        return displayUserToUser(user);
    }

    @Override
    public User updateInforAcc(UserToUpdateInfor userToUpdateInfor) {
        User user = inforLoginAcc();
        user.setFullName(userToUpdateInfor.getFullName());
        user.setEmail(userToUpdateInfor.getEmail());
        user.setAvatar(userToUpdateInfor.getAvatar());
        user.setPhone(userToUpdateInfor.getPhone());
        user.setAddress(userToUpdateInfor.getAddress());
        return save(user);
    }

    @Override
    public void updatePasswordAcc(UserToUpdatePassword userToUpdatePassword) {
        User user = inforLoginAcc();
        if (!passwordEncoder.matches(userToUpdatePassword.getOldPassword(), user.getPassWord())) {
            logger.error("Old password not true!");
            throw new RuntimeException();
        } else if (userToUpdatePassword.getNewPassword().equals(userToUpdatePassword.getOldPassword())) {
            logger.error("New password must be different Old password!");
            throw new RuntimeException();
        } else if (!userToUpdatePassword.getNewPassword().equals(userToUpdatePassword.getNewPasswordConfirm())) {
            logger.error("New password confirm must like new password!");
            throw new RuntimeException();
        }
        user.setPassWord(passwordEncoder.encode(userToUpdatePassword.getNewPassword()));
        save(user);
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
    public UserResponseToAdmin displayUserToAdmin(User user) {
        return UserResponseToAdmin.builder()
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

    public UserResponseToUser displayUserToUser(User user) {
        return UserResponseToUser.builder()
                .fullName(user.getFullName())
                .userName(user.getUserName())
                .Email(user.getEmail())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }

}
