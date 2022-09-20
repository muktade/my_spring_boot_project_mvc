package com.myproject.test.myproject.controller;


import com.myproject.test.myproject.dao.RoleDao;
import com.myproject.test.myproject.entity.Admin;
import com.myproject.test.myproject.entity.Role;
import com.myproject.test.myproject.entity.User;
import com.myproject.test.myproject.service.AdminService;
import com.myproject.test.myproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RoleDao roleDao;

    @Autowired
    AdminService adminService;

    @Autowired
    public ModuleController moduleController;


    @GetMapping("/login")
    public String loginPage(String message, String error, String logout, Model model){
        if(error != null){
            model.addAttribute("error", "Invalid User name or Password");
        }
        if(logout != null){
            message = "You have been logged out successfully";
        }
        List<Role>  roles = roleDao.findAll();
        moduleController.multiChoiceFormModule(model,"user_login",new User(), "/login", roles, message);
        return "user_login";
//        return "user_login";
    }

    @PostMapping(value = {"/loginsuccess", "/loginsuccess/{message}"})
    public String loginsSuccess(@PathVariable(value = "message", required = false) String message, Model model,
                                HttpSession session){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String page ="";
        String roleAdmin= "ROLE_ADMIN";
        String roleManager= "ROLE_MANAGER";
        String roleUser= "ROLE_USER";
        for (GrantedAuthority authority : authorities){
            System.out.println("role: "+authority.getAuthority());

            if(authority.getAuthority().equals(roleAdmin)){
                Admin admin = adminService.getByUserName(username);
                if(admin != null){
                    session.setAttribute("userName", admin.getUserName());
                    session.setAttribute("userRole", authority.getAuthority());
                    session.setAttribute("userId", admin.getUserId());
                    session.setAttribute("userPhoto", admin.getPhoto());
                    page = "redirect :/html/index";
                }
                else {
                    moduleController.formModel(model,"admin-register", new Admin(), "/admin/register",
                            "please complete your registration");
                    page = "redirect: /admin/register";
                }

            } else if (authority.getAuthority().equals(roleManager)) {
                ///ekhane Employee er kaj korte hobe
            } else if (authority.getAuthority().equals(roleUser)) {
                User user = userService.getByUserName(username);
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("userRole", user.getRoles());
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userPhoto", user.getPhoto());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userBirth", user.getUserBirth());
                session.setAttribute("userPassword", user.getUserPassword());
                page="redirect: /html/index";
            }
            else {
                moduleController.formModel(model,"admin-register", new User(), "/admin/register",
                        "please complete your user registration");
                page = "redirect: /admin/register";
            }
        }
        return page;
    }
}

