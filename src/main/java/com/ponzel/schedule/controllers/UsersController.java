package com.ponzel.schedule.controllers;

import com.ponzel.schedule.User;
import com.ponzel.schedule.data.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsersList(Model model){
        model.addAttribute("users", userService.getAllUsers(User.RoleOfUser.ROLE_USER));
        return "userList";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model){
        userService.delete(id);
        model.addAttribute("users",userService.getAllUsers(User.RoleOfUser.ROLE_USER));
        return "userList";
    }

}
