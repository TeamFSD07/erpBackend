package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.model.Employee;

public interface UserRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
    boolean existsByEmail(String email);
}
