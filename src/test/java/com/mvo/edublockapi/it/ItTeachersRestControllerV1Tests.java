package com.mvo.edublockapi.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Teacher;
import com.mvo.edublockapi.repository.CourseRepository;
import com.mvo.edublockapi.repository.TeacherRepository;
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

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItTeachersRestControllerV1Tests extends AbstractRestControllerBaseTest {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CourseRepository courseRepository;

    private TeacherTransientDTO teacherTransientDTO;

    @BeforeEach
    void setUp() {
        teacherTransientDTO = new TeacherTransientDTO("test");
    }

    @Test
    @DisplayName("Test save teacher functionality")
    public void givenTeacherTransientDTO_whenSaveDepartment_thenSuccessResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teacherTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.department", nullValue()));
    }

    @Test
    @DisplayName("Test get teacher by id functionality")
    public void givenTeacherId_whenGetDepartment_thenSuccessResponse() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setName("test");
        teacherRepository.save(teacher);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/teachers/" + teacher.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.department", nullValue()));
    }

    @Test
    @DisplayName("Test get teacher by incorrect id functionality")
    public void givenIncorrectId_whenGetById_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/teachers/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Teacher with ID 200 not found")));
    }

    @Test
    @DisplayName("Test get all teachers functionality")
    public void givenGetTeachersRequest_whenGetStudents_thenNonEmptyList() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/teachers")
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Update teacher by id functionality")
    public void givenDepartmentId_whenUpdateDepartment_thenSuccessResponse() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setName("new");
        teacherRepository.save(teacher);

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/teachers/" + teacher.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teacherTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.courses").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.department", nullValue()));
    }

    @Test
    @DisplayName("Update teacher by incorrect id functionality")
    public void givenIncorrectId_whenUpdateTeacher_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/teachers/" + 200)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(teacherTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Teacher with ID 200 not found")));
    }

    @Test
    @DisplayName("Delete teacher by id functionality")
    public void givenTeacherId_whenDeleteTeacher_thenDeletedResponse() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setName("new");
        teacherRepository.save(teacher);

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/teachers/" + teacher.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.massage", CoreMatchers.is("Teacher deleted successfully")));
    }

    @Test
    @DisplayName("Delete teacher by incorrect id functionality")
    public void givenIncorrectId_whenDeleteTeacher_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/teachers/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Teacher with ID 200 not found")));
    }

    @Test
    @DisplayName("Set relation teacher-course functionality")
    public void givenTeacherIdAndCourseId_whenSetRelationWitTeacherCourse_thenSuccessResponse() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setName("test");
        teacherRepository.save(teacher);

        Course course = new Course();
        course.setTitle("test");
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/teachers/" + teacher.getId() + "/courses/" + course.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.teacher.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.teacher.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.students").isEmpty());
    }

    @Test
    @DisplayName("Set relation teacher-course with incorrect teacher id and correct course id functionality")
    public void givenIncorrectTeacherIdAndCorrectCourseId_whenSetRelationWitTeacherCourse_thenErrorResponse() throws Exception {
        //given
        Course course = new Course();
        course.setTitle("test");
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/teachers/" + 200 + "/courses/" + course.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Teacher with ID 200 not found")));
    }

    @Test
    @DisplayName("Set relation teacher-course with correct teacher id and incorrect course id functionality")
    public void givenCorrectTeacherIdAndIncorrectCourseId_whenSetRelationWitTeacherCourse_thenErrorResponse() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setName("test");
        teacherRepository.save(teacher);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/teachers/" + teacher.getId() + "/courses/" + 200)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Course with ID 200 not found")));
    }

}
