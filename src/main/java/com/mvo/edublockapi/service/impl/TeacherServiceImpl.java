package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseCoursesDTO;
import com.mvo.edublockapi.dto.ResponseTeacherDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Teacher;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import com.mvo.edublockapi.mapper.TeacherMapper;
import com.mvo.edublockapi.repository.TeacherRepository;
import com.mvo.edublockapi.service.CourseService;
import com.mvo.edublockapi.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final CourseService courseService;

    @Override
    public ResponseTeacherDTO save(@Valid TeacherTransientDTO teacherTransientDTO) {
        log.info("Creating teacher with name: {}", teacherTransientDTO.name());
        Teacher transientTeacher = teacherMapper.fromTeacherTransientDTO(teacherTransientDTO);
        transientTeacher.setCourses(new HashSet<>());
        Teacher persistTeacher = teacherRepository.save(transientTeacher);
        log.info("Teacher successfully created with id: {}", persistTeacher.getId());
        return teacherMapper.toResponseGetTeacherDTO(persistTeacher);
    }

    @Override
    public ResponseTeacherDTO getById(Long id) {
        log.info("Getting teacher by id: {}", id);
        Teacher teacher = getTeacher(id);
        log.info("Teacher with id: {} successfully found", id);
        return teacherMapper.toResponseGetTeacherDTO(teacher);
    }

    @Override
    public Page<ResponseTeacherDTO> getAll(int page, int size) {
        log.info("Getting all teachers");
        Pageable pageable = PageRequest.of(page, size);
        return teacherRepository.findAll(pageable)
            .map(teacherMapper::toResponseGetTeacherDTO);
    }

    @Override
    public ResponseTeacherDTO update(Long id, @Valid TeacherTransientDTO teacherTransientDTO) {
        log.info("Getting teacher by id: {} for update", id);
        Teacher teacher = getTeacher(id);
        log.info("Updating teacher with id: {}", id);
        teacher.setName(teacherTransientDTO.name());
        Teacher updatedTeacher = teacherRepository.save(teacher);
        log.info("Teacher with id: {} successfully updated", id);
        return teacherMapper.toResponseGetTeacherDTO(updatedTeacher);
    }

    @Override
    public DeleteResponseDTO delete(Long id) {
        log.info("Getting teacher by id: {} for delete", id);
        Teacher teacher = getTeacher(id);
        teacherRepository.delete(teacher);
        log.info("Teacher with id {}: successfully deleted", id);
        return new DeleteResponseDTO("Teacher deleted successfully");
    }

    @Transactional
    @Override
    public ResponseCoursesDTO setRelationTeacherWithCourse(Long teacherId, Long courseId) {
        log.info("Setting relations for teacher-course, with teacher id: {}, and course id: {}", teacherId, courseId);
        Teacher teacher = getTeacher(teacherId);
        Course course = courseService.getCourseById(courseId);
        teacher.getCourses().add(course);
        return courseService.setRelationWithTeacher(course, teacher);
    }

    @Override
    public Teacher getTeacher(Long id) {
        log.info("Searching for teacher with id: {}", id);
        return teacherRepository.findTeacherById(id)
            .orElseThrow(() -> {
                log.error("Teacher with id {} not found", id);
                return new NotFoundEntityException(
                    "Teacher with ID " + id + " not found"
                );
            });
    }

}
