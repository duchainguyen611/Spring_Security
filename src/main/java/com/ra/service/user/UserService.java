package com.ra.service.user;


import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.request.UserToUpdateInfor;
import com.ra.model.dto.request.UserToUpdatePassword;
import com.ra.model.dto.response.UserResponseToAdmin;
import com.ra.model.dto.response.UserResponseToUser;
import com.ra.model.dto.response.UserResponseToLogin;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {
    UserResponseToLogin handleLogin(UserLogin userLogin);
    String handleRegister(UserRegister userRegister);
    Page<UserResponseToAdmin> getAll(Pageable pageable);
    List<UserResponseToAdmin> findByKeyWord(String keyWord);
    User findById(Long id);
    User inforLoginAcc();
    User updateInforAcc(UserToUpdateInfor userToUpdateInfor);
    void updatePasswordAcc(UserToUpdatePassword userToUpdatePassword);
    UserResponseToUser displayInforAcc ();
    User save(User user);

    User userLogin();
    void delete(Long id);
    UserResponseToAdmin displayUserToAdmin(User user);
    UserResponseToUser displayUserToUser(User user);
}
