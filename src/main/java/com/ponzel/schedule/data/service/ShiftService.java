package com.ponzel.schedule.data.service;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.Shift;

public interface ShiftService {
    void create(Schedule schedule, int day);
    Shift update(Shift shift);
    Shift update(Shift shift, Shift.TypeOfShift typeOfShift);
    Shift getShift(long id);
    Iterable<Shift> getShifts(Schedule schedule);
    void delete(Schedule schedule);
}
