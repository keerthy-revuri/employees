package com.employee.department_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Table("department")
@AllArgsConstructor
@NoArgsConstructor
@Data// specify the table name if it’s different from the entity name
public class Department {

    @PrimaryKey
    private UUID deptid;

    @Column("deptname")
    private String deptname;

    @Column("hodid")
    private UUID hodid;



}
