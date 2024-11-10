package com.employee.department_service.repository;

import com.employee.department_service.entity.Department;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends CassandraRepository<Department, UUID> {
    @Query("SELECT * FROM department WHERE deptname = :deptname ALLOW FILTERING")
    List<Department> finddept(@Param("deptname") String deptname, Pageable pageable);


}
