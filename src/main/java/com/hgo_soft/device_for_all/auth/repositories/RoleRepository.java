package com.hgo_soft.device_for_all.auth.repositories;

import com.hgo_soft.device_for_all.auth.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
}
