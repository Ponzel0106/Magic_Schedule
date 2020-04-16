package com.ponzel.schedule.controllers;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.Shift;
import com.ponzel.schedule.User;
import com.ponzel.schedule.data.service.ScheduleService;
import com.ponzel.schedule.data.service.ShiftService;
import com.ponzel.schedule.data.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private UserService userService;
    private ScheduleService scheduleService;
    private ShiftService shiftService;

    public ScheduleController(ScheduleService scheduleService, UserService userService, ShiftService shiftService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.shiftService = shiftService;
    }

    @GetMapping()
    public String selectMonth(){ return "selectMonthForCreateSchedules";}
    @PostMapping()
    public String createScheduleForAll(String month){
        List<Schedule> schedules = (List<Schedule>) scheduleService.getAllSchedules(month);
        if(!schedules.isEmpty()) return "scheduleIsAlreadyExists";
        for (User user : userService.getAllUsers(User.RoleOfUser.ROLE_USER)) {
            scheduleService.create(user,month);
        }
        return "redirect:/schedule/search";
    }

    @GetMapping("/user")
    public String selectUserAndMonthForCreateSchedule(Model model){
        Iterable<User> allUsers = userService.getAllUsers(User.RoleOfUser.ROLE_USER);
        List<User> newUsers = new ArrayList<>();
        for(User user : allUsers){
            if(user.getSchedules().isEmpty()){
                newUsers.add(user);
            }
        }
        model.addAttribute("users", newUsers);
        return "selectUserAndMonthForCreateSchedule";
    }
    @PostMapping("/user")
    public String createScheduleForUser(String username, String month){
        scheduleService.create(userService.getUser(username), month);
        return "redirect:/schedule/search";
    }


    @GetMapping("/list")
    public String selectMonthForMyList(){
        return "selectMonthForScheduleList";
    }
    @PostMapping("/list")
    public String showMyList(String month, Model model, @AuthenticationPrincipal User user){
        Schedule schedule = scheduleService.getSchedule(user, month);
        model.addAttribute("shifts", shiftService.getShifts(schedule));
        return "shiftsInSchedule";
    }

    @GetMapping("/search")
    public String selectUserAndMonthForScheduleList(Model model) {
        model.addAttribute("users", userService.getAllUsers(User.RoleOfUser.ROLE_USER));
        return "selectUserAndMonthForScheduleList";
    }
    @PostMapping("/search")
    public String showScheduleListForUser(String username, String month, Model model){
        User user = userService.getUser(username);
        Schedule schedule = scheduleService.getSchedule(user,month);
        model.addAttribute("name", user.getFirstAndLastName());
        model.addAttribute("month", month);
        model.addAttribute("shifts", shiftService.getShifts(schedule));
        return "shiftsInScheduleForAdmin";
    }

   @GetMapping("/shift/update/{id}")
    public String selectNewShiftType(@PathVariable("id") long id, Model model){
        model.addAttribute("shift", shiftService.getShift(id));
        return "updateShift";
    }
   @PostMapping("/shift/update/{id}")
    public String updateShift(@PathVariable("id") long id, String type, Model model){
        Shift shift = shiftService.getShift(id);
        shiftService.update(shift);
        Schedule schedule = shift.getSchedule();
        model.addAttribute("shifts", shiftService.getShifts(schedule));
        model.addAttribute("month", schedule.getMonth());
        model.addAttribute("name", schedule.getUser().getFirstAndLastName());
        return "shiftsInScheduleForAdmin";
    }

    @GetMapping("/update")
    public String selectUserAndMonthForUpdateSchedule(Model model){
        model.addAttribute("users", userService.getAllUsersWithSchedules());
        return "selectUserAndMonthForUpdateSchedule";
    }
    @PostMapping("/update")
    public String updateSchedule(String username, String month){
        Schedule schedule = scheduleService.getSchedule(userService.getUser(username), month);
        if(schedule == null) return "scheduleIsNotExists";
        for(Shift shift : shiftService.getShifts(schedule)){
            shiftService.update(shift);
        }
        return "redirect:/schedule/search";
    }

    @GetMapping("/delete")
    public String selectUserAndMonthForDeleteSchedule(Model model){
        model.addAttribute("users", userService.getAllUsersWithSchedules());
        return "selectUserAndMonthForDeleteSchedule";
    }
    @PostMapping("/delete")
    public String deleteSchedule(String username, String month){
        User user = userService.getUser(username);
        Schedule schedule = scheduleService.getSchedule(user, month);
        if(schedule == null) return "scheduleIsNotExists";
        scheduleService.delete(schedule);
        return"redirect:/schedule/search";
    }

}
