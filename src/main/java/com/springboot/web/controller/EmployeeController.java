package com.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.web.model.Employee;
import com.springboot.web.service.EmployeeService;

import java.util.List;

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

    // API for employee login (checks email and password)
    @GetMapping("/employee/login")
    public ResponseEntity<Boolean> isValidEmployee(@RequestParam String email, @RequestParam String password) {
        Boolean isValid = employeeService.isValidUser(email, password);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
