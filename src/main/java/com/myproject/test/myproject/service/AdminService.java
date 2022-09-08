package com.myproject.test.myproject.service;


import com.myproject.test.myproject.entity.Admin;
import com.myproject.test.myproject.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    String register(Admin admin, User user, MultipartFile photo);

    Admin update(Admin admin, MultipartFile photo);

    String updatePhoto(MultipartFile photo, String userName);

    Admin getByUserName(String userName);

}
