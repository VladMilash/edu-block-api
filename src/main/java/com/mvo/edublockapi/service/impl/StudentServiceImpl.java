package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.*;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.exception.AlReadyExistException;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import com.mvo.edublockapi.mapper.StudentMapper;
import com.mvo.edublockapi.repository.StudentRepository;
import com.mvo.edublockapi.service.CourseService;
import com.mvo.edublockapi.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseService courseService;

    @Override
    public ResponseStudentDTO save(@Valid StudentTransientDTO studentTransientDTO) {
        if (studentRepository.existsByEmail(studentTransientDTO.email())) {
            log.error("The email {} was used for registration earlier", studentTransientDTO.email());
            throw new AlReadyExistException("Student with email " + studentTransientDTO.email() + " already exist");
        }
        log.info("The email {} has not been used for registration before", studentTransientDTO.email());
        log.info("Creating student with name: {}, and email: {}", studentTransientDTO.name(), studentTransientDTO.email());
        Student transientStudent = studentMapper.fromStudentTransientDTO(studentTransientDTO);
        transientStudent.setCourses(new HashSet<>());
        Student persistStudent = studentRepository.save(transientStudent);
        log.info("Student successfully created with id: {}", persistStudent.getId());
        return studentMapper.toResponseGetStudentDTO(persistStudent);
    }

    @Override
    public List<ResponseStudentDTO> getAll() {
        log.info("Getting all students");
        List<Student> students = studentRepository.findAll();
        return students
            .stream()
            .map(studentMapper::toResponseGetStudentDTO)
            .toList();
    }

    @Override
    public ResponseStudentDTO getById(Long id) {
        log.info("Getting student by id: {}", id);
        Student student = getStudent(id);
        log.info("Student with id: {} successfully found", id);
        return studentMapper.toResponseGetStudentDTO(student);
    }

    @Override
    public ResponseStudentDTO update(Long id, @Valid StudentTransientDTO studentTransientDTO) {
        log.info("Getting student by id: {} for update", id);
        Student student = getStudent(id);
        log.info("Updating student with id: {}", id);
        student.setName(studentTransientDTO.name());
        student.setEmail(studentTransientDTO.email());
        Student updatedStudent = studentRepository.save(student);
        log.info("Student with id: {} successfully updated", id);
        return studentMapper.toResponseGetStudentDTO(updatedStudent);
    }

    @Override
    public DeleteResponseDTO delete(Long id) {
        log.info("Getting student by id: {} for delete", id);
        Student student = getStudent(id);
        studentRepository.delete(student);
        log.info("Student with id: {} successfully deleted", id);
        return new DeleteResponseDTO("Student deleted successfully");
    }

    @Transactional
    @Override
    public ResponseStudentDTO setRelationWithCourse(Long studentId, Long courseId) {
        log.info("Setting relations for student-course, with student id: {}, and course id: {}", studentId, courseId);
        Student student = getStudent(studentId);
        Course course = courseService.getCourseById(courseId);
        student.getCourses().add(course);
        courseService.setRelationWithStudent(course, student);
        Student updatedStudent = studentRepository.save(student);
        log.info("Finished setting relation for student with id: {} and course with id: {}" ,student.getId(), courseId);
        return studentMapper.toResponseGetStudentDTO(updatedStudent);
    }

    @Override
    public Set<CourseShortDTO> getStudentCourses(Long id) {
        log.info("Getting courses for student with id: {}", id);
        ResponseStudentDTO student = getById(id);
        log.info("Courses for student with id: {} successfully found", id);
        return student.courses();

    }

    private Student getStudent(Long id) {
        log.info("Searching for student with id: {}", id);
        return studentRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Student with id {} not found", id);
                return new NotFoundEntityException(
                    "Student with ID " + id + " not found"
                );
            });
    }

}
