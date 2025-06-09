package com.example.Employee_Management.Repository;

import com.example.Employee_Management.Entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee(null, "John", "Doe", "9876543210", "Male", 50000.0);
        Employee saved = employeeRepository.save(employee);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("John");
    }

    @Test
    void testFindById() {
        Employee employee = new Employee(null, "Jane", "Smith", "1234567890", "Female", 60000.0);
        Employee saved = employeeRepository.save(employee);

        Optional<Employee> found = employeeRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Jane");
    }

    @Test
    void testFindAll() {
        Employee employee1 = new Employee(null, "John", "Doe", "9876543210", "Male", 50000.0);
        Employee employee2 = new Employee(null, "Jane", "Smith", "1234567890", "Female", 60000.0);
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> allEmployees = employeeRepository.findAll();
        assertThat(allEmployees.size()).isGreaterThanOrEqualTo(2);
    }
}
