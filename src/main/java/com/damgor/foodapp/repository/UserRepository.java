package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUserName (String userName);
}
