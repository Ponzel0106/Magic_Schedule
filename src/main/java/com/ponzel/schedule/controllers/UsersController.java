package com.ponzel.schedule.controllers;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.Shift;
import com.ponzel.schedule.User;
import com.ponzel.schedule.data.ScheduleRepository;
import com.ponzel.schedule.data.ShiftRepository;
import com.ponzel.schedule.data.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    private UserRepository userRepo;
    private ScheduleRepository scheduleRepo;
    private ShiftRepository shiftRepo;

    public UsersController(UserRepository userRepo, ScheduleRepository scheduleRepo, ShiftRepository shiftRepo) {
        this.userRepo = userRepo;
        this.scheduleRepo = scheduleRepo;
        this.shiftRepo = shiftRepo;
    }

    @GetMapping
    public String getUsersList(Model model){
        model.addAttribute("users", userRepo.findAllByRole(User.RoleOfUser.ROLE_USER));
        return "userList";
    }

    @GetMapping("/delete/{id}")
    public String deleteUse(@PathVariable("id") long id, Model model){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException());
        Iterable<Schedule> schedules = scheduleRepo.findAllByUser(user);
        for(Schedule schedule : schedules){
            Iterable<Shift> shifts = shiftRepo.findAllBySchedule(schedule);
            for (Shift shift : shifts){
                shiftRepo.delete(shift);
            }
            scheduleRepo.delete(schedule);
        }
        userRepo.delete(user);
        model.addAttribute("users",userRepo.findAllByRole(User.RoleOfUser.ROLE_USER));
        return "userList";
    }

}
