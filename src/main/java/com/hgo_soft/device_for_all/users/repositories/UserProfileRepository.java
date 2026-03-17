package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.users.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
