package com.ponzel.schedule.controllers;

import com.ponzel.schedule.User;
import com.ponzel.schedule.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public String getUsersList(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }

    @GetMapping("/delete/{id}")
    public String deleteUse(@PathVariable("id") long id, Model model){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException());
        userRepo.delete(user);
        model.addAttribute("users",userRepo.findAll());
        return "userList";
    }

}
