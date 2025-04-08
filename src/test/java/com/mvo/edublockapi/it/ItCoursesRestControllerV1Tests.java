package com.mvo.edublockapi.it;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.repository.CourseRepository;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItCoursesRestControllerV1Tests extends AbstractRestControllerBaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CourseRepository courseRepository;

    private CourseTransientDTO courseTransientDTO;

    @BeforeEach
    void setUp() {
        courseTransientDTO = new CourseTransientDTO("test");
    }

    @Test
    @DisplayName("Test save course functionality")
    public void givenCourseTransientDTO_whenSaveCourse_thenSuccessResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/courses/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.teacher", nullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.students").isEmpty());
    }


    @Test
    @DisplayName("Test get course by id functionality")
    public void givenCourseId_whenGetCourse_thenSuccessResponse() throws Exception {
        //given
        Course course = new Course();
        course.setTitle("Test");
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/courses/" + course.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.teacher").isEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.students").isEmpty());
    }

    @Test
    @DisplayName("Test get course by incorrect id functionality")
    public void givenIncorrectId_whenGetById_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/courses/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Course with ID 200 not found")));
    }

    @Test
    @DisplayName("Test get all courses functionality")
    public void givenGetStudentsRequest_whenGetStudents_thenNonEmptyList() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/courses/")
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Update course by id functionality")
    public void givenCourseId_whenUpdateCourse_thenSuccessResponse() throws Exception {
        //given
        Course course = new Course();
        course.setTitle("New");
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/courses/" + course.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.notNullValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("test")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.teacher").isEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.students").isEmpty());
    }

    @Test
    @DisplayName("Update course by incorrect id functionality")
    public void givenIncorrectId_whenUpdateCourse_thenDeletedResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/courses/" + 200)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Course with ID 200 not found")));
    }

    @Test
    @DisplayName("Delete course by id functionality")
    public void givenCourseId_whenDeleteCourse_thenDeletedResponse() throws Exception {
        //given
        Course course = new Course();
        course.setTitle("New");
        courseRepository.save(course);

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/courses/" + course.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.massage", CoreMatchers.is("Course deleted successfully")));
    }

    @Test
    @DisplayName("Delete course by incorrect id functionality")
    public void givenIncorrectId_whenDeleteCourse_thenDeletedResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/courses/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Course with ID 200 not found")));
    }

}
