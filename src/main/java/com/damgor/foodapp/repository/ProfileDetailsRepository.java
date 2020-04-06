package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.Profile;
import com.damgor.foodapp.model.ProfileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDetailsRepository extends JpaRepository<ProfileDetails, Long> {
}
