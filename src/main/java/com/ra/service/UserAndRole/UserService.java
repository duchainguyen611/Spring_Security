package com.ra.service.UserAndRole;

import com.ra.model.dto.request.ProductRequest;
import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.UserInforToDisplay;
import com.ra.model.dto.response.UserResponse;
import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {
    UserResponse handleLogin(UserLogin userLogin);
    String handleRegister(UserRegister userRegister);
    Page<UserInforToDisplay> getAll(Pageable pageable);
    Page<UserInforToDisplay> findByKeyWordName(Pageable pageable,String keyWord);
    User findById(Long id);
    User save(User user);
    void delete(Long id);
    UserInforToDisplay displayUser(User user);
}
