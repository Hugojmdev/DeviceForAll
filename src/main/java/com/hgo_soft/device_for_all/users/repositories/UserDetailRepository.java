package com.hgo_soft.device_for_all.users.repositories;

import com.hgo_soft.device_for_all.users.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
