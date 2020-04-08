package com.ponzel.schedule.data;

import com.ponzel.schedule.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    Iterable<User> findAllByRole(User.RoleOfUser role);

}
