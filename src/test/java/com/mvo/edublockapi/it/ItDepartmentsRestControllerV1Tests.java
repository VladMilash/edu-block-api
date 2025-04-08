package com.mvo.edublockapi.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;
import com.mvo.edublockapi.entity.Department;
import com.mvo.edublockapi.entity.Teacher;
import com.mvo.edublockapi.repository.DepartmentRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItDepartmentsRestControllerV1Tests extends AbstractRestControllerBaseTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    private DepartmentTransientDTO departmentTransientDTO;

    @BeforeEach
    void setUp() {
        departmentTransientDTO = new DepartmentTransientDTO("test");
    }

    @Test
    @DisplayName("Test save department functionality")
    public void givenDepartmentTransientDTO_whenSaveDepartment_thenSuccessResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/departments/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(departmentTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.headOfDepartment", nullValue()));
    }

    @Test
    @DisplayName("Test get department by id functionality")
    public void givenDepartmentId_whenGetDepartment_thenSuccessResponse() throws Exception {
        //given
        Department department = new Department();
        department.setName("test");
        departmentRepository.save(department);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/departments/" + department.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.headOfDepartment", nullValue()));
    }

    @Test
    @DisplayName("Test get department by incorrect id functionality")
    public void givenIncorrectId_whenGetById_thenErrorResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/departments/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Department with ID 200 not found")));
    }

    @Test
    @DisplayName("Test get all departments functionality")
    public void givenGetDepartmentsRequest_whenGetStudents_thenNonEmptyList() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/departments/")
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    @DisplayName("Update department by id functionality")
    public void givenDepartmentId_whenUpdateDepartment_thenSuccessResponse() throws Exception {
        //given
        Department department = new Department();
        department.setName("new");
        departmentRepository.save(department);

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/departments/" + department.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(departmentTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.headOfDepartment", nullValue()));
    }

    @Test
    @DisplayName("Update department by incorrect id functionality")
    public void givenIncorrectId_whenUpdateDepartment_thenDeletedResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(put("/api/v1/departments/" + 200)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(departmentTransientDTO)));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Department with ID 200 not found")));
    }

    @Test
    @DisplayName("Delete department by id functionality")
    public void givenDepartmentId_whenDeleteDepartment_thenDeletedResponse() throws Exception {
        //given
        Department department = new Department();
        department.setName("new");
        departmentRepository.save(department);

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/departments/" + department.getId())
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.massage", CoreMatchers.is("Department deleted successfully")));
    }

    @Test
    @DisplayName("Delete department by incorrect id functionality")
    public void givenIncorrectId_whenDeleteDepartment_thenDeletedResponse() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/departments/" + 200)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Department with ID 200 not found")));
    }

    @Test
    @DisplayName("Set relation department-teacher functionality")
    public void givenDepartmentIdAndTeacherId_whenSetRelationWitTeacherDepartment_thenSuccessResponse() throws Exception {
        //given
        Department department = new Department();
        department.setName("test");
        departmentRepository.save(department);

        Teacher teacher = new Teacher();
        teacher.setName("test");
        teacherRepository.save(teacher);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/departments/" + department.getId() + "/teacher/" + teacher.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.headOfDepartment.id").value(teacher.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.headOfDepartment.name").value(teacher.getName()));
    }

    @Test
    @DisplayName("Set relation department-teacher with incorrect department id and correct teacher id functionality")
    public void givenIncorrectDepartmentIdCorrectAndTeacherId_whenSetRelationWitTeacherDepartment_thenSuccessResponse() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setName("test");
        teacherRepository.save(teacher);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/departments/" + 200 + "/teacher/" + teacher.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Department with ID 200 not found")));
    }

    @Test
    @DisplayName("Set relation department-teacher with correct department id and incorrect teacher id functionality")
    public void givenCorrectDepartmentIdIncorrectAndTeacherId_whenSetRelationWitTeacherDepartment_thenSuccessResponse() throws Exception {
        //given
        Department department = new Department();
        department.setName("test");
        departmentRepository.save(department);

        //when
        ResultActions result = mockMvc
            .perform(post("/api/v1/departments/" + department.getId() + "/teacher/" + 200)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Teacher with ID 200 not found")));
    }

}
