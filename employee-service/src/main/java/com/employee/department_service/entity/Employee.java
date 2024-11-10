package com.employee.department_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.UUID;

@Table("employee")
@AllArgsConstructor
@NoArgsConstructor
@Data// specify the table name if it’s different from the entity name
public class Employee {

    @PrimaryKey
    private UUID id;

    @Column("name")
    private String name;

    @Column("department")
    private String department;

    @Column("dateofjoining")
    private String dateofjoining;



}
