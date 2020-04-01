package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
