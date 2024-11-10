package com.employee.department_service.repository;

import com.employee.department_service.entity.Employee;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends CassandraRepository<Employee, UUID> {
    @Query("SELECT * FROM employee WHERE name = :namePrefix AND department = :dept ALLOW FILTERING")
    List<Employee> findByNameAndDepartment(@Param("namePrefix") String namePrefix, @Param("dept") String dept, Pageable pageable);

    @Query("SELECT * FROM employee WHERE dateofjoining = :dateofjoining ALLOW FILTERING")
    List<Employee> findByDateofjoining(@Param("dateofjoining") String dateofjoining);


}
