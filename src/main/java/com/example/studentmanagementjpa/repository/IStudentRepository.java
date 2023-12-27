package com.example.studentmanagementjpa.repository;

import com.example.studentmanagementjpa.model.Classroom;
import com.example.studentmanagementjpa.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface IStudentRepository extends CrudRepository<Student, Long> {
    Iterable<Student> findAllByClassroom(Classroom classroom);

    Iterable<Student> findAllByGender(String gender);
}
