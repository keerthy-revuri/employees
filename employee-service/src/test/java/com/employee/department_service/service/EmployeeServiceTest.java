package com.employee.department_service.service;

import com.employee.department_service.entity.Employee;
import com.employee.department_service.exception.ResourceNotFoundException;
import com.employee.department_service.model.EmployeeUpdate;
import com.employee.department_service.model.NewEmployee;
import com.employee.department_service.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void updateEmployee_SentIdDifferently() throws ResourceNotFoundException {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange Phase
        UUID uuid = UUID.randomUUID();
        EmployeeUpdate employeeUpdate = new EmployeeUpdate();
        employeeUpdate.setId(UUID.randomUUID());

         //Assert Phase(validate)
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(uuid, employeeUpdate);   // Act Phase (action)
        });
       // employeeService.updateEmployee(uuid, employeeUpdate);   // Act Phase (action)

    }

    @Test
    public void updateEmployee_SentNameEmpty() throws ResourceNotFoundException {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange Phase
        UUID uuid = UUID.randomUUID();
        EmployeeUpdate employeeUpdate = new EmployeeUpdate();
        employeeUpdate.setId(uuid);

        // Assert Phase(validate)
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(uuid, employeeUpdate);   // Act Phase (action)
        });

    }
    @Test
    public void updateEmployee_SentValidData() throws ResourceNotFoundException {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange Phase
        UUID uuid = UUID.randomUUID();
        EmployeeUpdate employeeUpdate = new EmployeeUpdate();
        employeeUpdate.setId(uuid);
        employeeUpdate.setName("Keerthy");
        employeeUpdate.setDepartment("HR");

        // Create the existing employee object as expected by the findById method
        Employee existingEmployee = new Employee(uuid, "Keerthy", "IT", "2024-11-05");

        // Mock repository responses
        when(employeeRepository.findById(uuid)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);
        // Act Phase (action)
        Employee employee = employeeService.updateEmployee(uuid, employeeUpdate);
        //Assert Phase
        assertEquals(uuid, employee.getId());
        assertEquals(employeeUpdate.getName(), employee.getName());
        assertEquals(employeeUpdate.getDepartment(), employee.getDepartment());
        assertEquals("2024-11-05", employee.getDateofjoining());

    }
    @Test
    public void updateEmployee_InvalidName_ThrowsException() {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange Phase
        UUID uuid = UUID.randomUUID();
        EmployeeUpdate employeeUpdate = new EmployeeUpdate();
        employeeUpdate.setId(uuid);
        employeeUpdate.setName(""); // Invalid name (empty)

        // Mock repository response
        when(employeeRepository.findById(uuid)).thenReturn(
                Optional.of(new Employee(uuid, "Keerthy", "IT", "2024-11-05")));

        // Act & Assert Phase
        // Act & Assert Phase
        Assertions.assertThrows(ResourceNotFoundException.class, () ->
                employeeService.updateEmployee(uuid, employeeUpdate)
        );
    }

    @Test
    public void updateEmployee_EmployeeNotFound() throws ResourceNotFoundException {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange Phase
        UUID uuid = UUID.randomUUID();
        EmployeeUpdate employeeUpdate = new EmployeeUpdate();
        employeeUpdate.setId(uuid);
        employeeUpdate.setName("Keerthy");
        employeeUpdate.setDepartment("HR");
        when(employeeRepository.findById(uuid)).thenReturn(
                Optional.empty());
        // Act Phase (action)
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(uuid, employeeUpdate);   // Act Phase (action)
        });
    }

    @Test
    public void addEmployee_WithExistingId_UsesProvidedId() {// if id is given in Request Body, it will use that
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange
        UUID existingId = UUID.randomUUID();
        NewEmployee newEmployee = new NewEmployee();
        newEmployee.setId(existingId);
        newEmployee.setName("Jane Smith");
        newEmployee.setDepartment("HR");
        newEmployee.setDateofjoining("2024-10-10");

        // Expected Employee based on provided NewEmployee
        Employee expectedEmployee = new Employee(existingId, "Jane Smith", "HR", "2024-10-10");

        // Mock the save operation
        when(employeeRepository.save(any(Employee.class))).thenReturn(expectedEmployee);

        // Act
        Employee savedEmployee = employeeService.addEmployee(newEmployee);

        // Assert
        assertEquals(existingId, savedEmployee.getId(), "Expected savedEmployee to have the provided ID");
        assertEquals(newEmployee.getName(), savedEmployee.getName());
        assertEquals(newEmployee.getDepartment(), savedEmployee.getDepartment());
        assertEquals(newEmployee.getDateOfJoining(), savedEmployee.getDateofjoining(), "Expected date of joining to match");

        // Verify save was called once
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    //getEmployee method
    @Test
    public void getEmployee_ValidId_ReturnsEmployee() throws ResourceNotFoundException {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange
        UUID validId = UUID.randomUUID();
        Employee expectedEmployee = new Employee(validId, "John Doe", "Finance", "2024-11-05");
        when(employeeRepository.findById(validId)).thenReturn(Optional.of(expectedEmployee));

        // Act
        Employee actualEmployee = employeeService.getEmployee(validId);

        // Assert
        assertNotNull(actualEmployee, "Expected an employee to be returned");
        assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        assertEquals(expectedEmployee.getName(), actualEmployee.getName());
        assertEquals(expectedEmployee.getDepartment(), actualEmployee.getDepartment());
        assertEquals(expectedEmployee.getDateofjoining(), actualEmployee.getDateofjoining());

        // Verify findById was called once with the correct ID
        verify(employeeRepository, times(1)).findById(validId);
    }

    @Test
    public void getEmployee_InvalidId_ThrowsResourceNotFoundException() {
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        // Arrange
        UUID invalidId = UUID.randomUUID();
        when(employeeRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployee(invalidId),
                "Expected getEmployee() to throw ResourceNotFoundException for an invalid ID"
        );

        //assertEquals(invalidId, exception.getResourceId(), "Expected the exception to contain the invalid ID");

        // Verify findById was called once with the correct ID
        verify(employeeRepository, times(1)).findById(invalidId);
    }

    //getAllEmployee Method


}


