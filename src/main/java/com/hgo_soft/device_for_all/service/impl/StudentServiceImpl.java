package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Student;
import com.hgo_soft.device_for_all.exception.ResourceNotFoundException;
import com.hgo_soft.device_for_all.repository.StudentRepository;
import com.hgo_soft.device_for_all.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return repository.findById(id)/*.orElse(new Student());*//*.orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id))*/;
    }

    @Override
    public Student save(Student entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
