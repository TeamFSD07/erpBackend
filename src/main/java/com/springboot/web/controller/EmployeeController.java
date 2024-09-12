package com.springboot.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.web.model.Employee;
import com.springboot.web.service.EmployeeService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Adjust the origin as needed for CORS
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // API for employee registration
    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
        try {
            Employee registeredEmployee = employeeService.registerEmployee(employee);
            return new ResponseEntity<>(registeredEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // API to fetch all employees
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // API to fetch employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/login")
    public ResponseEntity<Boolean> isValidEmployee(@RequestParam String email, @RequestParam String password) {
        // Trim email and password to avoid spaces causing issues
        String trimmedEmail = email.trim();
        String trimmedPassword = password.trim();
    
        // Debugging: Print the email and password for verification
        System.out.println("Received login request for email: " + trimmedEmail);
        System.out.println("Received password: " + trimmedPassword);
    
        Boolean isValid = employeeService.isValidUser(trimmedEmail, trimmedPassword);
    
        if (isValid) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }
    
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
    
    // Static admin credentials
    private static final String ADMIN_EMAIL = "Admin@example.com";
    private static final String ADMIN_PASSWORD = "Admin123";

    // Admin login API
    @GetMapping("/admin/login")
    public ResponseEntity<Boolean> isValidAdmin(@RequestParam String email, @RequestParam String password) {
        // Trim email and password to remove any leading/trailing whitespaces
        String trimmedEmail = email.trim();
        String trimmedPassword = password.trim();

        // Print received credentials (for debugging purposes)
        System.out.println("Received email: " + trimmedEmail);
        System.out.println("Received password: " + trimmedPassword);

        // Check credentials, email comparison is case-insensitive
        if (ADMIN_EMAIL.equalsIgnoreCase(trimmedEmail) && ADMIN_PASSWORD.equals(trimmedPassword)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
