package com.myproject.test.myproject.controller;


import com.myproject.test.myproject.entity.User;
import com.myproject.test.myproject.service.AdminService;
import com.myproject.test.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("update")
    public String updateForm(HttpSession session, Module module){
        String userName= getUserName();
        User currentUser = userService.findByUserName(userName);
        if(currentUser == null){
            return "redirect : invalidate";
        }
        else {

        }
    }


    private String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
