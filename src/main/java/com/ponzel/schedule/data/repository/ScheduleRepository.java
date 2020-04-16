package com.ponzel.schedule.data.repository;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    Iterable<Schedule> findAllByUser(User user);
    Iterable<Schedule> findAllByMonth(String month);
    Schedule findByUserAndMonth(User user, String month);
}
