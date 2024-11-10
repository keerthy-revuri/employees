package com.employee.department_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageFilter {
    private Integer offset = 0;
    private Integer count = 10;
    private String sortBy = "id";
    private boolean descending = false;
}
