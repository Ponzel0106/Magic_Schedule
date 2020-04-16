package com.ponzel.schedule.data.service;

import com.ponzel.schedule.User;

import java.util.List;

public interface UserService {
    User getUser(String username);
    Iterable<User> getAllUsers(User.RoleOfUser roleOfUser);
    List<User> getAllUsersWithSchedules();
    void delete(long id);

}
