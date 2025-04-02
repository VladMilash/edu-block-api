package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.*;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import com.mvo.edublockapi.mapper.StudentMapper;
import com.mvo.edublockapi.repository.StudentRepository;
import com.mvo.edublockapi.service.CourseService;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public ResponseGetStudentDTO save(StudentTransientDTO studentTransientDTO) {
        log.info("Creating student with name: {}, and email: {}", studentTransientDTO.name(), studentTransientDTO.email());
        Student transientStudent = studentMapper.fromStudentTransientDTO(studentTransientDTO);
        Student persistStudent = studentRepository.save(transientStudent);
        log.info("Student successfully created with id: {}", persistStudent.getId());
        persistStudent.setCourses(new HashSet<>());
        return studentMapper.toResponseGetStudentDTO(persistStudent);
    }

    @Override
    public List<ResponseGetStudentDTO> getAll() {
        log.info("Getting all students");
        List<Student> students = studentRepository.findAll();
        return students
            .stream()
            .map(studentMapper::toResponseGetStudentDTO)
            .toList();
    }

    @Override
    public ResponseGetStudentDTO getById(Long id) {
        Student student = getStudent(id);
        log.info("Student with id: {} successfully found", id);
        return studentMapper.toResponseGetStudentDTO(student);
    }

    @Override
    public ResponseGetStudentDTO update(Long id, StudentTransientDTO studentTransientDTO) {
        log.info("Getting student by id: {} for update", id);
        Student student = getStudent(id);
        log.info("Updating student with id: {}", id);
        student.setName(studentTransientDTO.name());
        student.setEmail(studentTransientDTO.email());
        Student updatededStudent = studentRepository.save(student);
        log.info("Student with id: {} successfully updated", id);
        return studentMapper.toResponseGetStudentDTO(updatededStudent);
    }

    @Override
    public DeleteResponseDTO delete(Long id) {
        log.info("Getting student by id: {} for delete", id);
        Student student = getStudent(id);
        studentRepository.delete(student);
        log.info("Student with id: {} successfully deleted", id);
        return new DeleteResponseDTO("Student deleted successfully");
    }

    @Override
    public ResponseGetStudentDTO setRelationWithCourse(Long studentId, Long courseId) {
        log.info("Setting relations for student-course, with student id: {}, and course id: {}", studentId, courseId);
        Student student = getStudent(studentId);
        Course course = courseService.getCourseById(courseId);
        student.getCourses().add(course);
        courseService.setRelationWithStudent(courseId, student);
        Student updatedStudent = studentRepository.save(student);
        log.info("Finished setting relation for student with id: {} and course with id: {}" ,courseId,student.getId());
        return studentMapper.toResponseGetStudentDTO(updatedStudent);
    }

    private Student getStudent(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Student with id {} not found", id);
                return new NotFoundEntityException(
                    "Student with ID " + id + " not found"
                );
            });
    }

}
