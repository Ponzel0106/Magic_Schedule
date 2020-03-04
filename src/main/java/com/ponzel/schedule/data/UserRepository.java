package com.ponzel.schedule.data;

import com.ponzel.schedule.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
