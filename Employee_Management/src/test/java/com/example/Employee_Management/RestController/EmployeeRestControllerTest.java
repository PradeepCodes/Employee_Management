package com.example.Employee_Management.RestController;

import com.example.Employee_Management.Entity.Employee;
import com.example.Employee_Management.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeRestController.class)
 class EmployeeRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("GET /api/employees -> 200 & JSON list")
    void getAllEmployees() throws Exception {
        List<Employee> list = List.of(
                new Employee(1L, "Alice", "Smith", "1112223334", "Female", 50000.0),
                new Employee(2L, "Bob",   "Jones", "2223334445", "Male",   60000.0)
        );
        Mockito.when(employeeService.getAllEmployees()).thenReturn(list);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(list.size()))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[1].firstName").value("Bob"));
    }

    @Test
    @DisplayName("GET /api/employees/{id} -> 200 & single JSON")
    void getEmployeeByIdFound() throws Exception {
        Employee e = new Employee(1L, "Alice", "Smith", "1112223334", "Female", 50000.0);
        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(e);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    @DisplayName("GET /api/employees/{id} -> 404 if not found")
    void getEmployeeByIdNotFound() throws Exception {
        Mockito.when(employeeService.getEmployeeById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/employees -> 201 & created JSON")
    void createEmployee() throws Exception {
        Employee input = new Employee(null, "Carol", "White", "3334445556", "Female", 55000.0);
        Employee saved = new Employee(10L, "Carol", "White", "3334445556", "Female", 55000.0);

        Mockito.when(employeeService.saveEmployee(any(Employee.class))).thenReturn(saved);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Carol"));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} -> 200 & updated JSON")
    void updateEmployee() throws Exception {
        Employee input = new Employee(null, "Dave", "Brown", "4445556667", "Male", 65000.0);
        Employee updated = new Employee(5L, "Dave", "Brown", "4445556667", "Male", 65000.0);

        Mockito.when(employeeService.saveEmployee(any(Employee.class))).thenReturn(updated);

        mockMvc.perform(put("/api/employees/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.firstName").value("Dave"));
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} -> 204 No Content")
    void deleteEmployee() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee(7L);

        mockMvc.perform(delete("/api/employees/7"))
                .andExpect(status().isNoContent());
    }
}
