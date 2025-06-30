package com.hgo_soft.device_for_all.repository;

import com.hgo_soft.device_for_all.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
