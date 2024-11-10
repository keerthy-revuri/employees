package com.employee.department_service.exception;

import java.util.UUID;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(UUID id) {
        super("Resource not found with requested id"+id.toString());
    }

}
