package com.myproject.test.myproject.service;

import com.myproject.test.myproject.entity.User;

public interface UserService {

    String registerUser(User user, String roleName);

    String updateUser(User user, String password);

    User findByUserName(String userName);

    String getUserName();

    Integer delete(String userName);

    Integer deactivate(String userName);
}
