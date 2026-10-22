package com.hgo_soft.device_for_all.auth.repositories;

import com.hgo_soft.device_for_all.auth.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);
    List<Permission> findAllByNameIn(Collection<String> names);
}
