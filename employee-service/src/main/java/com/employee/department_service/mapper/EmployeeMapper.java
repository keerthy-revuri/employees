package com.employee.department_service.mapper;

import com.employee.department_service.entity.Employee;
import com.employee.department_service.model.NewEmployee;

import java.util.UUID;

public class EmployeeMapper {
    public static NewEmployee mapToNewEmployee(Employee employee) {
        return new NewEmployee(
                employee.getId(),
                employee.getName(),
                employee.getDepartment(),
                employee.getDateofjoining()
        );
    }

    public static Employee mapToEmployee(NewEmployee newEmployee) {
        // Generate a new UUID if the incoming `NewEmployee` object does not have an `id`
        // code is removed from addEmployee method and added it here because in newEmployee - id is optional,
        // we generate id and map it ito Employee as it is required to add in db.
        if (newEmployee.getId() == null) {
            UUID generatedId = UUID.randomUUID();
            newEmployee.setId(generatedId);
        }
        return new Employee(
                newEmployee.getId(),
                newEmployee.getName(),
                newEmployee.getDepartment(),
                newEmployee.getDateOfJoining()
        );
    }

}
