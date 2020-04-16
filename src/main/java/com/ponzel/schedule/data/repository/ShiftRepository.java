package com.ponzel.schedule.data.repository;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.Shift;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends CrudRepository<Shift, Long> {
    Iterable<Shift> findAllBySchedule(Schedule schedule);
}
