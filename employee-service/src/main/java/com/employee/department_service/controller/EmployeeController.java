package com.employee.department_service.controller;

import com.employee.department_service.entity.Employee;
import com.employee.department_service.exception.ResourceNotFoundException;
import com.employee.department_service.model.EmployeeUpdate;
import com.employee.department_service.model.NewEmployee;
import com.employee.department_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") UUID employeeId) throws ResourceNotFoundException {
        System.out.println("I am in controller");
        System.out.println("id - "+employeeId);
        return employeeService.getEmployee(employeeId);
    }

    @GetMapping
    public List<NewEmployee> getAllEmployee(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                            @RequestParam(value = "count", defaultValue = "5") int count,
                                            @RequestParam(value = "dept", required = false) String dept,
                                            @RequestParam(value = "namePrefix", required = false) String namePrefix)
                                             {
        System.out.println("I am in getAllmployee method");
        List<NewEmployee> employees = employeeService.getAllEmployee(offset, count, dept, namePrefix);
        return employees;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody NewEmployee newEmployee){
        return employeeService.addEmployee(newEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") UUID employeeId) throws ResourceNotFoundException {
         employeeService.deleteEmployee(employeeId);
         return ResponseEntity.ok("employee with id -"+employeeId+" " +"deleted successfully");
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable("id") UUID employeeID, @RequestBody EmployeeUpdate employeeUpdate) throws ResourceNotFoundException {
        return employeeService.updateEmployee(employeeID, employeeUpdate);
    }

    @PatchMapping("/{id}")
    public Employee partialUpdateEmployee(@PathVariable("id") UUID employeeID, @RequestBody NewEmployee updatedEmployee) throws ResourceNotFoundException {
        return employeeService.partialUpdate(employeeID, updatedEmployee);
    }


}
