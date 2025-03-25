package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.StudentDTO;
import com.mvo.edublockapi.dto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.mapper.StudentMapper;
import com.mvo.edublockapi.repository.StudentRepository;
import com.mvo.edublockapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentRegistrationImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDTO save(StudentTransientDTO studentTransientDTO) {
        Student transientStudent = studentMapper.fromStudentRegistrationDTO(studentTransientDTO);
        Student persistStudent = studentRepository.save(transientStudent);
        persistStudent.setCourses(new HashSet<>());
        return studentMapper.map(persistStudent);
    }

    @Override
    public List<StudentDTO> getAll() {
        return studentRepository.findAll()
            .stream()
            .map(studentMapper::map)
            .toList();
    }

    @Override
    public StudentDTO getById(Long id) {
        return studentRepository.findById(id)
            .map(studentMapper::map)
            .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public StudentDTO update(Long id, StudentTransientDTO studentTransientDTO) {
        Student student = studentRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);

        student.setName(studentTransientDTO.name());
        student.setEmail(studentTransientDTO.email());

        Student updatdedStudent = studentRepository.save(student);
        return studentMapper.map(updatdedStudent);
    }
}
