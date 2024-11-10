package com.employee.department_service.service;

import com.employee.department_service.entity.Employee;
import com.employee.department_service.entity.OffsetBasedPageRequest;
import com.employee.department_service.exception.ResourceNotFoundException;
import com.employee.department_service.mapper.EmployeeMapper;
import com.employee.department_service.model.DepartmentDetails;
import com.employee.department_service.model.EmployeeUpdate;
import com.employee.department_service.model.NewEmployee;
import com.employee.department_service.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;
    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Employee getEmployee(UUID id) throws ResourceNotFoundException {
        System.out.println("I am in Service");
        Optional<Employee> byId = employeeRepository.findById(id);
        // alt + enter - to avoid null values
        if (byId.isEmpty()){
            throw new ResourceNotFoundException(id);
        }
        return byId.get();
    }

    public Employee addEmployee(NewEmployee newEmployee) {

        log.info("adding a new employee");

        final ResponseEntity<List<DepartmentDetails>> response =
                restTemplate.exchange(
                        "http://localhost:8081/departments?deptname="+newEmployee.getDepartment(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        log.info("response:"+ response);

        // Map NewEmployee to Employee entity and save it
        return employeeRepository.save(EmployeeMapper.mapToEmployee(newEmployee));

    }

    public void deleteEmployee(UUID id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        employeeRepository.deleteById(id);
    }
    //@Transactional
    public Employee updateEmployee(UUID id, EmployeeUpdate employeeUpdate) throws ResourceNotFoundException {

//         if(!employeeUpdate.getId().equals(id)) { //throws Null Pointer exception
//             throw new ResourceNotFoundException(id);
//         }
         if(!Objects.equals(employeeUpdate.getId(), id)) { //avoids Null Pointer exception
             throw new ResourceNotFoundException(id);
         }
         if(!StringUtils.hasText(employeeUpdate.getName())){
             throw new ResourceNotFoundException(id);
         }
         Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
                 () -> new ResourceNotFoundException(id)
         );// using optional avoids NullPointer exception
         System.out.println("updated employee department"+employeeUpdate.getDepartment());
         existingEmployee.setName(employeeUpdate.getName());
         existingEmployee.setDepartment(employeeUpdate.getDepartment());
         return employeeRepository.save(existingEmployee);
         //return existingEmployee;
         }

    public Employee partialUpdate(UUID id, NewEmployee updatedEmployee) throws ResourceNotFoundException{
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        if(StringUtils.hasText(updatedEmployee.getName())){
            existingEmployee.setName(updatedEmployee.getName());
        }
        if(StringUtils.hasText(updatedEmployee.getDepartment())){
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
        }
        if (updatedEmployee.getDateOfJoining() != null) {
            existingEmployee.setDateofjoining(updatedEmployee.getDateOfJoining());
        }


        return employeeRepository.save(existingEmployee);
    }

    public List<NewEmployee> getAllEmployee(int offset, int count, String dept, String namePrefix){
        List<Employee> employees = employeeRepository.findByNameAndDepartment(namePrefix, dept,
                new OffsetBasedPageRequest(offset, count, null));
        //return employees;
        return employees.stream()
                .map((employee) -> EmployeeMapper.mapToNewEmployee(employee)).
                collect(Collectors.toList());
    }

//    @Scheduled(cron = "0 * * * * ?") // CRON expression to run every minute
//    public void performScheduledTask() {
//
//        System.out.println("Rescheduling this task for every 1 minute");
//
//    }



}
