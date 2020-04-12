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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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
    public String selectMonth(){ return "selectMonthForCreateSchedules";}
    @PostMapping()
    public String createScheduleForAll(String month){
        Iterable<User> users = userRepo.findAllByRole(User.RoleOfUser.ROLE_USER);
        for (User user : users) {
           createSchedule(user,month);
        }
        return "redirect:/schedule/search";
    }

    @GetMapping("/user")
    public String selectUserAndMonthForCreateSchedule(Model model){
        Iterable<User> allUsers = userRepo.findAllByRole(User.RoleOfUser.ROLE_USER);
        List<User> newUsers = new ArrayList<>();
        for(User user : allUsers){
            if(user.getSchedules().size()==0){
                newUsers.add(user);
            }
        }
        model.addAttribute("users", newUsers);
        return "selectUserAndMonthForCreateSchedule";
    }
    @PostMapping("/user")
    public String createScheduleForUser(String username, String month){
        User user = userRepo.findByUsername(username);
        createSchedule(user, month);
        return "redirect:/schedule/search";
    }

    public void createSchedule(User user, String month){
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

    @GetMapping("/list")
    public String selectMonthForMyList(){
        return "selectMonthForScheduleList";
    }
    @PostMapping("/list")
    public String showMyList(String month, Model model, @AuthenticationPrincipal User user){
        Schedule schedule = scheduleRepo.findByUserAndMonth(user, month);
        model.addAttribute("shifts", shiftRepo.findAllBySchedule(schedule));

        return "shiftsInSchedule";
    }

    @GetMapping("/search")
    public String selectUserAndMonthForScheduleList(Model model) {
        model.addAttribute("users", userRepo.findAllByRole(User.RoleOfUser.ROLE_USER));
        return "selectUserAndMonthForScheduleList";
    }
    @PostMapping("/search")
    public String showScheduleListForUser(String username, String month, Model model){
        User user = userRepo.findByUsername(username);
        Schedule schedule = scheduleRepo.findByUserAndMonth(user,month);
        model.addAttribute("name", user.getFirstAndLastName());
        model.addAttribute("month", month);
        model.addAttribute("shifts", shiftRepo.findAllBySchedule(schedule));
        return "shiftsInScheduleForAdmin";
    }

   @GetMapping("/shift/update/{id}")
    public String selectNewShiftType(@PathVariable("id") long id, Model model){
        Shift shift = shiftRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException());
        model.addAttribute("shift", shift);
        return "updateShift";
    }
   @PostMapping("/shift/update/{id}")
    public String updateShift(@PathVariable("id") long id, String type, Model model){
        Shift shift = shiftRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException());
        shift.setType(Shift.TypeOfShift.valueOf(type));
        shiftRepo.save(shift);
        Schedule schedule = shift.getSchedule();
        model.addAttribute("shifts", shiftRepo.findAllBySchedule(schedule));
        model.addAttribute("month", schedule.getMonth());
        model.addAttribute("name", schedule.getUser().getFirstAndLastName());
        return "shiftsInScheduleForAdmin";
    }

    @GetMapping("/update")
    public String selectUserAndMonthFotUpdateSchedule(Model model){
        Iterable<User> allUsers = userRepo.findAllByRole(User.RoleOfUser.ROLE_USER);
        List<User> usersWithSchedule = new ArrayList<>();
        for(User user : allUsers){
            if(user.getSchedules().size()!=0){
                usersWithSchedule.add(user);
            }
        }
        model.addAttribute("users", usersWithSchedule);
        return "selectUserAndMonthForUpdateSchedule";
    }
    @PostMapping("/update")
    public String updateSchedule(String username, String month){
        Schedule schedule = scheduleRepo.findByUserAndMonth(userRepo.findByUsername(username),month);
        Iterable<Shift> shifts = shiftRepo.findAllBySchedule(schedule);
        for(Shift shift :  shifts){
            shift.setType(Shift.generateTypeOfShift());
            shiftRepo.save(shift);
        }
        return "redirect:/schedule/search";
    }

    @GetMapping("/delete")
    public String selectUserAndMonthForDeleteSchedule(Model model){
        Iterable<User> allUsers = userRepo.findAllByRole(User.RoleOfUser.ROLE_USER);
        List<User> usersWithSchedule = new ArrayList<>();
        for(User user : allUsers){
            if(user.getSchedules().size()!=0){
                usersWithSchedule.add(user);
            }
        }
        model.addAttribute("users", usersWithSchedule);
        return "selectUserAndMonthForDeleteSchedule";
    }
    @PostMapping("/delete")
    public String deleteSchedule(String username, String month){
        User user = userRepo.findByUsername(username);
        Schedule schedule = scheduleRepo.findByUserAndMonth(user,month);
        Iterable<Shift> shifts = shiftRepo.findAllBySchedule(schedule);
        for(Shift shift : shifts){
            shiftRepo.delete(shift);
        }
        scheduleRepo.delete(schedule);
        return"redirect:/start";
    }

}
