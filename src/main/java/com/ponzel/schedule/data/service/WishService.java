package com.ponzel.schedule.data.service;

import com.ponzel.schedule.User;
import com.ponzel.schedule.Wish;

public interface WishService {
    void create(User user, String month, int day, String type);
    Wish getWish(User user, String month, int day);
    Iterable<Wish> getAllWishes();
    Iterable<Wish> getAllWishes(User user);
    Boolean isExists(User user, String month, int day);
    void delete(long id);
    void delete (Wish wish);

}
