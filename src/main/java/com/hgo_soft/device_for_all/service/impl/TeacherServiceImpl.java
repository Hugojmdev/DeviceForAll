package com.hgo_soft.device_for_all.service.impl;

import com.hgo_soft.device_for_all.entity.Teacher;
import com.hgo_soft.device_for_all.repository.TeacherRepository;
import com.hgo_soft.device_for_all.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;

    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Teacher> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Teacher save(Teacher entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
