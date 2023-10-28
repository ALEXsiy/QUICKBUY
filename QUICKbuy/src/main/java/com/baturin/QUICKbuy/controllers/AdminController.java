package com.baturin.QUICKbuy.controllers;

import com.baturin.QUICKbuy.models.User;
import com.baturin.QUICKbuy.models.enums.Role;
import com.baturin.QUICKbuy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/admin")

    public String admin(Model model){
        model.addAttribute("users",userService.list());
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")
    public  String userBan(@PathVariable("id") Long id ){
        userService.banUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/admin/user/edit/{user}")
    public String userEdit(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/admin/user/edit")
    public String userEdit(@RequestParam("userId") User user, @RequestParam Map<String, String> form) {
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }
}
