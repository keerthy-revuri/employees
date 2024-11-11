package com.employee.department_service.mapper;

import com.employee.department_service.entity.Employee;
import com.employee.department_service.model.NewEmployee;
import com.employee.department_service.repository.EmployeeRepository;
import com.employee.department_service.service.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Log4j2
@ExtendWith(SpringExtension.class)
public class EmployeeMapperTest {
    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void mapToEmployee_NoId_GeneratesNewId() {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange
        NewEmployee newEmployee = new NewEmployee();
        newEmployee.setName("John Doe");
        newEmployee.setDepartment("Finance");
        newEmployee.setDateofjoining("2024-11-05");

        // Mocking save method to return an Employee
        Employee generatedEmployee = new Employee(UUID.randomUUID(), "John Doe", "Finance", "2024-11-05");
        when(employeeRepository.save(any(Employee.class))).thenReturn(generatedEmployee);

        // Act
        Employee savedEmployee = employeeService.addEmployee(newEmployee);
        log.info("Saved Employee: {}", savedEmployee);
        // Assert

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository, times(1)).save(captor.capture());
        // Capture the Employee object passed to the save method
        Employee capturedEmployee = captor.getValue();

        // Validate that a new ID was generated and assigned correctly
        assertNotNull(savedEmployee.getId(), "Expected savedEmployee to have a generated ID");
        assertEquals(generatedEmployee.getId(), savedEmployee.getId());  // Compare against the generated UUID
        assertEquals(capturedEmployee.getId(), savedEmployee.getId());
        assertEquals(newEmployee.getName(), capturedEmployee.getName());
        assertEquals(newEmployee.getDepartment(), capturedEmployee.getDepartment());
        assertEquals(newEmployee.getDateOfJoining(), capturedEmployee.getDateofjoining(), "Expected date of joining to match");

        // Verify that save was only called once with the correct argument
        verify(employeeRepository, times(1)).save(capturedEmployee);
    }

}


