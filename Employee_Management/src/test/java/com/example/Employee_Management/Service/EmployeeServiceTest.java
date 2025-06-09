package com.example.Employee_Management.Service;

import com.example.Employee_Management.Entity.Employee;
import com.example.Employee_Management.Exception.EmployeeNotFoundException;
import com.example.Employee_Management.Repository.EmployeeRepository;
import com.example.Employee_Management.Service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeeById_Found() {
        Employee employee = new Employee(1L, "John", "Doe", "9876543210", "Male", 50000.0);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(2L);
        });
        assertTrue(exception.getMessage().contains("Employee not found"));
    }

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee(null, "Jane", "Doe", "1234567890", "Female", 60000.0);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee saved = employeeService.saveEmployee(employee);
        assertEquals("Jane", saved.getFirstName());
    }
}
