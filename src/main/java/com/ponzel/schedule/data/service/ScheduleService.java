package com.ponzel.schedule.data.service;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.User;

public interface ScheduleService {
    void create(User user, String month);
    Iterable<Schedule> getAllSchedules(String month);
    Iterable<Schedule> getAllSchedules(User user);
    Schedule getSchedule(User user, String month);
    void delete(Schedule schedule);
}
