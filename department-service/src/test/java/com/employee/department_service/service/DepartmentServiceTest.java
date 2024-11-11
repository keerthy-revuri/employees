package com.employee.department_service.service;

import com.employee.department_service.entity.Employee;
import com.employee.department_service.exception.ResourceNotFoundException;
import com.employee.department_service.model.EmployeeUpdate;
import com.employee.department_service.model.NewEmployee;
import com.employee.department_service.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class DepartmentServiceTest {


}


