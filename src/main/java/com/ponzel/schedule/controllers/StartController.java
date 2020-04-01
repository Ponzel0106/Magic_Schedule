package com.ponzel.schedule.controllers;

import com.ponzel.schedule.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/start")
public class StartController {

    @GetMapping()
    public String showUsersStartPAge(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("name", user.getFirstAndLastName());
        return "startUsersPage";
    }

    @GetMapping("/admin")
    public String showAdminsStartPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("name", user.getFirstAndLastName());
        return "startAdminsPage";
    }
}
