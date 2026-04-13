package com.challenge.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for EmployeeController.
 * Uses MockMvc to simulate real HTTP requests against the full Spring context.
 */
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    // -------------------------------------------------------
    // GET /api/v1/employee
    // -------------------------------------------------------

    @Test
    void getAllEmployees_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllEmployees_shouldReturnJsonArray() throws Exception {
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllEmployees_shouldReturnNonEmptyList() throws Exception {
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    // -------------------------------------------------------
    // GET /api/v1/employee/{uuid}
    // -------------------------------------------------------

    @Test
    void getEmployeeByUuid_shouldReturn200ForValidUuid() throws Exception {
        UUID existingUuid = employeeService.getAllEmployees().get(0).getUuid();
        mockMvc.perform(get("/api/v1/employee/" + existingUuid))
                .andExpect(status().isOk());
    }

    @Test
    void getEmployeeByUuid_shouldReturnCorrectEmployee() throws Exception {
        List<Employee> employees = employeeService.getAllEmployees();
        UUID existingUuid = employees.get(0).getUuid();
        String expectedFirstName = employees.get(0).getFirstName();

        mockMvc.perform(get("/api/v1/employee/" + existingUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(expectedFirstName));
    }

    @Test
    void getEmployeeByUuid_shouldReturn404ForUnknownUuid() throws Exception {
        UUID randomUuid = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/employee/" + randomUuid))
                .andExpect(status().isNotFound());
    }

    // -------------------------------------------------------
    // POST /api/v1/employee
    // -------------------------------------------------------

    @Test
    void createEmployee_shouldReturn201() throws Exception {
        String requestBody = """
                {
                    "firstName": "Alice",
                    "lastName": "Brown",
                    "salary": 75000,
                    "age": 26,
                    "jobTitle": "QA Engineer",
                    "email": "alice.brown@company.com"
                }
                """;

        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void createEmployee_shouldReturnCreatedEmployeeWithCorrectFields() throws Exception {
        String requestBody = """
                {
                    "firstName": "Alice",
                    "lastName": "Brown",
                    "salary": 75000,
                    "age": 26,
                    "jobTitle": "QA Engineer",
                    "email": "alice.brown@company.com"
                }
                """;

        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Brown"))
                .andExpect(jsonPath("$.jobTitle").value("QA Engineer"))
                .andExpect(jsonPath("$.email").value("alice.brown@company.com"))
                .andExpect(jsonPath("$.uuid").isNotEmpty());
    }

    @Test
    void createEmployee_shouldReturn400ForEmptyBody() throws Exception {
        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
