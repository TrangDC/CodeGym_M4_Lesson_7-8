package com.example.studentmanagementjpa.service;

import com.example.studentmanagementjpa.model.Classroom;
import com.example.studentmanagementjpa.model.Student;
import org.springframework.data.domain.Page;

import java.util.Comparator;

public interface IStudentService extends IGenerateService<Student>, Comparator<Student> {
    Iterable<Student> findAllByClassroom(Classroom classroom);
    Iterable<Student> findAllByGender(String gender);
}
