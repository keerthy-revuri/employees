package com.employee.department_service.exception;

public class ForbiddenAccessException extends Exception{
    ForbiddenAccessException(){
        super("Unauthorized error");
    }
}
