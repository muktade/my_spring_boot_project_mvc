package com.myproject.test.myproject.service;

import com.myproject.test.myproject.entity.Admin;
import com.myproject.test.myproject.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    String registerUser(User user, String roleName);

    String userRegister(User user, MultipartFile photo);


    String updateUser(User user, String password);

    User findByUserName(String userName);

    String getUserName();

    Integer delete(String userName);

    Integer deactivate(String userName);

    User getByUserName(String userName);
}
