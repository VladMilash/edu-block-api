package com.mvo.edublockapi.service.impl;

import com.mvo.edublockapi.dto.ResponseStudentDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.exception.AlReadyExistException;
import com.mvo.edublockapi.mapper.StudentMapper;
import com.mvo.edublockapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @InjectMocks
    private StudentServiceImpl serviceUnderTest;

    private StudentTransientDTO studentTransientDTO;
    private Student persistStudent;
    private ResponseStudentDTO responseStudentDTO;


    @BeforeEach
    void setUp() {
        studentTransientDTO = new StudentTransientDTO("test", "test@test.ru");
        persistStudent = new Student();
        persistStudent.setId(1L);
        persistStudent.setName("test");
        persistStudent.setEmail("test@test.ru");
        responseStudentDTO = new ResponseStudentDTO(1L,"test", "test@test.ru", new HashSet<>());
    }   

    @Test
    @DisplayName("Test save student functionality")
    public void givenStudentToSave_whenSaveStudent_thenRepositoryIsCalled() {
        //given
        when(studentRepository.existsByEmail(anyString())).thenReturn(false);
        when(studentRepository.save(any())).thenReturn(persistStudent);
        when(studentMapper.toResponseGetStudentDTO(any())).thenReturn(responseStudentDTO);

        //when
        ResponseStudentDTO responseFromService = serviceUnderTest.save(studentTransientDTO);

        //then
        assertNotNull(responseFromService);
        verify(studentRepository).existsByEmail(studentTransientDTO.email());
        verify(studentRepository).save(any());
    }

    @Test
    @DisplayName("Test save student with duplicate email functionality")
    public void givenStudentToSaveWithDuplicateEmail_whenSaveDeveloper_thenExceptionIsThrown() {
        //given
        when(studentRepository.existsByEmail(anyString())).thenReturn(true);

        //when
        assertThrows(AlReadyExistException.class, () -> serviceUnderTest.save(studentTransientDTO));

        //then
        verify(studentRepository).existsByEmail(studentTransientDTO.email());
        verify(studentRepository, never()).save(any());
    }
}