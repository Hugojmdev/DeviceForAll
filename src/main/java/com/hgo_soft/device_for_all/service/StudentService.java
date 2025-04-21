package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Student save(Student entity);
    void deleteById(Long id);
}
