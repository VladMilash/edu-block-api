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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public ResponseCoursesDTO save(CourseTransientDTO courseTransientDTO) {
        log.info("Creating course with title: {}", courseTransientDTO.title());
        Course transientCourse = courseMapper.fromCourseTransientDTO(courseTransientDTO);
        Course persistCourse = courseRepository.save(transientCourse);
        log.info("Course successfully created with id: {}", persistCourse.getId());
        persistCourse.setStudents(new HashSet<>());
        return courseMapper.toResponseGetCourses(persistCourse);
    }

    @Override
    public List<ResponseCoursesDTO> getAll() {
        log.info("Getting all courses");
        List<Course> courses = courseRepository.findAll();
        return courses
            .stream()
            .map(courseMapper::toResponseGetCourses)
            .toList();
    }

    @Override
    public ResponseCoursesDTO getById(Long id) {
        Course course = getCourseById(id);
        log.info("Course with id: {} successfully found", id);
        return courseMapper.toResponseGetCourses(course);
    }

    @Override
    public ResponseCoursesDTO update(Long id, CourseTransientDTO courseTransientDTO) {
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
        log.info("Started found for course with id: {}", id);
        return courseRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Course with id {} not found", id);
                return new NotFoundEntityException("Course with ID " + id + " not found");
            });
    }

    @Transactional
    @Override
    public void setRelationWithStudent(Long courseId , Student student) {
        Course course = getCourseById(courseId);
        course.getStudents().add(student);
        courseRepository.save(course);
        log.info("Finished setting relation for course with id: {} and student with id: {}", courseId, student.getId());
    }

    @Transactional
    @Override
    public ResponseCoursesDTO setRelationWithTeacher(Long courseId, Teacher teacher) {
        Course course = getCourseById(courseId);
        course.setTeacher(teacher);
        Course uodatedCourse = courseRepository.save(course);
        log.info("Finished setting relation for course with id: {} and teacher with id: {}", courseId, teacher.getId());
        return courseMapper.toResponseGetCourses(uodatedCourse);
    }
}
