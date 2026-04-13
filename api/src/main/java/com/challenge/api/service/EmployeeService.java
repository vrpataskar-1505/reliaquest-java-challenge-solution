package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeModel;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;


// Service class for employee operations.
// Using in-memory list since no database is needed.

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    public EmployeeService() {
        // adding some mock employees ==>
        employees.add(createMockEmployee("Shweta", "Pataskar", "DevOps Engineer", 85000, 27, "shweta@gmail.com"));
        employees.add(createMockEmployee("Shreya", "Vibhute", "Product Manager", 95000, 34, "shreya@gmail.com"));
        employees.add(createMockEmployee("Om", "Nehulkar", "Software Engineer", 90000, 31, "om@gmail.com"));
    }

    /**
     * Returns all employees.
     * @return list of all employees
     */
    public List<Employee> getAllEmployees() {
        return employees;
    }

    /**
     * Find employee by UUID.
     * Returns null if not found.
     * @param uuid employee UUID
     * @return matching employee or null
     */
    public Employee getEmployeeByUuid(UUID uuid) {
        // using stream to search through the list=>
        return employees.stream()
                .filter(e -> e.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    /**
     * Creates new employee from request data.
     * @param requestBody employee details
     * @return newly created employee
     */
    public Employee createEmployee(Map<String, Object> requestBody) {
        // assigning random UUID to new employee=>
        EmployeeModel employee = new EmployeeModel();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName((String) requestBody.get("firstName"));
        employee.setLastName((String) requestBody.get("lastName"));

        // combining the first and last name for full name=>
        employee.setFullName(requestBody.get("firstName") + " " + requestBody.get("lastName"));
        employee.setSalary((Integer) requestBody.get("salary"));
        employee.setAge((Integer) requestBody.get("age"));
        employee.setJobTitle((String) requestBody.get("jobTitle"));
        employee.setEmail((String) requestBody.get("email"));
        employee.setContractHireDate(Instant.now());

        // null means employee is currently active....
        employee.setContractTerminationDate(null);
        employees.add(employee);
        return employee;
    }

    private Employee createMockEmployee(
            String firstName, String lastName, String jobTitle, int salary, int age, String email) {
        EmployeeModel employee = new EmployeeModel();
        employee.setUuid(UUID.randomUUID());
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setFullName(firstName + " " + lastName);
        employee.setSalary(salary);
        employee.setAge(age);
        employee.setJobTitle(jobTitle);
        employee.setEmail(email);
        employee.setContractHireDate(Instant.now());
        employee.setContractTerminationDate(null);
        return employee;
    }
}
