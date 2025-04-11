package com.hgo_soft.device_for_all.service;

import com.hgo_soft.device_for_all.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    Student save(Student entity);
    void deleteById(Long id);
}
