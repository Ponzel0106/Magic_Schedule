package com.ponzel.schedule.controllers;

import com.ponzel.schedule.User;
import com.ponzel.schedule.data.service.WishService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishes")
public class WishController {

    private WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping()
    public String getAllWishes(Model model){
        model.addAttribute("wishes", wishService.getAllWishes());
        return "wishList";
    }

    @GetMapping("/myWishes")
    public String getAllMyWishes(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("wishes", wishService.getAllWishes(user));
        return "myWishList";
    }

    @GetMapping("/create")
    public String selectMonthDayTypeForCreateWish(){
        return "selectMonthDayTypeForCreateWish";
    }
    @PostMapping("/create")
    public String createWish(@AuthenticationPrincipal User user, String month, int day, String type){
        if(!wishService.isExists(user, month, day)) {
            wishService.create(user, month, day, type);
        }
        return"redirect:/wishes/myWishes";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") long id, @AuthenticationPrincipal User user){
        wishService.delete(id);
        return user.isAdmin() ? "redirect:/wishes" : "redirect:/wishes/myWishes";
    }
}
