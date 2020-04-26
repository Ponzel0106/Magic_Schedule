package com.ponzel.schedule.data.service;

import com.ponzel.schedule.Shift;
import com.ponzel.schedule.User;
import com.ponzel.schedule.Wish;
import com.ponzel.schedule.data.repository.WishRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation service`s methods to work with table "wish" in DB "schedule"
 */
@Service
public class WishServiceImp implements WishService {

    private WishRepository wishRepo;

    public WishServiceImp(WishRepository wishRepo) {
        this.wishRepo = wishRepo;
    }

    @Override
    public void create(User user, String month, int day, String type) {
        Wish wish = new Wish();
        wish.setUser(user);
        wish.setMonth(month);
        wish.setDay(day);
        wish.setType(Shift.TypeOfShift.valueOf(type));
        wishRepo.save(wish);
    }

    @Override
    public Wish getWish(User user, String month, int day) {
        return wishRepo.findByUserAndMonthAndDay(user, month, day);
    }

    @Override
    public Iterable<Wish> getAllWishes() {
        return wishRepo.findAll();
    }

    @Override
    public Iterable<Wish> getAllWishes(User user) {
        return wishRepo.findAllByUser(user);
    }

    @Override
    public Boolean isExists(User user, String month, int day) {
        return wishRepo.findByUserAndMonthAndDay(user, month, day) != null;
    }

    @Override
    public void delete(long id) {
        Wish wish = wishRepo.findById(id)
                            .orElseThrow(()-> new IllegalArgumentException("Wish with id:"+id+" is not exists"));
        wishRepo.delete(wish);
    }

    @Override
    public void delete(Wish wish) {
        wishRepo.delete(wish);
    }
}
