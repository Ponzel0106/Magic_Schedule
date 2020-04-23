package com.ponzel.schedule.data.service;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.Shift;
import com.ponzel.schedule.data.repository.ShiftRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation service`s methods to work with table "shift" in DB "schedule"
 */
@Service
public class ShiftServiceImp implements ShiftService {

    private ShiftRepository shiftRepo;

    public ShiftServiceImp(ShiftRepository shiftRepo) {
        this.shiftRepo = shiftRepo;
    }

    @Override
    public void create(Schedule schedule, int day) {
        Shift shift = new Shift();
        shift.setSchedule(schedule);
        shift.setDay(day);
        shift.setType(Shift.generateTypeOfShift());
        shiftRepo.save(shift);
    }

    @Override
    public Shift update(Shift shift) {
        shift.setType(Shift.generateTypeOfShift());
        shiftRepo.save(shift);
        return shift;
    }

    @Override
    public Shift update(Shift shift, Shift.TypeOfShift typeOfShift) {
        shift.setType(typeOfShift);
        shiftRepo.save(shift);
        return shift;
    }

    @Override
    public Shift getShift(long id) {
        return shiftRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("No shift with id:"+id));
    }

    @Override
    public Iterable<Shift> getShifts(Schedule schedule) {
        return shiftRepo.findAllBySchedule(schedule);
    }


    @Override
    public void delete(Schedule schedule) {
        for(Shift shift : shiftRepo.findAllBySchedule(schedule)){
            shiftRepo.delete(shift);
        }
    }
}
