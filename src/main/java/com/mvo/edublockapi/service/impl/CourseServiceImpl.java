package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.CourseDTO;
import com.mvo.edublockapi.dto.ResponseGetAllCourses;
import com.mvo.edublockapi.dto.StudentShortDTO;
import com.mvo.edublockapi.dto.TeacherShortDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.mapper.CourseMapper;
import com.mvo.edublockapi.repository.CourseRepository;
import com.mvo.edublockapi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ResponseGetAllCourses> getAll() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
            .map(course ->
                new ResponseGetAllCourses(
                    course.getId(),
                    course.getTitle(),
                    course.getTeacher() != null
                        ? new TeacherShortDTO(course.getTeacher().getId(), course.getTeacher().getName())
                        : null,
                    course.getStudents()
                        .stream()
                        .map(student -> new StudentShortDTO(student.getId(), student.getName())
                        ).collect(Collectors.toSet())
                )).collect(Collectors.toList());
    }
}
