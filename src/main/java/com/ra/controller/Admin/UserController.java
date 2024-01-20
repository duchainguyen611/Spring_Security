package com.ra.controller.Admin;

import com.ra.model.dto.response.UserInforToDisplay;

import com.ra.model.entity.User;
import com.ra.service.UserAndRole.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "5", name = "limit") int limit,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "userName", name = "sort")String sort) {
        Pageable pageable = PageRequest.of(page,limit, Sort.by(sort));
        Page<UserInforToDisplay> users = userService.getAll(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fixStatusUser(@PathVariable("id") Long id){
        User userSearch = userService.findById(id);
        userSearch.setStatus(!userSearch.getStatus());
        User userUpdate = userService.save(userSearch);
        return new ResponseEntity<>(userUpdate,HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchUser(@PathVariable("keyword") String keyword){
        List<UserInforToDisplay> users = userService.findByKeyWord(keyword);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
