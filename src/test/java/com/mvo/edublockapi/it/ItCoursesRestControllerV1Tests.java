package com.mvo.edublockapi.it;

import com.mvo.edublockapi.dto.DeleteResponseDTO;
import com.mvo.edublockapi.dto.ResponseGetCoursesDTO;
import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
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
import org.testcontainers.shaded.org.hamcrest.Matchers;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItCoursesRestControllerV1Tests extends AbstractRestControllerBaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private CourseTransientDTO courseTransientDTO;
    private ResponseGetCoursesDTO responseGetCoursesDTO;
    private DeleteResponseDTO deleteResponseDTO;


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

}
