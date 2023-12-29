package com.example.studentmanagementjpa.repository;

import com.example.studentmanagementjpa.model.Classroom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassroomRepository extends PagingAndSortingRepository<Classroom, Long> {
}
