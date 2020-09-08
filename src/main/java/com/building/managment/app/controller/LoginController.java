package com.building.managment.app.controller;

import com.building.managment.app.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("/index")
    public String index(){
        return "dashBoard";
    }
    @PostMapping
    public String loginSuccess(User user, Model model) {
        model.addAttribute("user", user);
        return "dashBoard";
    }
}
