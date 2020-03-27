package com.ponzel.schedule.data;

import com.ponzel.schedule.Schedule;
import com.ponzel.schedule.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    Schedule findByUserAndMonth(User user, String month);
}
