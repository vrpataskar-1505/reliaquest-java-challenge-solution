package com.challenge.api.service;

import static org.junit.jupiter.api.Assertions.*;

import com.challenge.api.model.Employee;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for EmployeeService.
 * Tests all service methods in isolation without spinning up the Spring context.
 */
class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        // Fresh service instance before each test
        employeeService = new EmployeeService();
    }

    // -------------------------------------------------------
    // getAllEmployees()
    // -------------------------------------------------------

    @Test
    void getAllEmployees_shouldReturnNonEmptyList() {
        List<Employee> employees = employeeService.getAllEmployees();
        assertNotNull(employees, "Employee list should not be null");
        assertFalse(employees.isEmpty(), "Employee list should not be empty");
    }

    @Test
    void getAllEmployees_shouldReturnThreeMockEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(3, employees.size(), "Should return exactly 3 mock employees");
    }

    @Test
    void getAllEmployees_shouldHaveValidUuids() {
        List<Employee> employees = employeeService.getAllEmployees();
        for (Employee e : employees) {
            assertNotNull(e.getUuid(), "Each employee should have a UUID");
        }
    }

    // -------------------------------------------------------
    // getEmployeeByUuid()
    // -------------------------------------------------------

    @Test
    void getEmployeeByUuid_shouldReturnCorrectEmployee() {
        // Get a real UUID from mock data
        UUID existingUuid = employeeService.getAllEmployees().get(0).getUuid();
        Employee found = employeeService.getEmployeeByUuid(existingUuid);
        assertNotNull(found, "Should find employee with existing UUID");
        assertEquals(existingUuid, found.getUuid(), "Returned employee UUID should match requested UUID");
    }

    @Test
    void getEmployeeByUuid_shouldReturnNullForUnknownUuid() {
        UUID randomUuid = UUID.randomUUID();
        Employee found = employeeService.getEmployeeByUuid(randomUuid);
        assertNull(found, "Should return null for non-existent UUID");
    }

    // -------------------------------------------------------
    // createEmployee()
    // -------------------------------------------------------

    @Test
    void createEmployee_shouldReturnCreatedEmployee() {
        Map<String, Object> requestBody = buildRequestBody();
        Employee created = employeeService.createEmployee(requestBody);
        assertNotNull(created, "Created employee should not be null");
    }

    @Test
    void createEmployee_shouldAssignUuid() {
        Map<String, Object> requestBody = buildRequestBody();
        Employee created = employeeService.createEmployee(requestBody);
        assertNotNull(created.getUuid(), "Created employee should have a UUID assigned");
    }

    @Test
    void createEmployee_shouldSetCorrectFields() {
        Map<String, Object> requestBody = buildRequestBody();
        Employee created = employeeService.createEmployee(requestBody);
        assertEquals("Alice", created.getFirstName(), "First name should match");
        assertEquals("Brown", created.getLastName(), "Last name should match");
        assertEquals("Alice Brown", created.getFullName(), "Full name should match");
        assertEquals(75000, created.getSalary(), "Salary should match");
        assertEquals(26, created.getAge(), "Age should match");
        assertEquals("QA Engineer", created.getJobTitle(), "Job title should match");
        assertEquals("alice.brown@company.com", created.getEmail(), "Email should match");
    }

    @Test
    void createEmployee_shouldAddEmployeeToList() {
        int sizeBefore = employeeService.getAllEmployees().size();
        employeeService.createEmployee(buildRequestBody());
        int sizeAfter = employeeService.getAllEmployees().size();
        assertEquals(sizeBefore + 1, sizeAfter, "Employee list should grow by 1 after creation");
    }

    @Test
    void createEmployee_shouldSetContractHireDate() {
        Employee created = employeeService.createEmployee(buildRequestBody());
        assertNotNull(created.getContractHireDate(), "Contract hire date should be set");
    }

    @Test
    void createEmployee_shouldHaveNullTerminationDate() {
        Employee created = employeeService.createEmployee(buildRequestBody());
        assertNull(created.getContractTerminationDate(), "New employee should have null termination date");
    }

    // -------------------------------------------------------
    // Helper
    // -------------------------------------------------------

    private Map<String, Object> buildRequestBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("firstName", "Alice");
        body.put("lastName", "Brown");
        body.put("salary", 75000);
        body.put("age", 26);
        body.put("jobTitle", "QA Engineer");
        body.put("email", "alice.brown@company.com");
        return body;
    }
}
