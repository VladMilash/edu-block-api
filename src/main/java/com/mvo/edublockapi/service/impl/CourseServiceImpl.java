package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.ResponseGetCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
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
    public CourseDTO save(CourseTransientDTO courseTransientDTO) {
        Course transientCourse = courseMapper.fromCourseTransientDTO(courseTransientDTO);
        Course persistCourse = courseRepository.save(transientCourse);
        persistCourse.setStudents(new HashSet<>());
        return courseMapper.map(persistCourse);
    }

    @Override
    public List<ResponseGetCoursesDTO> getAll() {
        List<Course> courses = courseRepository.findAll();
        return courses
            .stream()
            .map(courseMapper::toResponseGetCourses)
            .toList();
    }

    @Override
    public ResponseGetCoursesDTO getById(Long id) {
        return courseRepository.findById(id)
            .map(courseMapper::toResponseGetCourses)
            .orElseThrow(() -> {
                log.error("Course with id {} not found", id);
                return new NotFoundEntityException("Course with ID " + id + " not found");
            });
    }
}
