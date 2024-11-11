package com.employee.department_service.exception;

import java.util.UUID;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(UUID deptid) {
        super("Resource not found with requested id"+deptid.toString());
    }

}
