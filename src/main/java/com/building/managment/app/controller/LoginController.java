package com.building.managment.app.controller;

import com.building.managment.app.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String index() {
        return "dashBoard";
    }
    @PostMapping
    public String loginSuccess(User user, HttpSession session) {
        session.setAttribute("name", user.getName());
        return "dashBoard";
    }
}
