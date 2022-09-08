package com.myproject.test.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ModuleController {
    public void simpleModule(Model model, String module, String message){
        model.addAttribute("module", module);
        model.addAttribute("message",message);
    }

    public void displayModel()
}
