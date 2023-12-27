package com.example.studentmanagementjpa.service.impl;

import com.example.studentmanagementjpa.model.Classroom;
import com.example.studentmanagementjpa.repository.IClassroomRepository;
import com.example.studentmanagementjpa.service.IClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ClassroomService implements IClassroomService {
    @Autowired
    public IClassroomRepository iClassroomRepository;
    @Override
    public Iterable<Classroom> findAll() {
        return iClassroomRepository.findAll();
    }

    @Override
    public void save(Classroom classroom) {
        iClassroomRepository.save(classroom);
    }

    @Override
    public Optional<Classroom> findById(Long id) {
        return iClassroomRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iClassroomRepository.deleteById(id);
    }
}
