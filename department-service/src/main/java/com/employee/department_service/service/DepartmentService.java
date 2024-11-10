package com.employee.department_service.service;

import com.employee.department_service.entity.Department;
import com.employee.department_service.entity.OffsetBasedPageRequest;
import com.employee.department_service.exception.ResourceNotFoundException;
import com.employee.department_service.repository.DepartmentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Log4j2
@Service
public class DepartmentService {
    DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     *
     * @param id empid
     * @return dept details
     * @throws ResourceNotFoundException
     */
    public Department getDepartment(UUID id) throws ResourceNotFoundException {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Department> getAllDepartment(int offset, int count, String deptname){
        List<Department> departments = departmentRepository.finddept(deptname,
                new OffsetBasedPageRequest(offset, count, null));

        log.info("departments"+ departments);
        return departments;

    }

}
