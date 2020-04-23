package com.ponzel.schedule.data.service;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.User;
import com.ponzel.schedule.data.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.Month;

/**
 * Implementation service`s methods to work with table "schedule" in DB "schedule"
 */
@Service
public class ScheduleServiceImp implements ScheduleService {

    private ScheduleRepository scheduleRepo;
    private ShiftService shiftService;

    public ScheduleServiceImp(ScheduleRepository scheduleRepo, ShiftService shiftService) {
        this.scheduleRepo = scheduleRepo;
        this.shiftService = shiftService;
    }

    @Override
    public void create(User user, String month) {
        Schedule schedule = new Schedule();
        schedule.setUser(user);
        schedule.setMonth(month);
        scheduleRepo.save(schedule);
        for(int day = 1; day<= Month.valueOf(month).length(false); day++) {
            shiftService.create(schedule, day);
        }
    }

    @Override
    public Iterable<Schedule> getAllSchedules(String month) {
        return scheduleRepo.findAllByMonth(month);
    }

    @Override
    public Iterable<Schedule> getAllSchedules(User user) {
        return scheduleRepo.findAllByUser(user);
    }

    @Override
    public Schedule getSchedule(User user, String month) {
        return scheduleRepo.findByUserAndMonth(user, month);
    }

    @Override
    public void delete(Schedule schedule) {
        shiftService.delete(schedule);
        scheduleRepo.delete(schedule);
    }
}
