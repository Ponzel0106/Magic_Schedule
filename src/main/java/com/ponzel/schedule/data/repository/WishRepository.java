package com.ponzel.schedule.data.repository;

import com.ponzel.schedule.User;
import com.ponzel.schedule.Wish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends CrudRepository<Wish, Long> {
    Iterable<Wish> findAllByUser(User user);
    Wish findByUserAndMonthAndDay(User user, String month, int day);
}
