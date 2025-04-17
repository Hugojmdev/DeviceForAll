package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Teacher;
import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> findAll();
    Optional<Teacher> findById(Long id);
    Teacher save(Teacher entity);
    void deleteById(Long id);
}
