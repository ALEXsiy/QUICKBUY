package com.baturin.QUICKbuy.controllers;

import com.baturin.QUICKbuy.models.User;
import com.baturin.QUICKbuy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String createUser(User user, Model model){
        if(!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует!");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/hello")
    public String securityUrl(){
        return "hello";
    }

    @GetMapping("/user/{user}")
    public String userinfo(@PathVariable("user") User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("products",user.getProducts());
        return "user-info";
    }

}
