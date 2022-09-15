package com.myproject.test.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageControler {


    @GetMapping("home")
    public String homePagge(HttpSession session){
        if(session.getAttribute("userName")!= null){
            System.out.println("name: " +session.getAttribute("userName"));
        }

        return "html/index";
    }
}
