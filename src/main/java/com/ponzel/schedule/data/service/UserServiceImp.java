package com.ponzel.schedule.data.service;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.User;
import com.ponzel.schedule.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepo;
    private ScheduleService scheduleService;

    public UserServiceImp(UserRepository userRepo, ScheduleService scheduleService) {
        this.userRepo = userRepo;
        this.scheduleService = scheduleService;
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Iterable<User> getAllUsers(User.RoleOfUser roleOfUser) {
        return userRepo.findAllByRole(roleOfUser);
    }

    @Override
    public List<User> getAllUsersWithSchedules() {
        Iterable<User> users = userRepo.findAllByRole(User.RoleOfUser.ROLE_USER);
        List<User> usersWithSchedules = new LinkedList<>();
        for(User user : users){
            if(!user.getSchedules().isEmpty()) usersWithSchedules.add(user);
        }
        return usersWithSchedules;
    }

    @Override
    public void delete(long id) {
        User user = userRepo.findById(id)
                .orElseThrow(()->new IllegalArgumentException("No User with id:"+id));
        for(Schedule schedule :  scheduleService.getAllSchedules(user)){
            scheduleService.delete(schedule);
        }
        userRepo.delete(user);
    }
}
