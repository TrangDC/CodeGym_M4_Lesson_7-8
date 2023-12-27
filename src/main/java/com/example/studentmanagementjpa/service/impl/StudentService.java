package com.example.studentmanagementjpa.service.impl;

import com.example.studentmanagementjpa.model.Classroom;
import com.example.studentmanagementjpa.model.Student;
import com.example.studentmanagementjpa.repository.IStudentRepository;
import com.example.studentmanagementjpa.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService implements IStudentService {

    @Autowired
    public IStudentRepository iStudentRepository;

    @Override
    public Iterable<Student> findAll() {
        return iStudentRepository.findAll();
    }

    @Override
    public void save(Student student) {
        iStudentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return iStudentRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iStudentRepository.deleteById(id);
    }

    @Override
    public Iterable<Student> findAllByClassroom(Classroom classroom) {
        return iStudentRepository.findAllByClassroom(classroom);
    }

    @Override
    public Iterable<Student> findAllByGender(String gender) {
        return iStudentRepository.findAllByGender(gender);
    }

    public int compare(Student o1, Student o2) {
        return (o1.getAge() - (o2.getAge()));
    }

}
