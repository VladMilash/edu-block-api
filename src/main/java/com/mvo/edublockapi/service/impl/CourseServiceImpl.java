package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.entity.Teacher;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import com.mvo.edublockapi.mapper.CourseMapper;
import com.mvo.edublockapi.repository.CourseRepository;
import com.mvo.edublockapi.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public ResponseCoursesDTO save(@Valid CourseTransientDTO courseTransientDTO) {
        log.info("Creating course with title: {}", courseTransientDTO.title());
        Course transientCourse = courseMapper.fromCourseTransientDTO(courseTransientDTO);
        transientCourse.setStudents(new HashSet<>());
        Course persistCourse = courseRepository.save(transientCourse);
        log.info("Course successfully created with id: {}", persistCourse.getId());
        return courseMapper.toResponseGetCourses(persistCourse);
    }

    @Override
    public Page<ResponseCoursesDTO> getAll(int page, int size) {
        log.info("Getting all courses");
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses
            .map(courseMapper::toResponseGetCourses);
    }

    @Override
    public ResponseCoursesDTO getById(Long id) {
        Course course = getCourseById(id);
        log.info("Course with id: {} successfully found", id);
        return courseMapper.toResponseGetCourses(course);
    }

    @Override
    public ResponseCoursesDTO update(Long id, @Valid CourseTransientDTO courseTransientDTO) {
        log.info("Getting course by id: {} for update", id);
        Course course = getCourseById(id);
        log.info("Updating course with id: {}", id);
        course.setTitle(courseTransientDTO.title());
        Course updatedCourse = courseRepository.save(course);
        log.info("Course with id: {} successfully updated", id);
        return courseMapper.toResponseGetCourses(updatedCourse);
    }

    @Override
    public DeleteResponseDTO delete(Long id) {
        log.info("Getting course by id: {} for delete", id);
        Course course = getCourseById(id);
        courseRepository.delete(course);
        log.info("Course with id: {} successfully deleted", id);
        return new DeleteResponseDTO("Course deleted successfully");
    }

    @Override
    public Course getCourseById(Long id) {
        log.info("Searching for course with id: {}", id);
        return courseRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Course with id {} not found", id);
                return new NotFoundEntityException("Course with ID " + id + " not found");
            });
    }

    @Transactional
    @Override
    public void setRelationWithStudent(Course course, Student student) {
        course.getStudents().add(student);
        courseRepository.save(course);
        log.info("Finished setting relation for course with id: {} and student with id: {}", course.getId(), student.getId());
    }

    @Transactional
    @Override
    public ResponseCoursesDTO setRelationWithTeacher(Course course, Teacher teacher) {
        course.setTeacher(teacher);
        Course updatedCourse = courseRepository.save(course);
        log.info("Finished setting relation for course with id: {} and teacher with id: {}", course.getId(), teacher.getId());
        return courseMapper.toResponseGetCourses(updatedCourse);
    }
}
