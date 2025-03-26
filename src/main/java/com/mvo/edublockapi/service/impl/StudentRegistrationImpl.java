package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import com.mvo.edublockapi.mapper.StudentMapper;
import com.mvo.edublockapi.repository.StudentRepository;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentRegistrationImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDTO save(StudentTransientDTO studentTransientDTO) {
        log.info("Creating student with name: {}, and email: {}", studentTransientDTO.name(), studentTransientDTO.email());
        Student transientStudent = studentMapper.fromStudentRegistrationDTO(studentTransientDTO);
        Student persistStudent = studentRepository.save(transientStudent);
        log.info("Student successfully created with id: {}", persistStudent.getId());
        persistStudent.setCourses(new HashSet<>());
        return studentMapper.map(persistStudent);
    }

    @Override
    public List<StudentDTO> getAll() {
        log.info("Getting all students");
        return studentRepository.findAll()
            .stream()
            .map(studentMapper::map)
            .toList();
    }

    @Override
    public StudentDTO getById(Long id) {
        log.info("Getting student by id: {}", id);
        return studentRepository.findById(id)
            .map(student -> {
                log.info("Student with id: {} successfully found", id);
                return studentMapper.map(student);
            })
            .orElseThrow(() -> {
                log.error("Student with id {} not found", id);
                return new NotFoundEntityException(
                    "Student with ID " + id + " not found"
                );
            });
    }

    @Override
    public StudentDTO update(Long id, StudentTransientDTO studentTransientDTO) {
        log.info("Getting student by id: {} for update", id);
        StudentDTO studentDTO = getById(id);
        Student student = studentMapper.map(studentDTO);
        log.info("Updating student with id: {}", id);
        student.setName(studentTransientDTO.name());
        student.setEmail(studentTransientDTO.email());
        Student updatededStudent = studentRepository.save(student);
        log.info("Student with id: {} successfully updated", id);
        return studentMapper.map(updatededStudent);
    }

    @Override
    public DeleteResponseDTO delete(Long id) {
        log.info("Getting student by id: {} for delete", id);
        StudentDTO studentDTO = getById(id);
        Student student = studentMapper.map(studentDTO);
        studentRepository.delete(student);
        log.info("Student with id: {} successfully deleted", id);
        return new DeleteResponseDTO("Student deleted successfully");
    }
}
