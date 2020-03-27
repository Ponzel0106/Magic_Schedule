package com.ponzel.schedule.controllers;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.Shift;
import com.ponzel.schedule.User;
import com.ponzel.schedule.data.ScheduleRepository;
import com.ponzel.schedule.data.ShiftRepository;
import com.ponzel.schedule.data.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Month;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private UserRepository userRepo;
    private ScheduleRepository scheduleRepo;
    private ShiftRepository shiftRepo;

    public ScheduleController(UserRepository userRepo, ScheduleRepository scheduleRepo, ShiftRepository shiftRepo) {
        this.userRepo = userRepo;
        this.scheduleRepo = scheduleRepo;
        this.shiftRepo = shiftRepo;
    }

    @GetMapping()
    public String selectMonth(){ return "month";}

    @PostMapping()
    public String createSchedule(String month){
       Iterable<User> users = userRepo.findAll();
        for (User user : users) {
            Schedule schedule = new Schedule();
            schedule.setMonth(month);
            schedule.setUser(user);
            scheduleRepo.save(schedule);


            for(int i = 1; i <= Month.valueOf(schedule.getMonth()).length(false); i++){
                Shift shift = new Shift();
                shift.setDay(i);
                shift.setSchedule(schedule);
                shift.setType(Shift.generateTypeOfShift());
                shiftRepo.save(shift);
            }
        }
        return "/home";
    }

    @GetMapping("/list")
    public String selectMonthForList(){
        return "monthForScheduleList";
    }
    @PostMapping("/list")
    public String showList(String month, Model model, @AuthenticationPrincipal User user){
        Schedule schedule = scheduleRepo.findByUserAndMonth(user, month);
        model.addAttribute("shifts", shiftRepo.findAll());

        return "shiftsInSchedule";
    }
}
