package com.springboot.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.web.model.Employee;
import com.springboot.web.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Service method to register a new employee
    public Employee registerEmployee(Employee employee) {
        // Check if email already exists before saving the employee
        if (employeeRepository.findByEmail(employee.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }
        return employeeRepository.save(employee);
    }

    // Service method to retrieve all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Service method to get employee by ID
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null); // return null if not found, you can also throw an exception
    }

    // Service method to validate employee login by email and password
    public Boolean isValidUser(String email, String password) {
        // Debugging: Print the email we are trying to find
        System.out.println("Searching for employee with email: " + email.trim());
        
        // Find employee by email and trim any extra spaces
        Employee employee = employeeRepository.findByEmail(email.trim());

        // Debugging: Print whether employee was found
        if (employee == null) {
            System.out.println("Employee not found");
        } else {
            System.out.println("Employee found with email: " + employee.getEmail());
        }

        // Check if employee exists and if password matches
        if (employee != null && employee.getPassword().equals(password.trim())) {
            System.out.println("Password matched");
            return true;
        } else {
            System.out.println("Invalid credentials: email or password incorrect");
            return false;
        }
    }

    // Optionally add a method to update an employee's password (for forgot-password flow)
    public void updateEmployeePassword(String email, String newPassword) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee != null) {
            employee.setPassword(newPassword);
            employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found with the provided email.");
        }
    }
}
