package com.example.Employee_Management.Service.Impl;

import com.example.Employee_Management.Entity.Employee;
import com.example.Employee_Management.Exception.EmployeeNotFoundException;
import com.example.Employee_Management.Repository.EmployeeRepository;
import com.example.Employee_Management.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Override
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        repo.deleteById(id);
    }
}
