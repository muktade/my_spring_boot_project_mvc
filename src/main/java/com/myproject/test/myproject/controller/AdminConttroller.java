package com.myproject.test.myproject.controller;

//import javax.validation.Valid;
import com.myproject.test.myproject.entity.Admin;
import com.myproject.test.myproject.entity.User;
import com.myproject.test.myproject.service.AdminService;
import com.myproject.test.myproject.service.UserService;
import com.myproject.test.myproject.utils.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.swing.text.html.CSS.getAttribute;

@Controller
@RequestMapping("/admin/")
public class AdminConttroller {
    @Autowired
    private AdminService adminService;

    @Autowired
    private DataService dataService;

    @Autowired
    private ModuleController moduleController;

    @GetMapping("register")
    public String registerForm(Model model){
        moduleController.formModel(model,"register", new Admin(), "/admin/register", null);
        List<String> role = new ArrayList<>();
        role.add("admin");
        role.add("user");
//        options.add("option 3");
        model.addAttribute("options", role);
        return "/user_registation";

    }

    @PostMapping("register")
    public String register(Admin admin, BindingResult result, @RequestParam("photo") MultipartFile photo, HttpServletRequest request, Model model){
        String message ="Please insert All the information";
        System.out.println("register");
        System.out.println(result);
        if(!result.hasErrors()){
            User user = getUserFromRequest(request);
            System.out.println(user);
            message = adminService.register(admin, user,photo);
            switch (message){
                case "0":
                    message = "Photo is not save";
                    break;
                case "1":
                    message= "Registration successful";
                    break;
            }
        }
        moduleController.formModel(model, "register", admin, "/admin/register", message);
        return "/html/index";
    }

    @GetMapping("registeruser")
    public String registerUserForm(Model model){
        moduleController.formModel(model,"register", new User(), "/admin/registeruser", null);
        return "/user_registation";

    }
    @Autowired
    private UserService userService;
    @PostMapping("registeruser")
    public String registerUser(User user, BindingResult result, @RequestParam("image") MultipartFile photo, HttpServletRequest request, Model model){
        String message ="Please insert All the information";
        if(!result.hasErrors()){
//            User user = getUserFromUserRequest(request);
            message = userService.registerUser(user,"USER");
            switch (message){
                case "0":
                    message = "Photo is not save";
                    break;
                case "1":
                    message= "Registration successful";
                    break;
            }
        }
        moduleController.formModel(model, "register", user, "/admin/registeruser", message);
        return "redirect:/html/index";
    }

    @GetMapping
    public String getAdminInfo(HttpSession session, Model model){
        Admin admin = adminService.getByUserName(getUserName());
        if(admin != null){
            Map<String, Object> data = dataService.getViewData(admin, "username", "isActive");
                moduleController.displayModel(model, "profile view", data, null);
                return "pages/details";
        }
        moduleController.formModel(model, "user_registation", new Admin(), "/admin/register","Please complete your registration");
       return "/html/index";
    }

    private String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    private User getUserFromRequest(HttpServletRequest request) {
        User user = new User();
        user.setUserName(request.getParameter("userName"));
        user.setEmail(request.getParameter("email"));
        user.setUserPassword(request.getParameter("userPassword"));
//        user.setPhoto(request.getParameter("photo"));
//        user.setUserBirth(request.getParameter("user_birth"));

        return user;
    }

    private User getUserFromUserRequest(HttpServletRequest request) {
        User user = new User();
        user.setUserName(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setUserPassword(request.getParameter("password"));
//        user.setPhoto(request.getParameter("photo"));
//        user.setUserBirth(request.getParameter("user_birth"));

        return user;
    }

}
