package com.example.Employee_Management.Repository;

import com.example.Employee_Management.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
