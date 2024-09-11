package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Check if an employee exists by email
    boolean existsByEmail(String email);

    // Check if an employee exists by password (though it's unusual to check passwords like this)
    boolean existsByPassword(String password);

    // Find employee by email (this is more useful for login and retrieving employee details)
    Employee findByEmail(String email);
}
