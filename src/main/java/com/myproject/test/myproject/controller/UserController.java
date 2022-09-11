package com.myproject.test.myproject.controller;


import com.myproject.test.myproject.entity.User;
import com.myproject.test.myproject.service.AdminService;
import com.myproject.test.myproject.service.UserService;
import com.myproject.test.myproject.utils.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ModuleController moduleController;

    @Autowired
    private DataService dataService;

    @GetMapping("update")
    public String updateForm(HttpSession session, Model model){
        String userName= getUserName();
        User currentUser = userService.findByUserName(userName);
        if(currentUser == null){
            return "redirect : invalidate";
        }

        else {moduleController.formModel(model, "Account update", currentUser, "/user/update", null);
            return "forms/user";
        }
    }

    @PostMapping("update")
    public String updateUser(@RequestParam("oldPassword") String oldPasswor, User user, Model model){
        String message= userService.updateUser(user,oldPasswor);
        String output = userService.updateUser(user,oldPasswor);
        switch (output){
            case "11":

                moduleController.simpleModule(model, "Account update info","Account update successful");
                return "pages/details";
//                break;
        }
        moduleController.formModel(model,"account update", user, "/user/update", message);
        return "forms/user";
    }

    @GetMapping("invalidate")
    public String invalidate(Model model, HttpSession session){
        String username = getUserName();
        userService.deactivate(username);
        return "redirect : /logout";
    }

    @GetMapping("profile")
    public String profile(Model model, HttpSession session){
        String userName = getUserName();
        String role = (String) session.getAttribute("userRole");
        Object profile = null;
        if(role.equals("ROLE_ADMIN")){
            profile = adminService.getByUserName(userName);

        }
//        else if (role.equals("ROLE_MANAGER")) {
//            profile = this.employeeService.getByUsername(username);
//        } else if (role.equals("ROLE_STUDENT")) {
//            profile = this.studentService.getByUsername(username);
//        }
        else if (role.equals("ROLE_USER")) {
            profile=userService.getByUserName(userName);
        }
        Map<String , Object> data = dataService.getViewData(profile, "Username", "isActive");
        moduleController.displayModel(model,"profile view" , data, null);
        return "pages/details";
    }



    private String getUserName(){

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
