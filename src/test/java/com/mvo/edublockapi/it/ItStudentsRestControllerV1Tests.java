package com.mvo.edublockapi.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.repository.CourseRepository;
import com.mvo.edublockapi.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItStudentsRestControllerV1Tests extends AbstractRestControllerBaseTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CourseRepository courseRepository;

    private StudentTransientDTO studentTransientDTO;

    @BeforeEach
    void setUp() {
        studentTransientDTO = new StudentTransientDTO("test", "test@test.ru");
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save student functionality")
    public void givenStudentTransientDTO_whenSaveStudent_thenSuccessResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/students/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.ru"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses").isEmpty());
    }

    @Test
    @DisplayName("Test create student with duplicate email functionality")
    public void givenStudentTransientDTOWithDuplicateEmail_whenSaveStudent_thenErrorResponse() throws Exception {
        //given
        String duplicateEmail = "duplicate@mail.com";
        Student student = new Student();
        student.setName("test");
        student.setEmail(duplicateEmail);
        studentRepository.save(student);
        StudentTransientDTO studentTransientDTOWithDuplicateEmail = new StudentTransientDTO("new", duplicateEmail);

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/students/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentTransientDTOWithDuplicateEmail)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                CoreMatchers.is("Student with email " + studentTransientDTOWithDuplicateEmail.email() + " already exist")));
    }

    @Test
    @DisplayName("Test get student by id functionality")
    public void givenStudentId_whenGetStudent_thenSuccessResponse() throws Exception {
        //given
        Student student = new Student();
        student.setName("test");
        student.setEmail("test@test.ru");
        studentRepository.save(student);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/students/" + student.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.ru"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses").isEmpty());
    }

    @Test
    @DisplayName("Test get student by incorrect id functionality")
    public void givenIncorrectId_whenGetById_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/students/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Student with ID 200 not found")));
    }

    @Test
    @DisplayName("Test get all students functionality")
    public void givenGetStudentsRequest_whenGetStudents_thenNonEmptyList() throws Exception {
        //given
        Student student = new Student();
        student.setName("new");
        student.setEmail("new@new.ru");
        studentRepository.save(student);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/students/")
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty());
    }

    @Test
    @DisplayName("Update student by id functionality")
    public void givenStudentId_whenUpdateStudent_thenSuccessResponse() throws Exception {
        //given
        Student student = new Student();
        student.setName("new");
        student.setEmail("new@new.ru");
        studentRepository.save(student);

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/students/" + student.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.ru"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses").isEmpty());
    }

    @Test
    @DisplayName("Update student by incorrect id functionality")
    public void givenIncorrectId_whenUpdateStudent_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/students/" + 200)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Student with ID 200 not found")));
    }

    @Test
    @DisplayName("Delete student by id functionality")
    public void givenStudentId_whenDeleteStudent_thenDeletedResponse() throws Exception {
        //given
        Student student = new Student();
        student.setName("new");
        student.setEmail("new@new.ru");
        studentRepository.save(student);

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/students/" + student.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.massage", CoreMatchers.is("Student deleted successfully")));
    }

    @Test
    @DisplayName("Delete student by incorrect id functionality")
    public void givenIncorrectId_whenDeleteStudent_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/students/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Student with ID 200 not found")));
    }

    @Test
    @DisplayName("Test get student courses functionality")
    public void givenStudentId_whenGetStudentCourses_thenSuccessResponse() throws Exception {
        //given
        Student student = new Student();
        student.setName("test");
        student.setEmail("test@test.ru");
        student.setCourses(new HashSet<>());
        studentRepository.save(student);
        Course course = new Course();
        course.setTitle("course");
        course.setStudents(new HashSet<>());
        courseRepository.save(course);
        student.getCourses().add(course);
        course.getStudents().add(student);
        studentRepository.save(student);
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/students/" + student.getId() + "/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(course.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("course"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].teacher").isEmpty());
    }

    @Test
    @DisplayName("Set relation student-course functionality")
    public void givenStudentIdAndCourseId_whenSetRelationWithCourse_thenSuccessResponse() throws Exception {
        //given
        Student student = new Student();
        student.setName("test");
        student.setEmail("test@test.ru");
        studentRepository.save(student);
        Course course = new Course();
        course.setTitle("course");
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/students/" + student.getId() + "/courses/" + course.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@test.ru"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses.[0].id").value(course.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses.[0].title").value(course.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses.[0].teacher").isEmpty());
    }

    @Test
    @DisplayName("Set relation student-course with incorrect student id and correct course id functionality")
    public void givenIncorrectStudentIdAndCorrectCourseId_whenSetRelationWitStudentCourse_thenErrorResponse() throws Exception {
        //given
        Course course = new Course();
        course.setTitle("course");
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/students/" + 200 + "/courses/" + course.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Student with ID 200 not found")));
    }

    @Test
    @DisplayName("Set relation student-course with correct student id and incorrect course id functionality")
    public void givenCorrectStudentIdAndIncorrectCourseId_whenSetRelationWitStudentCourse_thenErrorResponse() throws Exception {
        //given
        Student student = new Student();
        student.setName("test");
        student.setEmail("test@test.ru");
        studentRepository.save(student);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/students/" + student.getId() + "/courses/" + 200)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Course with ID 200 not found")));
    }

}
