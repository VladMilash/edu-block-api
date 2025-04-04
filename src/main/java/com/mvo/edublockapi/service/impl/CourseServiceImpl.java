package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetCoursesDTO;
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

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public ResponseGetCoursesDTO save(CourseTransientDTO courseTransientDTO) {
        log.info("Creating course with title: {}", courseTransientDTO.title());
        Course transientCourse = courseMapper.fromCourseTransientDTO(courseTransientDTO);
        Course persistCourse = courseRepository.save(transientCourse);
        log.info("Course successfully created with id: {}", persistCourse.getId());
        persistCourse.setStudents(new HashSet<>());
        return courseMapper.toResponseGetCourses(persistCourse);
    }

    @Override
    public List<ResponseGetCoursesDTO> getAll() {
        log.info("Getting all courses");
        List<Course> courses = courseRepository.findAll();
        return courses
            .stream()
            .map(courseMapper::toResponseGetCourses)
            .toList();
    }

    @Override
    public ResponseGetCoursesDTO getById(Long id) {
        Course course = getCourseById(id);
        log.info("Course with id: {} successfully found", id);
        return courseMapper.toResponseGetCourses(course);
    }

    @Override
    public ResponseGetCoursesDTO update(Long id, CourseTransientDTO courseTransientDTO) {
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

    public Course getCourseById(Long id) {
        log.info("Started found for course with id: {}", id);
        return courseRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Course with id {} not found", id);
                return new NotFoundEntityException("Course with ID " + id + " not found");
            });
    }

    @Override
    public void setRelationWithStudent(Long courseId , Student student) {
        Course course = getCourseById(courseId);
        course.getStudents().add(student);
        courseRepository.save(course);
        log.info("Finished setting relation for course with id: {} and student with id: {}" ,courseId,student.getId());
    }

    @Override
    public ResponseGetCoursesDTO setRelationWithTeacher(Long courseId, Teacher teacher) {
        Course course = getCourseById(courseId);
        course.setTeacher(teacher);
        return courseMapper.toResponseGetCourses(courseRepository.save(course));
    }
}
