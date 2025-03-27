package com.mvo.edublockapi.repository;

import com.mvo.edublockapi.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "course with students")
    List<Course> findAll();
}
