package com.challenge.api.controller;

import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



// REST Controller for employee management.
// Has 3 endpoints - get all, get by uuid, create employee.

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Returns all employees.
     * Using mock data since no database is needed.
     * @return list of all employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Find employee by UUID.
     * Returns 404 if employee not found.
     * @param uuid employee UUID
     * @return employee if found
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
        Employee employee = employeeService.getEmployeeByUuid(uuid);
        // return 404 if employee doesn't exist
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with UUID: " + uuid);
        }
        return ResponseEntity.ok(employee);
    }

    /**
     * Creates a new employee.
     * Takes employee details in request body and saves in memory.
     * Returns 400 if request body is empty or invalid.
     * @param requestBody employee details like name, salary, age etc
     * @return newly created employee with 201 status
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> requestBody) {
        if (requestBody == null || requestBody.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body cannot be empty");
        }
        try {
            Employee created = employeeService.createEmployee(requestBody);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create employee: " + e.getMessage());
        }
    }
}