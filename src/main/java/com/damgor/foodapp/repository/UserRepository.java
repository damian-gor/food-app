package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUserName (String userName);
    @Query("select u.id from User u WHERE u.userName =?1")
    long getUserIdByUserName (String userName);
}
