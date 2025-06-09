package com.example.Employee_Management.Controller;

import com.example.Employee_Management.Entity.Employee;
import com.example.Employee_Management.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testListEmployees() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "John", "Doe", "9876543210", "Male", 50000.0)
        );

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-list"))
                .andExpect(model().attributeExists("employees"));
    }

    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeExists("employee"));
    }

    @Test
    void testEditEmployee() throws Exception {
        Employee employee = new Employee(1L, "Jane", "Smith", "1234567890", "Female", 60000.0);
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeExists("employee"));
    }
}
