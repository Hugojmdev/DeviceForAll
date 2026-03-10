package com.hgo_soft.device_for_all.departments.repositories;

import com.hgo_soft.device_for_all.departments.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
